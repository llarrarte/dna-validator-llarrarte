package com.dna.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsRetrieveResponse {
    @JsonProperty("count_mutant_dna")
    Integer countMutantDNA;
    @JsonProperty("count_human_dna")
    Integer countHumanDNA;
    Double ratio;
}
