package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

public class Task2 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Games\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-extensions");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);

        try {
            System.out.println("Запрашиваю данные с api.ipify.org...");

            String url = "https://api.ipify.org/?format=json";
            driver.get(url);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            WebElement elem = driver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);

            if (!obj.containsKey("ip")) {
                throw new ParseException(0, "Ключ 'ip' не найден в JSON ответе");
            }

            String ip = (String) obj.get("ip");

            System.out.println("\n+---------------------+");
            System.out.println("|   Ваш IP-адрес:     |");
            System.out.println("+---------------------+");
            System.out.println("| " + String.format("%-19s", ip) + " |");
            System.out.println("+---------------------+");

        } catch (ParseException e) {
            System.err.println("Ошибка парсинга JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка при получении IP: " + e.getMessage());
        } finally {
            driver.quit();
            System.out.println("\nДрайвер успешно закрыт");
        }
    }
}