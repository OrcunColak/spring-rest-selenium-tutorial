package com.colak.springtutorial.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SearchResponse {
    private String title;
    private String url;
}
