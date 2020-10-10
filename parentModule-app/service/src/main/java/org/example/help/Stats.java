package org.example.help;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stats<T> {
    private T min;
    private T max;
    private T avg;
}
