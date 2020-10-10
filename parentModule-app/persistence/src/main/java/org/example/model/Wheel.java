package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enums.TyreType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wheel {

    private String model;
    private Long size;
    private TyreType type;

    @Override
    public String toString() {
        return String.join("\n\t",
                "WHEEL MODEL: " + model,
                "WHEEL SIZE: " + size,
                "TYRE TYPE: " + type.name());
    }
}
