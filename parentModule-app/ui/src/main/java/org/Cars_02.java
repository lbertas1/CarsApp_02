package org;

import org.example.controllers.Controller;
import org.example.CarService;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.port;

public class Cars_02 {

    public static void main(String[] args) {

        final String FILENAME_PATH = "./resources/data/cars.json";
        var carService = new CarService(FILENAME_PATH);
        var menu = new MenuService(carService);
        menu.menu();

        port(8090);
        initExceptionHandler(e -> System.out.println(e.getMessage()));

        Controller controller = new Controller(carService);
        controller.initRoutes();
    }
}
