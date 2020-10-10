package org.example.converters;

import org.example.model.Car;

import java.util.List;

public class JsonCarsConverter extends JsonConverter<List<Car>> {
    public JsonCarsConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
