CREATE TABLE IF NOT EXISTS dna (
  id VARCHAR(256) NOT NULL,
  sequence JSON NOT NULL,
  ismutant TINYINT NOT NULL,
  PRIMARY KEY (`id`)
  );