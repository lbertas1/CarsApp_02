package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enums.CarBodyColor;
import org.example.model.enums.CarBodyType;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarBody {

    private CarBodyColor color;
    private CarBodyType type;
    private List<String> components;

    @Override
    public String toString() {
        return String.join("\n\t",
                "CAR BODY COLOR: " + color.name(),
                "CAR BODY TYPE: " + type.name(),
                "COMPONENTS: " + components.stream().collect(Collectors.joining(", ")));
    }
}
