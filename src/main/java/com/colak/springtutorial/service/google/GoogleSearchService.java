package com.colak.springtutorial.service.google;

import com.colak.springtutorial.controller.DriverType;
import com.colak.springtutorial.service.IBaseService;
import com.colak.springtutorial.service.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;


@Service
@Slf4j
public class GoogleSearchService implements IBaseService {

    @Override
    public DriverType getDriverType() {
        return DriverType.GOOGLE;
    }

    @Override
    public List<SearchResponse> getResults(String query, int maxCount) {
        log.info("Inside Google Search Function");

        List<SearchResponse> response = null;
        WebDriver driver = null;
        try {
            driver = WebDriverFactory.createDriver();
            driver.get("https://www.google.com");

            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(query);
            searchBox.submit();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3")));

            List<WebElement> results = driver
                    .findElements(By.cssSelector("h3"))
                    .stream()
                    .limit(maxCount)
                    .toList();

            response = results
                    .stream()
                    .map(result -> new SearchResponse(result.getText(),
                            result.findElement(By.xpath(".."))
                                    .getAttribute("href")))
                    .toList();
        } catch (Exception e) {
            log.error("Exception Occurred in Google Search", e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        return response;
    }

}
