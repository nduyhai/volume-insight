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

* add column hour to volume data, for faster query data by hour for daily report
* support paging for daily report
* support distributed scheduler for job