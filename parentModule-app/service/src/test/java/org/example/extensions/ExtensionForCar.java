package org.example.extensions;

import org.example.CarService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ExtensionForCar implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CarService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final String directory = ExtensionForCar.class.getResource("").toString().substring(6).split("target")[0];
        final String path = directory + "src/test/resources/";
        final String testJsonFile = "cars.json";
        final String fullPath = path + testJsonFile;
        return new CarService(fullPath);
    }
}
