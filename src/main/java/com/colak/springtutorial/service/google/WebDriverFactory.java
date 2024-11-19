package com.colak.springtutorial.service.google;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;


public class WebDriverFactory {

    public static WebDriver createDriver() {
        try {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(getChromeOptions());
        } catch (Exception e) {
            throw new WebDriverException("Failed to create WebDriver", e);
        }
    }

    private static ChromeOptions getChromeOptions() {//check
        ChromeOptions options = new ChromeOptions();
        // Run in headless mode (no UI)
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return options;
    }
}
