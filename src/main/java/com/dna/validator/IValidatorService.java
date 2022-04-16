package com.dna.validator;

public interface IValidatorService {

    Boolean isMutant(String[] dna);

    boolean sequenceValid(String[] dna);
}
