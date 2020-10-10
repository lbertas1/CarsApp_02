package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enums.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Engine {

    private Type type;
    private Double power;

    @Override
    public String toString() {
        return String.join("\n\t",
                "ENGINE TYPE: " + type.name(),
                "POWER: " + power);
    }
}
