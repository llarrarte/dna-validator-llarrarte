package com.dna.stats;

import com.dna.stats.mapper.DNAMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StatsSaveServiceTest {

    @Mock
    DNAMapper dnaMapper;

    @InjectMocks
    StatsSaveService statsSaveService;

    @Test
    public void saveSequence() {

        String[] mutantDNA = {"AAAAA", "GTGGT", "TGGAT", "TGATT", "ACACT"};
        statsSaveService.save(mutantDNA,true);

        assertDoesNotThrow(()->{});
    }


}
