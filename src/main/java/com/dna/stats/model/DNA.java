package com.dna.stats.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DNA {
    private String[] dna;
    private boolean isMutant;
}
