package com.dna.stats;

import com.dna.stats.model.StatsRetrieveResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    IStatsRetrieveService statsRetrieveService;

    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - la consulta fue exitosa"),
            @ApiResponse(code = 500, message = "Internal server error - Se presentó un error inesperado")
    })
    public ResponseEntity<StatsRetrieveResponse> getStats(){
        try{
           return ResponseEntity.status(HttpStatus.OK).body(statsRetrieveService.getStats());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}