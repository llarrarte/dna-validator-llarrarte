package com.dna.stats;

import com.dna.stats.mapper.DNAMapper;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service
public class StatsSaveService implements IStatsSaveService {


    final Logger logger = LoggerFactory.getLogger(StatsSaveService.class);

    @Autowired
    private DNAMapper dnaMapper;

    @Override
    public void save(String[] dna, boolean isMutant) {
        try {
            //La secuencia se va a almacenar como JSON en la BD
            Gson gson = new Gson();
            String dnaJson = gson.toJson(dna);

            //Para garantizar que una secuencia procesada se guarde una sola vez, la primary key del registro en la BD
            //se genera a partir del contenido del arreglo usando SHA256
            String sha256hex = DigestUtils.sha256Hex(dnaJson);

            dnaMapper.save(sha256hex, dnaJson, isMutant);
        }catch(DuplicateKeyException e){
            logger.info("La secuencia ya se encuentra en la base de datos");
        }
    }
}
