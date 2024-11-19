package com.colak.springtutorial.controller;

import com.colak.springtutorial.service.DriverServiceFactory;
import com.colak.springtutorial.service.IBaseService;
import com.colak.springtutorial.service.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SearchController {

    private final DriverServiceFactory driverServiceFactory;

    // http://localhost:8080/api/v1/search?query=what%20is%20latest%20in%20AI&driver=GOOGLE
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SearchResponse> getWebSearchResults(
            @RequestParam(required = true, name = "query") String query,
            @RequestParam(required = false, name = "driver", defaultValue = "GOOGLE") DriverType driver,
            @RequestParam(required = false, name = "max_count", defaultValue = "10") String maxCount) {

        IBaseService service = driverServiceFactory.getDriverService(driver);
        return service.getResults(query, Integer.parseInt(maxCount));
    }
}
