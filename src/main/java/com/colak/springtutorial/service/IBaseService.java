package com.colak.springtutorial.service;

import com.colak.springtutorial.controller.DriverType;

import java.util.List;

public interface IBaseService{
    List<SearchResponse> getResults(String query, int maxCount);
    DriverType getDriverType();
}
