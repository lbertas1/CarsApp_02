package org.example;

import org.example.converters.JsonCarsConverter;
import org.example.enums.ArgumentToStatistics;
import org.example.enums.ArgumentsToSort;
import org.example.exception.CarServiceException;
import org.example.help.MileageAndPowerPriceStats;
import org.example.help.Stats;
import org.example.model.Car;
import org.example.model.enums.CarBodyType;
import org.example.model.enums.Type;
import org.example.model.enums.TyreType;
import org.example.validator.CarValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CarService {

    private Set<Car> cars;

    public CarService(String fileName) {
        this.cars = init(fileName);
    }

    public Set<Car> getCars() {
        return cars;
    }

    private Set<Car> init(String filename) {
        AtomicInteger counter = new AtomicInteger(1);
        return new JsonCarsConverter(filename)
                .fromJson()
                .orElseThrow(() -> new CarServiceException("cannot convert data from file"))
                .stream()
                .filter(car -> {
                    var carValidator = new CarValidator();
                    var errors = carValidator.validate(car);
                    if (!errors.isEmpty()) {
                        System.out.println("----------------------- validation errors for car no. " + counter.get() + " ------------------------");
                        System.out.println(errors
                                .entrySet()
                                .stream()
                                .map(e -> e.getKey() + ": " + e.getValue())
                                .collect(Collectors.joining("\n"))
                        );
                    }
                    counter.incrementAndGet();
                    return errors.isEmpty();
                }).collect(Collectors.toSet());
    }

    public String cars() {
        return cars
                .stream()
                .map(Car::toString)
                .collect(Collectors.joining("\n\n"));
    }

    public List<Car> sortByGivenArguments(ArgumentsToSort argumentsToSort, boolean descending) {
        if (Objects.isNull(argumentsToSort)) throw new IllegalArgumentException("argument to sort is null");

        List<Car> carsAfterSort = null;

        switch (argumentsToSort) {
            case POWER -> carsAfterSort = cars.stream().sorted(Comparator.comparing(car -> car.getEngine().getPower())).collect(Collectors.toList());
            case COMPONENTS_QUANTITY -> carsAfterSort = cars.stream().sorted(Comparator.comparing(car -> car.getCarBody().getComponents().size())).collect(Collectors.toList());
            case WHEEL_SIZE -> carsAfterSort = cars.stream().sorted(Comparator.comparing(car -> car.getWheel().getSize())).collect(Collectors.toList());
        }

        if (descending) Collections.reverse(carsAfterSort);

        return carsAfterSort;
    }

    public Set<Car> getCarsByCarBodyTypeAndPrice(CarBodyType carBodyType, double a, double b) {
        if (Objects.isNull(carBodyType)) throw new IllegalArgumentException("Car body type is null");

        return cars.stream()
                .filter(car -> car.getPrice().doubleValue() >= a && car.getPrice().doubleValue() <= b)
                .filter(car -> car.getCarBody().getType().equals(carBodyType))
                .collect(Collectors.toSet());
    }

    public Set<Car> getCarsByEngineType(Type type) {
        if (Objects.isNull(type)) throw new IllegalArgumentException("Given argument is null");

        return cars.stream()
                .filter(car -> car.getEngine().getType().equals(type))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public MileageAndPowerPriceStats getStatisticsForGivenArgument(ArgumentToStatistics argumentToStatistics) {
        if (Objects.isNull(argumentToStatistics)) throw new IllegalArgumentException("Given argument is null");

        switch (argumentToStatistics) {
            case PRICE -> {
                DoubleSummaryStatistics priceStatistics = cars
                        .stream()
                        .map(Car::getPrice)
                        .mapToDouble(BigDecimal::doubleValue)
                        .summaryStatistics();

                return MileageAndPowerPriceStats.builder()
                        .priceStats(Stats.<BigDecimal>builder()
                                .max(BigDecimal.valueOf(priceStatistics.getMax()))
                                .min(BigDecimal.valueOf(priceStatistics.getMin()))
                                .avg(BigDecimal.valueOf(priceStatistics.getAverage()))
                                .build())
                        .build();
            }
            case POWER -> {
                DoubleSummaryStatistics powerStatistics = cars
                        .stream()
                        .mapToDouble(car -> car.getEngine().getPower())
                        .summaryStatistics();

                return MileageAndPowerPriceStats.builder()
                        .powerStats(Stats.<Double>builder()
                                .max(powerStatistics.getMax())
                                .min(powerStatistics.getMin())
                                .avg(powerStatistics.getAverage())
                                .build())
                        .build();
            }
            case MILEAGE -> {
                DoubleSummaryStatistics mileageStatistics = cars
                        .stream()
                        .mapToDouble(Car::getMileage)
                        .summaryStatistics();

                return MileageAndPowerPriceStats.builder()
                        .mileageStats(Stats.<Double>builder()
                                .max(mileageStatistics.getMax())
                                .min(mileageStatistics.getMin())
                                .avg(mileageStatistics.getAverage())
                                .build())
                        .build();
            }
        }
        return null;
    }

    public Map<Car, Integer> createMapWhereKeyIsCarAndValueIsMileage() {
        return cars
                .stream()
                .sorted((o1, o2) -> Long.compare(o2.getMileage(), o1.getMileage()))
                .collect(Collectors.toMap(
                        Function.identity(),
                        Car::getMileage,
                        (integer, integer2) -> integer,
                        LinkedHashMap::new
                ));
    }

    public Map<TyreType, List<Car>> groupByTyreType() {
        return cars
                .stream()
                .map(car -> car.getWheel().getType())
                .collect(Collectors.toMap(
                        Function.identity(),
                        tyreType -> cars
                                .stream()
                                .filter(car -> car.getWheel().getType().equals(tyreType))
                                .collect(Collectors.toList()),
                        (cars1, cars2) -> cars2,
                        LinkedHashMap::new
                ));
    }

    public List<Car> getCarsWithGivenComponents(List<String> components) {
        if (Objects.isNull(components)) throw new IllegalArgumentException("Given argument is null");

        return cars.stream()
                .filter(car -> {
                    for (String component : components) {
                        if (!car.getCarBody().getComponents().contains(component)) return false;
                    }
                    return true;
                })
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
