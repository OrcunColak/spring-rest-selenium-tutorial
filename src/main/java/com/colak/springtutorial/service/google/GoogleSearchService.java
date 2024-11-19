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

            // Finds the search input box on Google’s homepage using the name attribute (q).
            WebElement searchBox = driver.findElement(By.name("q"));
            // Types the query into the search box.
            searchBox.sendKeys(query);

            // Submits the form by calling submit() on the search box element.
            searchBox.submit();

            // Waits for up to 10 seconds for the search results to appear.
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Specifically, it waits for any <h3> element (Google uses <h3> tags for result titles) to be present in the DOM.
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3")));

            // Finds all <h3> elements on the page, which represent search result titles.
            // Limits the list to the maxCount number of results using a stream().
            List<WebElement> results = driver
                    .findElements(By.cssSelector("h3"))
                    .stream()
                    .limit(maxCount)
                    .toList();

            response = results
                    .stream()
                    .map(result -> new SearchResponse(
                            // Extracts the title text
                            result.getText(),
                            getUrl(result))
                    )
                    .toList();
        } catch (Exception e) {
            log.error("Exception Occurred in Google Search", e);
        } finally {
            // the browser session is terminated by calling driver.quit().
            if (driver != null) {
                driver.quit();
            }
        }
        return response;
    }

    private static String getUrl(WebElement result) {
        // Finds the parent <a> element of the <h3> (the clickable link).
        return result.findElement(By.xpath(".."))
                // Extracts the URL from the <a> element.
                .getAttribute("href");
    }

}
