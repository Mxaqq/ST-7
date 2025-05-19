package org.example;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class Task3 {
    public static void main(String[] args) {
        // Установка пути к драйверу через системную переменную
        System.setProperty("webdriver.chrome.driver", "D:\\Games\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56.3287&longitude=44.002&hourly=temperature_2m,rain&timezone=Europe%2FMoscow&forecast_days=1";

            driver.get(url);
            WebElement elem = driver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();
            System.out.println("Получены данные от API");

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);

            if (!obj.containsKey("hourly")) {
                throw new Exception("API не вернул данные о погоде. Проверьте параметры запроса.");
            }

            JSONObject hourly = (JSONObject) obj.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            // Создаем папку result, если ее нет
            if (!Files.exists(Paths.get("result"))) {
                Files.createDirectories(Paths.get("result"));
            }

            // Форматирование чисел
            DecimalFormat tempFormat = new DecimalFormat("+#;-#");
            DecimalFormat rainFormat = new DecimalFormat("0.0");

            // Вывод в консоль с красивым форматированием
            System.out.println("\nПрогноз погоды в Нижнем Новгороде");
            System.out.println("+----+--------+------------+---------+");
            System.out.println("| №  | Время  | Температура | Осадки |");
            System.out.println("+----+--------+------------+---------+");

            // Запись в файл
            try (FileWriter writer = new FileWriter("result/forecast.txt")) {
                writer.write("Прогноз погоды в Нижнем Новгороде\n");
                writer.write("+----+--------+------------+---------+\n");
                writer.write("| №  | Время  | Температура | Осадки |\n");
                writer.write("+----+--------+------------+---------+\n");

                for (int i = 0; i < times.size(); i++) {
                    String time = ((String) times.get(i)).substring(11);
                    String temp = tempFormat.format(Double.parseDouble(temps.get(i).toString()));
                    String rain = rainFormat.format(Double.parseDouble(rains.get(i).toString()));

                    // Форматированный вывод в консоль
                    System.out.printf("| %-2d | %s | %5s°C    | %5s мм |\n",
                            i+1, time, temp, rain);

                    // Запись в файл
                    writer.write(String.format("| %-2d | %s | %5s°C    | %5s мм |\n",
                            i+1, time, temp, rain));
                }
                System.out.println("+----+--------+------------+---------+");
                writer.write("+----+--------+------------+---------+\n");
                System.out.println("\nДанные сохранены в result/forecast.txt");
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}