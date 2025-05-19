package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task1 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Games\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("http://api.open-notify.org/astros.json");
            System.out.println(webDriver.getPageSource());
            WebElement elem = webDriver.findElement(By.tagName("pre"));

            String json_str = elem.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(json_str);
            JSONArray people = (JSONArray) obj.get("people");
            System.out.println("Список космонавтов на орбите");
            System.out.println("Количество: " + people.size());
            for (int i = 0; i < people.size(); ++i) {
                JSONObject person = (JSONObject) people.get(i);
                System.out.println((i + 1) + ") " + person.get("craft") + " " + person.get("name"));
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
    }
}