#Technical Assignment

## Requirements

1. A aplicação deve aceitar a importação de um arquivo de tweets. O arquivo a ser enviado para a sua API é o de trump.csv 
  disponível neste repositório na pasta data, e nós utilizaremos o seguinte comando para a importação:
```
curl -X POST https://<path/to/app> -H 'cache-control: no-cache' -H 'content-type: multipart/form-data' -F data=@trump.csv
```

2. A aplicação deve persistir os dados em uma banco de dados (de sua escolha).
   
3. A aplicação deve disponibilizar um endpoint para consultas de tweets
    * Este endpoint tem o requisito mínimo de aceitar os filtros de `id`, `date`, `target`, `insult` e `tweet`
    * Os filtros de `insult` e `tweet` devem aceitar buscas de textos parciais (`ilike`).
    
4. Entregue a aplicação usando Docker, container e um arquivo docker-compose, tornando fácil para nós executarmos a 
   aplicação e os testes.
   
5. Quanto menor o uso de memória RAM, melhor! Faremos testes de carga para avaliar sua aplicação.

4. Desenvolva sua aplicação usando Java e frameworks são permitidos.


### Required:
* JDK 11
* Docker
* Docker-compose

### How to run:
- Tests:
    - `./mvnw clean test`
- Build:
    - Jar with maven: `./mvnw clean package`. Jar available at `./target/data-processing-0.0.1-SNAPSHOT.jar`.
    - Docker image: make sure docker is running and `./mvn clean spring-boot:build-image`
- The app:
    - `docker-compose up [-d]`
    
### Available endpoints
```
curl -X POST 'localhost:8080/tweets' \
-H 'Content-Type: multipart/form-data' \
-F 'data=@"/path/to/file.csv"'
```
```
curl 'localhost:8080/tweets?id=1&date=2014-10-09&target=politicians&insult=action&tweet=crowd'
```