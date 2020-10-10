package org.example.help;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MileageAndPowerPriceStats {
    private final Stats<BigDecimal> priceStats;
    private final Stats<Double> mileageStats;
    private final Stats<Double> powerStats;
}
