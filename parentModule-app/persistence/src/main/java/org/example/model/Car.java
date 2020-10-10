package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {

    private String model;
    private BigDecimal price;
    private Integer mileage;
    private Engine engine;
    private CarBody carBody;
    private Wheel wheel;

    public Car(String fileName) {

    }

    @Override
    public String toString() {
        return String.join("\n",
                "MODEL: " + model,
                "PRICE: " + price,
                "MILEAGE: " + mileage,
                "ENGINE: " + engine.toString(),
                "CARBODY: " + carBody.toString(),
                "WHEEL: " + wheel.toString()
        );
    }
}
