package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.CarService;
import org.example.data.JsonTransformer;
import org.example.enums.ArgumentToStatistics;
import org.example.enums.ArgumentsToSort;
import org.example.model.enums.CarBodyType;
import org.example.model.enums.Type;

import static spark.Spark.get;
import static spark.Spark.path;

@RequiredArgsConstructor
public class Controller {

    private final CarService carService;

    public void initRoutes() {
        path("/get-cars", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                return carService.cars();
            }), new JsonTransformer());
        });

        path("/sort-by-item", () -> {
            get("/:sortItem/:order", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                ArgumentsToSort argumentsToSort = ArgumentsToSort.valueOf(request.params("sortItem"));
                boolean order = Boolean.parseBoolean(request.params("order"));
                return carService.sortByGivenArguments(argumentsToSort, order);
            }), new JsonTransformer());
        });

        path("/cars-by-bodyType-and-price-range", () -> {
            get("/:carBodyType/:price1/:price2", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                CarBodyType carBodyType = CarBodyType.valueOf(request.params("carBodyType"));
                double price1 = Double.parseDouble(request.params("price1"));
                double price2 = Double.parseDouble(request.params("price2"));
                return carService.getCarsByCarBodyTypeAndPrice(carBodyType, price1, price2);
            }), new JsonTransformer());
        });

        path("/cars-by-engine-type", () -> {
            get("/:type", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                return carService.getCarsByEngineType(Type.valueOf(request.params("type")));
            }), new JsonTransformer());
        });

        path("/statistics", () -> {
            get("/:argument", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                carService.getStatisticsForGivenArgument(ArgumentToStatistics.valueOf(request.params("argument")));
                return null;
            }), new JsonTransformer());
        });

        path("/car-mileage-map", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                carService.createMapWhereKeyIsCarAndValueIsMileage();
                return null;
            }), new JsonTransformer());
        });

        path("/cars-grouped-by-tyre-type", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                carService.groupByTyreType();
                return null;
            }), new JsonTransformer());
        });
    }
}
