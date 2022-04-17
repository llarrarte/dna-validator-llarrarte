package com.dna.stats.mapper;

import com.dna.stats.model.DBStats;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DNAMapper {

    @Select("WITH T1 AS ( SELECT isMutant FROM dna ) SELECT (SELECT COUNT(*) FROM T1) AS total, (SELECT COUNT(*) FROM T1 WHERE isMutant = true) AS mutants")
    DBStats getStats();

    @Insert("INSERT INTO dna VALUES(#{id}, #{dna}, #{isMutant})")
    void save(String id, String dna, boolean isMutant);
}
