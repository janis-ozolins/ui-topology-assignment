## Implementation details

REST API of network topology.
* No persistence of network (restarting process will reset to empty network)
* Single network stored
* No session

## Prerequisites

* Java 8+ to run Gradle.
* Available 8080 port

## Running

```bash
./gradlew rest-api:bootRun
```

### Test it

```bash
./gradlew test
```

## Documentation

See swagger ui for API details: http://localhost:8080/swagger-ui/index.html
