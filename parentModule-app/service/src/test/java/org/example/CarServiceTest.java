package org.example;

import lombok.RequiredArgsConstructor;
import org.example.enums.ArgumentToStatistics;
import org.example.enums.ArgumentsToSort;
import org.example.extensions.ExtensionForCar;
import org.example.help.MileageAndPowerPriceStats;
import org.example.model.Car;
import org.example.model.CarBody;
import org.example.model.Engine;
import org.example.model.Wheel;
import org.example.model.enums.CarBodyType;
import org.example.model.enums.Type;
import org.example.model.enums.TyreType;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ExtensionForCar.class)
@RequiredArgsConstructor
class CarServiceTest {

    private final CarService carService;

    @Test
    public void getCars() {
        assertNotNull(carService.getCars());
    }

    @Test
    public void sortByPowerAscending() {
        List<Car> cars = carService.sortByGivenArguments(ArgumentsToSort.POWER, false);

        Engine engineWithBiggestPower = carService
                .getCars()
                .stream()
                .map(Car::getEngine)
                .max(Comparator.comparing(Engine::getPower))
                .orElseThrow();

        Engine engineWithSmallestPower = carService
                .getCars()
                .stream()
                .map(Car::getEngine)
                .min(Comparator.comparing(Engine::getPower))
                .orElseThrow();

        assertAll(() -> {
            assertEquals(engineWithBiggestPower, cars.get(cars.size() - 1).getEngine());
            assertEquals(engineWithSmallestPower, cars.get(0).getEngine());
        });
    }

    @Test
    public void sortByPowerDescending() {
        List<Car> cars = carService.sortByGivenArguments(ArgumentsToSort.POWER, true);

        Engine engineWithBiggestPower = carService
                .getCars()
                .stream()
                .map(Car::getEngine)
                .max(Comparator.comparing(Engine::getPower))
                .orElseThrow();

        Engine engineWithSmallestpower = carService
                .getCars()
                .stream()
                .map(Car::getEngine)
                .min(Comparator.comparing(Engine::getPower))
                .orElseThrow();

        assertAll(() -> {
            assertEquals(engineWithBiggestPower, cars.get(0).getEngine());
            assertEquals(engineWithSmallestpower, cars.get(cars.size() - 1).getEngine());
        });
    }

    @Test
    public void sortByWheelSizeAscending() {
        List<Car> cars = carService.sortByGivenArguments(ArgumentsToSort.WHEEL_SIZE, false);

        Wheel biggestWheel = carService
                .getCars()
                .stream()
                .map(Car::getWheel)
                .max(Comparator.comparing(Wheel::getSize))
                .orElseThrow();

        Wheel smallestWheel = carService
                .getCars()
                .stream()
                .map(Car::getWheel)
                .min(Comparator.comparing(Wheel::getSize))
                .orElseThrow();

        assertAll(() -> {
            assertEquals(biggestWheel, cars.get(cars.size() - 1).getWheel());
            assertEquals(smallestWheel, cars.get(0).getWheel());
        });
    }

    @Test
    public void sortByWheelSizeDescending() {
        List<Car> cars = carService.sortByGivenArguments(ArgumentsToSort.WHEEL_SIZE, true);

        Wheel biggestWheel = carService
                .getCars()
                .stream()
                .map(Car::getWheel)
                .max(Comparator.comparing(Wheel::getSize))
                .orElseThrow();

        Wheel smallestWheel = carService
                .getCars()
                .stream()
                .map(Car::getWheel)
                .min(Comparator.comparing(Wheel::getSize))
                .orElseThrow();

        assertAll(() -> {
            assertEquals(biggestWheel, cars.get(0).getWheel());
            assertEquals(smallestWheel, cars.get(cars.size() - 1).getWheel());
        });
    }

    @Test
    public void sortByComponentsQuantityAscending() {
        List<Car> cars = carService.sortByGivenArguments(ArgumentsToSort.COMPONENTS_QUANTITY, false);

        List<Car> carsAfterSorting = carService
                .getCars()
                .stream()
                .sorted(Comparator.comparing(car -> car.getCarBody().getComponents().size()))
                .collect(Collectors.toList());

        assertEquals(carsAfterSorting, cars);
    }

    @Test
    public void sortByComponentsQuantityDescending() {
        List<Car> cars = carService.sortByGivenArguments(ArgumentsToSort.COMPONENTS_QUANTITY, true);

        List<Car> carsAfterSorting = carService
                .getCars()
                .stream()
                .sorted(Comparator.comparing(car -> car.getCarBody().getComponents().size()))
                .collect(Collectors.toList());

        Collections.reverse(carsAfterSorting);

        assertEquals(carsAfterSorting, cars);
    }

