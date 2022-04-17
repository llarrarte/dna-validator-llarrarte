# dna-validator

La aplicación dna-validator contiene una API Rest que permite detectar si un humano es mutante y consultar mediante un servicio las estadísticas de las verificaciones realizadas

La url para detectar si un humano es mutante es: http://dnavalidator-env.eba-mnmepydc.us-east-1.elasticbeanstalk.com/mutants

Un ejemplo de como consumirlo es el siguiente:

```bash
curl --location --request POST 'http://dnavalidator-env.eba-mnmepydc.us-east-1.elasticbeanstalk.com/mutants' \
--header 'Content-Type: application/json' \
--data-raw '{"dna": [
        "AAAAA",
        "GTGGT",
        "TGGAG",
        "TGATT",
        "GCACT"        
    ]
}'
```

La url para obtener las estadísticas de las revisiones es: http://dnavalidator-env.eba-mnmepydc.us-east-1.elasticbeanstalk.com/stats

Un ejemplo de como consumirlo es el siguiente:

```bash
curl --location --request GET 'http://dnavalidator-env.eba-mnmepydc.us-east-1.elasticbeanstalk.com/stats'
```

En el archivo `validator.postman_collection.json` se encuentra una una colección de Postman para consumir estos servicios. 

## Configuración local del proyecto

El proyecto fue construido con SpringBoot como framework y usa Gradle para manejar las dependencias. 

Para ejecutarlo en un entorno local se debe clonar el repositorio
```bash 
git clone https://github.com/llarrarte/dna-validator-llarrarte.git
```
Una vez clonado, en la ubicación donde se encuentra el proyecto, se corre el comando para descargar las dependencias y compilar el proyecto 
```bash 
gradle build
```
Finalmente, para iniciar el proyecto ejecutar
```bash 
gradle bootRun
```

Se puede validar que el proyecto funciona con la url http://localhost:9095/stats que retorna las estadísticas de las validaciones realizadas.

También se puede utilizar la colección de Postman mencionada anteriormente, modificando la url por http://localhost:9095/







