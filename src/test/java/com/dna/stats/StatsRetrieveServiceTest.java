package com.dna.stats;

import com.dna.stats.mapper.DNAMapper;
import com.dna.stats.model.DBStats;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StatsRetrieveServiceTest {

    @Mock
    DNAMapper dnaMapper;

    @InjectMocks
    StatsRetrieveService statsRetrieveService;

    @Test
    public void getStats() {
        Mockito.when(dnaMapper.getStats()).thenReturn(DBStats.builder().mutants(5).total(100).build());

        statsRetrieveService.getStats();

        double ratio = statsRetrieveService.getStats().getCountMutantDNA() / statsRetrieveService.getStats().getCountHumanDNA();

        assertEquals(5,statsRetrieveService.getStats().getCountMutantDNA());
        assertEquals(95, statsRetrieveService.getStats().getCountHumanDNA());
        assertEquals(ratio, statsRetrieveService.getStats().getRatio());
    }

}
