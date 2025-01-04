# volume-insight

## Setup

```bash 
docker-compose up
```

## Run services

```bash
cd volume-tracker
mvn spring-boot:run
```

## Run client

edit this property to ```true``` for init master data, then set it back to ```false```

```bash
app:
  init: false
```

Run client

```bash
cd volume-client
mvn spring-boot:run
```

## Enhancements

* add scheduler job to update volume data when 9:00 AM is missing, the job will run after 9:00 AM
* add column hour to volume data, for faster query data by hour for daily report