    @ParameterizedTest
    @MethodSource("carBodyTypeAndPriceValues")
    public void getCarsByCarBodyTypeAndPrice(CarBodyType carBodyType, double price1, double price2) {
        Set<Car> carsByCarBodyTypeAndPrice = carService.getCarsByCarBodyTypeAndPrice(carBodyType, price1, price2);

        List<Car> collect = carsByCarBodyTypeAndPrice
                .stream()
                .filter(car -> !car.getCarBody().getType().equals(carBodyType))
                .collect(Collectors.toList());

        List<Car> collect1 = carsByCarBodyTypeAndPrice
                .stream()
                .filter(car -> car.getPrice().doubleValue() < price1)
                .collect(Collectors.toList());

        List<Car> collect2 = carsByCarBodyTypeAndPrice
                .stream()
                .filter(car -> car.getPrice().doubleValue() > price2)
                .collect(Collectors.toList());

        assertAll(() -> {
            assertTrue(collect.isEmpty());
            assertTrue(collect1.isEmpty());
            assertTrue(collect2.isEmpty());
        });
    }

    static List<Arguments> carBodyTypeAndPriceValues() {
        return List.of(
                Arguments.of(CarBodyType.COMBI, 40, 140),
                Arguments.of(CarBodyType.HATCHBACK, 50, 150),
                Arguments.of(CarBodyType.SEDAN, 60, 160)
        );
    }

    @ParameterizedTest
    @EnumSource(Type.class)
    public void getCarsByEngineType(Type type) {
        Set<Car> carsByCarBodyTypeAndPrice = carService.getCarsByEngineType(type);

        List<Car> collect = carsByCarBodyTypeAndPrice
                .stream()
                .filter(car -> !car.getEngine().getType().equals(type))
                .collect(Collectors.toList());

        assertTrue(collect.isEmpty());
    }

    @RepeatedTest(10)
    public void createMapWhereKeyIsCarAndValueIsMileage() {
        Map<Car, Integer> carMileageMap = carService.createMapWhereKeyIsCarAndValueIsMileage();

        List<Car> cars = new ArrayList<>(carMileageMap.keySet());

        int randomNumber = new Random().nextInt(cars.size());

        assertEquals(cars.get(randomNumber).getMileage(), carMileageMap.get(cars.get(randomNumber)));
    }

    @ParameterizedTest
    @EnumSource(TyreType.class)
    public void groupByTyreType(TyreType tyreType) {
        Map<TyreType, List<Car>> tyreTypeListMap = carService.groupByTyreType();

        List<Car> carsWithIncorrectTyreType = tyreTypeListMap
                .get(tyreType)
                .stream()
                .filter(car -> !car.getWheel().getType().equals(tyreType))
                .collect(Collectors.toList());

        assertTrue(carsWithIncorrectTyreType.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("componentsForCarValues")
    public void getCarsWithGivenComponents(String component1, String component2) {
        List<Car> carsWithGivenComponents = carService.getCarsWithGivenComponents(List.of(component1, component2));

        System.out.println(carsWithGivenComponents);

        List<Car> carsWithComponents = carsWithGivenComponents
                .stream()
                .filter(car -> car.getCarBody().getComponents().contains(component1)
                        && car.getCarBody().getComponents().contains(component2))
                .collect(Collectors.toList());

        assertEquals(carsWithComponents, carsWithGivenComponents);
    }

    private static List<Arguments> componentsForCarValues() {
        return List.of(
                Arguments.of("ABS", "AIR CONDITIONING"),
                Arguments.of("ABS", "BLUETOOTH"),
                Arguments.of("NAVIGATION", "AIR CONDITIONING")
        );
    }

    @ParameterizedTest
    @EnumSource(ArgumentToStatistics.class)
    public void getStatisticsForCars(ArgumentToStatistics argumentToStatistics) {
        MileageAndPowerPriceStats mileageAndPowerPriceStats = carService.getStatisticsForGivenArgument(argumentToStatistics);

        switch (argumentToStatistics) {
            case POWER -> {
                assertAll(() -> {
                    assertNotNull(mileageAndPowerPriceStats.getPowerStats());
                    assertNotNull(mileageAndPowerPriceStats.getPowerStats().getMax());
                    assertNotNull(mileageAndPowerPriceStats.getPowerStats().getMin());
                    assertNotNull(mileageAndPowerPriceStats.getPowerStats().getAvg());
                });
            }
            case PRICE -> {
                assertAll(() -> {
                    assertNotNull(mileageAndPowerPriceStats.getPriceStats());
                    assertNotNull(mileageAndPowerPriceStats.getPriceStats().getMax());
                    assertNotNull(mileageAndPowerPriceStats.getPriceStats().getMin());
                    assertNotNull(mileageAndPowerPriceStats.getPriceStats().getAvg());
                });
            }
            case MILEAGE -> {
                assertAll(() -> {
                    assertNotNull(mileageAndPowerPriceStats.getMileageStats());
                    assertNotNull(mileageAndPowerPriceStats.getMileageStats().getMax());
                    assertNotNull(mileageAndPowerPriceStats.getMileageStats().getMin());
                    assertNotNull(mileageAndPowerPriceStats.getMileageStats().getAvg());
                });
            }
        }
    }
}