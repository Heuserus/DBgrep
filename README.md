# DBgrep

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
