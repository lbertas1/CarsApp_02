package org.example.validator;

import org.example.model.Car;
import org.example.model.Engine;
import org.example.model.enums.CarBodyColor;
import org.example.model.enums.CarBodyType;
import org.example.model.enums.TyreType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarValidator implements Validator<Car> {
    @Override
    public Map<String, String> validate(Car car) {
        if (car == null) {
            return Map.of("car object", "is null");
        }

        Map<String, String> errors = new HashMap<>();

        if (!isModelValid(car.getModel())) {
            errors.put("org/example/model ", "is not valid");
        }

        if (!isPriceValid(car.getPrice())) {
            errors.put("price ", "ins't valid");
        }

        if (!isMileageValid(car.getMileage())) {
            errors.put("mileage ", "ins't valid");
        }

        if (!isComponentsCollectionValid(car.getCarBody().getComponents())) {
            errors.put("components ", "ins't valid");
        }

        if (!isModelValid(car.getWheel().getModel())) {
            errors.put("wheel model ", "is not valid");
        }

        if (!isSizeValid(car.getWheel().getSize())) {
            errors.put("wheel size ", "is not valid");
        }

        if (!isPowerValid(car.getEngine().getPower())) {
            errors.put("engine power ", "is not valid");
        }

        if (!isEngineTypeValid(car.getEngine())) {
            errors.put("engine ", "is not valid");
        }

        if (!isTyreTypeValid(car.getWheel().getType())) {
            errors.put("wheel type", "is not valid");
        }

        if (!isCarBodyColorValid(car.getCarBody().getColor())) {
            errors.put("color ", "is not valid");
        }

        if (!isCarBodyTypeValid(car.getCarBody().getType())) {
            errors.put("car body type ", "is not valid");
        }


        return errors;
    }

    private boolean isModelValid(String model) {
        return model.matches("[A-Z]+\\s*[A-Z]*");
    }

    private boolean isPriceValid(BigDecimal price) {
        return price.intValue() > 0;
    }

    private boolean isSizeValid(Long size) {
        return size > 0;
    }

    private boolean isPowerValid(Double power) {
        return power > 0;
    }

    private boolean isEngineTypeValid(Engine engine) {
        return engine != null;
    }

    private boolean isTyreTypeValid(TyreType tyreType) {
        return tyreType != null;
    }

    private boolean isCarBodyColorValid(CarBodyColor carBodyColor) {
        return carBodyColor != null;
    }

    private boolean isCarBodyTypeValid(CarBodyType carBodyType) {
        return carBodyType != null;
    }

    private boolean isMileageValid(Integer mileage) {
        return mileage > 0;
    }

    private boolean isComponentsCollectionValid(List<String> components) {
        AtomicBoolean isValid = new AtomicBoolean(true);
        components.forEach(s -> {
            if (!s.matches("[A-Z]+\\s*[A-Z]*")) isValid.set(false);
        });
        return isValid.get();
    }
}
