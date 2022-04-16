package com.dna.stats;

import com.dna.stats.mapper.DNAMapper;
import com.dna.stats.model.DBStats;
import com.dna.stats.model.StatsRetrieveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsRetrieveService implements IStatsRetrieveService{

    @Autowired
    private DNAMapper dnaMapper;

    @Override
    public StatsRetrieveResponse getStats() {
        DBStats response = dnaMapper.getStats();
        Integer humans = response.getTotal()-response.getMutants();
        Double ratio = Double.valueOf(response.getMutants()/humans);
        return StatsRetrieveResponse.builder().countMutantDNA(response.getMutants())
                .countHumanDNA(humans)
                .ratio(ratio).build();
    }
}
