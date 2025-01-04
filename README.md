# volume-insight

## Setup

```bash 
docker-compose up
```

## Run services
We're using Spring Boot, Java 17 to run services, and Flyway to manage database schema. To run services, you can use maven command

```bash
cd volume-tracker
mvn spring-boot:run
```

## Run client

Set this property to ```true``` to init master data

```bash
app:
  init: false
```

Run client to create master data

```bash
cd volume-client
mvn spring-boot:run
```

Set this property to ```false``` to test the API.

```bash
cd volume-client
mvn spring-boot:run
```

## Enhancements

* Add column hour to volume data, for faster query data by hour for daily report
* Support paging for daily report
* Support distributed scheduler for job