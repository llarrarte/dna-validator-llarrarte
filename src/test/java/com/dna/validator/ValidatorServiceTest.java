package com.dna.validator;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ValidatorServiceTest {

    @InjectMocks
    ValidatorService validatorService;

    @Test
    void isMutant() {
        String[] mutantDNA = {"AAAAA", "GTGGT", "TGGAT", "TGATT", "ACACT"};
        assertEquals(true, validatorService.isMutant(mutantDNA));
    }

    @Test
    void isNotMutant() {
        String[] humanDNA = {"AAAAA", "GTGGA", "TGGAT", "TGATT", "ACACT"};
        assertEquals(false, validatorService.isMutant(humanDNA));
    }

    @Test
    void isValidSequence() {
        String[] validDNA = {"GTGG", "TGGA", "TGAT", "ACAC"};
        assertEquals(true, validatorService.sequenceValid(validDNA));
    }

    @Test
    void notValidSequence() {
        String[] validDNA = {"GTGGT", "TGGA", "TGAT", "ACAC"};
        assertEquals(false, validatorService.sequenceValid(validDNA));
    }
}
