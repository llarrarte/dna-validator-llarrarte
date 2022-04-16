package com.dna.stats.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DBStats {
    private int total;
    private int mutants;
}
