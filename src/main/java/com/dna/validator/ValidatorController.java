package com.dna.validator;

import com.dna.stats.IStatsSaveService;
import com.dna.validator.model.ValidateRequest;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/mutants")
public class ValidatorController {

    @Autowired
    IValidatorService validatorService;

    @Autowired
    IStatsSaveService statsSaveService;

    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - indica que el ADN corresponde al de un mutante"),
            @ApiResponse(code = 400, message = "Bad request - La secuencia enviada no es correcta"),
            @ApiResponse(code = 403, message = "Forbidden - La secuencia de ADN no corresponde con la de un mutante"),
            @ApiResponse(code = 500, message = "Internal server error - Se presentó un error inesperado")
    })
    public ResponseEntity validate(@RequestBody ValidateRequest request){
        try{
            boolean response;
            if (validatorService.sequenceValid(request.getDna())){
                response = validatorService.isMutant(request.getDna());
                statsSaveService.save(request.getDna(),response);
            return response ?
                    ResponseEntity.status(HttpStatus.OK).build() :
                    ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }else{
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}