package com.colak.springtutorial.service;

import com.colak.springtutorial.controller.DriverType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class DriverServiceFactory {

    private final Map<DriverType, IBaseService> driverServices;

    public DriverServiceFactory(List<IBaseService> driverServices) {
        this.driverServices = driverServices.stream()
                .collect(Collectors.toMap(
                        IBaseService::getDriverType,
                        service -> service));
    }

    public IBaseService getDriverService(DriverType type) {
        return driverServices.get(type);
    }

}
