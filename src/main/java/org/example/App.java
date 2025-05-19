package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App 
{
    public static void main( String[] args )
    {
        System.setProperty("webdriver.chrome.driver", "D:\\Games\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("https://www.calculator.net/password-generator.html");
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
    }
}
