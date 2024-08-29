FROM gradle:8.5-jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle installDist

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/build/install/app /app

CMD ["./bin/app"]
