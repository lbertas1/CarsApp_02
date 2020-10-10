package org;

import org.example.data.UserData;
import lombok.RequiredArgsConstructor;
import org.example.CarService;
import org.example.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class MenuService {
    private final CarService carService;

    private int chooseOption() {
        System.out.println("Menu:");
        System.out.println("1. Show cars");
        System.out.println("2. Sort cars");
        System.out.println("3. Get cars by car body type and price");
        System.out.println("4. Group cars by engine type");
        System.out.println("5. Group statistics for given argument");
        System.out.println("6. Get cars and their mileage");
        System.out.println("7. Group by tyre type");
        System.out.println("8. Get cars by components");
        System.out.println("0. End of app");
        return UserData.getInt("Choose option:");
    }

    public void menu() {

        int option;
        do {
            option = chooseOption();
            switch (option) {
                case 1 -> option1();
                case 2 -> option2();
                case 3 -> option3();
                case 4 -> option4();
                case 5 -> option5();
                case 6 -> option6();
                case 7 -> option7();
                case 8 -> option8();
                case 0 -> {
                    System.out.println("Have a nice day!");
                    return;
                }
                default -> System.out.println("No such option");
            }

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    private void option1() {
        System.out.println(carService.cars());
    }

    private void option2() {
        var sortedCars = carService.sortByGivenArguments(
                UserData.getArgumentToSort(),
                UserData.getBoolean("Descending?"));
        System.out.println(sortedCars);
    }

    private void option3() {
        var cars = carService.getCarsByCarBodyTypeAndPrice(
                UserData.getCarBodyType(),
                UserData.getDouble("Enter first range"),
                UserData.getDouble("Enter second range")
        );
        System.out.println(cars);
    }

    private void option4() {
        var cars = carService.getCarsByEngineType(
                UserData.getEngineType()
        );
        System.out.println(cars);
    }

    private void option5() {
        var cars = carService.getStatisticsForGivenArgument(
                UserData.getArgumentForStatistics()
        );
        System.out.println(cars);
    }

    private void option6() {
        Map<Car, Integer> mapWhereKeyIsCarAndValueIsMileage = carService.createMapWhereKeyIsCarAndValueIsMileage();
        mapWhereKeyIsCarAndValueIsMileage.forEach((car, integer) -> System.out.println("CAR: " + car + " MILEAGE: " + integer));
    }

    private void option7() {
        carService.groupByTyreType().forEach((tyreType, cars) -> {
            System.out.println("Tyre type: " + tyreType + "\n");
            cars.forEach(car -> System.out.println("\t" + car + "\n"));
        });
    }

    private void option8() {
        List<String> components = new ArrayList<>();
        boolean isEnd = false;
        do {
            components.add(UserData.getString("Provide component"));
            isEnd = UserData.getBoolean("Is that all?");
        } while (!isEnd);

        System.out.println(carService.getCarsWithGivenComponents(
                components
        ));
    }
}
