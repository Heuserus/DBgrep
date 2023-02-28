# DBgrep

[Llama Ascii Art](https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.redbubble.com%2Fde%2Fi%2Fposter%2FASCII-Alpaka-BW-von-Yincinerate%2F45126625.LVTDI&psig=AOvVaw3m62gTMJwkYWKZ-GBM843v&ust=1666860064188000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCKCVxeG__foCFQAAAAAdAAAAABAE)

## Features / project boundaries

- Implementation in Java
- CLI spec (original grep command to get some inspirations)
- dbgrep --profile myMaria ...
- Dynamic Jdbc driver loading
- dpgrep --help profile
- testdaten
- relational + Nosql
- Search scope: --table Article --column ..
- Result presentation: Data, Tablnames, just tablename + count
- dbgrep ... --regexp "Schm%dt"   --column amount --type int --range 200:300

Profile file `myMaria.cfg`:

```
jdbcurl=jdbc:mysql//someserver.com:3306/myDb
driver=mysql/mysql-connector-java/6.0.6/mysql-connector-java-6.0.6.jar
user=hans
password=wurst
```

## Documentation

- Enduser
- Software

## MVP

- Zugriff auf NoSQL und SQL Databases
- Profile System
- CLI + Result Presentation
- One SQL and one NoSQL Database for testing purposes
- High Unit Test Coverage
- Dynamic Driver Loading
- Enduser Documentation ( Help Commands )
- Javadocs
- Simple Regexp (nur % und das was mongodb unterstützt)

## Addons

- Read The Docs
- Profile Creation Edit in CLI (Vorlagensystem)
- Regexp (Clientside)
- Hesliche ASCII ART (yaml backflip)

## Milestone 1

- Postgres und MongoDB auflegen (Simon)
- Projekt Archistruktur erstellen (Alle) -> done
- Basic Abfrage an PostgresDB (Ben)
- Basic Abfrage an MongoDB (Luca)
- CLI Logik festlegen (Marco)

## Milestone 2

- Profile System + CLI
- Detailed Datenbankabfragen + CLI
- Dynamic Driver Loading (Simon)
- Ab Milestone 2 Unit Tests
- Tests auch auf linux

## Milestone 3

- Probleme and Bug fixes
- Fancy Result presentation und Help System
- Deployment + Enduser Tests (Auch auf linux)
- (Abfragen die wir aus Milestone 2 verschoben haben) + Regexp

## Already done

- DB accesss for MongoDB and Postgres [partly]
- Result representation [possibly needs to be extended]
- One SQL and one NoSQL Database for testing purposes
- Dynamic Driver Loading [done but not in production code]

## Remaining Tasks

- [ ] Map argument string to queries in MongoDB [Luca]\
  ![69%](https://progress-bar.dev/69)
  - [ ] Simple Regexp (nur % und das was mongodb unterstützt)  [Luca] \
    ![0%](https://progress-bar.dev/0)
- [ ] Map argument string to queries in Postgres [Ben] \
  ![7%](https://progress-bar.dev/7)
  - [ ] Simple Regexp (nur % und das was mongodb unterstützt)  [Ben] \
    ![0%](https://progress-bar.dev/0)
- [ ] implement validation of passed CL arguments [Marco] \
  ![70%](https://progress-bar.dev/70)
- [x] add dynamic driver loading to production code [Simon] \
  ![100%](https://progress-bar.dev/100)
- [ ] User Documentation [Marco & Simon] \
  ![80%](https://progress-bar.dev/80)
- [ ] Project Documentation (Java Docs) [all of us]\
  ![13%](https://progress-bar.dev/13)
- [ ] High Unit Test Coverage [all of us]\
  ![7%](https://progress-bar.dev/7)
- [ ] testdaten [all of us]\
  ![40%%](https://progress-bar.dev/40)
