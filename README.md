# DBgrep

https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.redbubble.com%2Fde%2Fi%2Fposter%2FASCII-Alpaka-BW-von-Yincinerate%2F45126625.LVTDI&psig=AOvVaw3m62gTMJwkYWKZ-GBM843v&ust=1666860064188000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCKCVxeG__foCFQAAAAAdAAAAABAE

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

MVP
Zugriff auf NoSQL und SQL Databases
Profile System
CLI + Result Presentation
One SQL and one NoSQL Database for testing purposes
High Unit Test Coverage
Dynamic Driver Loading
Enduser Documentation ( Help Commands )

Addons
Read The Docs
Profile Creation in CLI
Hesliche ASCII ART

Milestones
