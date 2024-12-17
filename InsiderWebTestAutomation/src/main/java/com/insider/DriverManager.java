package com.insider;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverManager {

    public static void setup(String browser){
        switch (browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                break;
            case "safari":
                WebDriverManager.safaridriver().setup();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + browser);
        }
    }

    public static WebDriver getDriver(String browser) {
        return switch (browser) {
            case "chrome" -> new ChromeDriver();
            case "safari" -> new SafariDriver();
            case "firefox" -> new FirefoxDriver();
            default -> throw new IllegalStateException("Unexpected value: " + browser);
        };
    }
}
