package com.dna.validator;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ValidatorService implements IValidatorService{

    private int matchCounter;
    private String pattern;
    private StringBuilder[] columns;
    private StringBuilder[] diagonals;
    private StringBuilder[] backDiagonals;

    /**
     * Valida si la secuencia de ADN recibida corresponde o no a la de un mutante
     * @param dna arreglo de cadenas que representan una tabla de nxn con la secuencia de ADN
     * @return true si la secuencia corresponde con la de un mutante, false en caso contrario
     */
    public Boolean isMutant(String[] dna) {
        matchCounter = 0;
        if(sequenceValid(dna)){
            return processSequence(dna);
        }else{
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Revisa las filas, columnas y diagonales de la secuencia de ADN ingresada
     * para determinar si corresponde a la de un mutante
     * @param dna arreglo de cadenas que representan una tabla de nxn con la secuencia de ADN
     * @return true si para la entrada encuentra más de una secuencia de cuatro letras iguales
     */
    private boolean processSequence(String[] dna) {
        //Se procesan las filas de la tabla para definir si es mutante
        for (String sequence : dna) {
            matchCounter += containsSequence(sequence);
            if (matchCounter > 1)
                return true;
        }

        //Si en los valores horizontales de la tabla no se define si es mutante,
        //se obtienen las columnas y diagonales de la tabla para revisar en ellas
        getLists(dna);

        //Se procesan las columnas
        for (StringBuilder sequence : columns) {
            matchCounter += containsSequence(String.valueOf(sequence));
            if (matchCounter > 1)
                return true;
        }


        //Si sigue sin definirse si es mutante, se evalúan las diagonales
        for (StringBuilder sequence : diagonals) {
            matchCounter += containsSequence(String.valueOf(sequence));
            if (matchCounter > 1)
                return true;
        }

        //Por último,si sigue sin definirse si es mutante, se evalúan las diagonales invertidas

        for (StringBuilder sequence : backDiagonals) {
            matchCounter += containsSequence(String.valueOf(sequence));
            if (matchCounter > 1)
                return true;
        }


        return matchCounter > 1;
    }

    /**
     * Crea arreglos con los valores verticales y diagonales de la secuencia a revisar
     * @param dna arreglo de cadenas que representan una tabla de nxn con la secuencia de ADN
     */
    public void getLists(String[] dna){
        columns = new StringBuilder[dna.length];
        diagonals = new StringBuilder[(dna.length*2)-1];
        backDiagonals = new StringBuilder[(dna.length*2)-1];
        int min = -dna.length+1;

        for (int i = 0; i< dna.length;i++){
            for (int j = 0; j< dna.length;j++){
                columns[j] =  columns[j] == null ?
                        new StringBuilder(String.valueOf((dna[i].charAt(j)))):
                        columns[j].append(dna[i].charAt(j));
                diagonals[i+j] = diagonals[i+j] == null ?
                        new StringBuilder(String.valueOf((dna[i].charAt(j)))):
                        diagonals[i+j].append(dna[i].charAt(j));
                backDiagonals[i-j-min] = backDiagonals[i-j-min] == null ?
                        new StringBuilder(String.valueOf((dna[i].charAt(j)))):
                        backDiagonals[i-j-min].append(dna[i].charAt(j));
            }
        }
    }

    /**
     * Evalúa si una cadena contiene secuencias de 4 letras iguales
     * @param subSequence cadena a revisar
     * @return la cantidad de secuencias encontradas en la cadena
     */
    private int containsSequence(String subSequence) {
        if (subSequence == null || subSequence.isEmpty() ){
            return 0;
        }else{
            int patternLength = 4;
            getPatternInfo(subSequence.charAt(0), patternLength);
            //si el tamaño de la cadena a evaluar es menor al del patrón buscado, no se evalúa
            if (subSequence.length()<pattern.length()){
                return 0;
            }
        }

        //se evalúa la cadena partiéndola por subcadenas del tamaño del patrón que se busca (m)
        String tmpString = subSequence.substring(0,pattern.length());
        if (tmpString.equals(pattern)) {
            //si los primeros (m) caracteres de la cadena son iguales al patrón,
            //se suma una coincidencia y se evalua el resto de la cadena a partir del caractér m+1
            return 1 + containsSequence(subSequence.substring(pattern.length()));
        }else{
            //si los primeros (m) caracteres de la cadena no son iguales al patrón que se busca,
            //se continúa la búsqueda con el siguiente substring (desde 1 hasta m+2)
            return containsSequence(subSequence.substring(1));
        }

    }

    /**
     * Obtiene el patrón para comparar la cadena de acuerdo con el caracter inicial de ésta
     * @param initial caracter para crear el patrón
     * @param length longitud deseada del patrón
     */
    private void getPatternInfo(char initial, int length){
        pattern = StringUtils.repeat(initial,length);
    }

    /**
     * Valida si la secuencia contiene caracteres que no corresponden con los esperados (A,T,G,C)
     * y si la tabla dada no es de nxn
     * @param dna secuencia de adn a validar
     * @return true si cumple con las condiciones, false en caso contrario
     */
    private boolean sequenceValid(String[] dna) {
        return lengthValid(dna) && contentValid(dna);
    }

    /**
     * Valida que los caracteres en la secuencia correspondan con los esperados (A,T,G,C)
     * @param dna secuencia de adn a validar
     * @return true si contiene únicamente caracteres válidos, false en caso contrario
     */
    private boolean contentValid(String[] dna) {
        Stream<String> stream = Stream.of(dna);
        return stream.collect(Collectors.joining()).matches("[ATGC]+");
    }

    /**
     * Valida si la secuencia recibida tiene columnas de al menos 4 caracteres y forma una matriz cuadrada
     * @param dna secuencia a validar
     * @return true si cumple con las condiciones, false en caso contrario
     */
    private boolean lengthValid(String[] dna) {
        return (dna[0].length() == dna.length) && dna.length > 4;
    }
}
