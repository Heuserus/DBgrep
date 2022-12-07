#### commands:

    dbgrep

#### options

| options           | description                                                                                                             |
| ----------------- | ----------------------------------------------------------------------------------------------------------------------- |
| -h, --help        | display help page                                                                                                       |
| -p, --profile     | connection path/profile                                                                                                 |
| -c, --column      | column to be searched, table must be specified [if used without table search in every column the name provided matches] |
| -C, --column-name | search for all column names (keys) of the specified name                                                                |
| [-c, --count ]    | displays result count                                                                                                   |
| -t, --table       | specifies table to be searched                                                                                          |
| -T, --table-name  | specifies a table name to be searched                                                                                   |
| - r, --recursive  | follow foreign keys                                                                                                     |
| , --and           | boolean AND operator specifying optional condition                                                                      |
| , --or            | boolean OR operator specifying optional condition                                                                       |
| , --color         | luca implements                                                                                                         |

dbgrep -t user --and -c "age <= 90 --and -c name != Michael

dbgrep:
 -t: tablename
    -c: columnName
            value
    -C: columnName
-t: tableName
    -c: columnName
-T: tableName




dbgrep -T %==user%                    -> alle tablenames die "user" containen
dbgrep -C %ORDER_NR%                -> alle columnnames (der gesamten db) die ...
dbgrep -t ORDERS -C %NR%            -> alle columnames (in table "ORDERS")
dbgrep -t <%ORDERS% -C %NR%          -> alle columnames die "NR" containen in allen tables die "ORDERS" containen
dbgrep -t %ORDERS% -c %NR%          -> nicht möglich
dbgrep -t ORDERS                    -> alle zeilen/spalten der table ORDERS
dbgrep -t %ORDERS%                  -> alle zeilen/spalten aller tables die "ORDERS" containen
dbgrep -c %NR%                      -> nicht möglich
dbgrep -c %ID% 234567               -> alle zeilen in der gesamten db welche id containen mit dem wert 234567
dbgrep -c ID 234567                 -> alle zeilen in der gesamten db mit columnname id mit dem wert 234567
dbgrep -t ORDER -c ID 234567        -> alle zeilen in der gesamten db mit columnname id mit dem wert 234567
dbgrep -t %ORDER% -c ID 234567      -> alle zeilen in der gesamten db mit columnname id mit dem wert 234567
dbgrep -


## Vereinfachtes User Interface:

`-t --table` und `-c --column` wählt tabelle / column aus.\
Bei konkreter Namensangabe nur diese Tabelle/Column. Bei Pattern alle matches.

`-n --names` **Mit `<word | pattern>`:** inhaltliche suche, aber nur die Namen in denen was gefunden wurde werden returnded. **Ohne `<word | pattern>`:** namenssuche durch gegebenen tabellen/columns


**Syntax:\**
`dbgrep [options] <word | pattern>`

**Definition `pattern`:**
- Angabe von Patterns mit '/': `/pattern/` (javascript notation)
- bei **string**: regex bzw. %-notation von sql
- bei **Zahlen**: range (z.B. /0-5/), kleiner als (z.b. /<5/), größer (z.b. />5/)

**Alle möglichen Kombinationen:**
- `dbgrep <word | pattern>` -> inhaltssuche durch alles
- `dbgrep -n <word | pattern>` -> inhaltssuche durch alles. Nur Tabellen und Col names werden returned.
- `dbgrep -n` -> Error
- `dbgrep -c <pat> <word | pattern>` -> inhaltssuche durch alle col die das pattern matchen nach angegebenen pattern
- `dbgrep -c <pat> -n <word | pattern>` -> inhaltssuche durch alle col die das pattern matchen nach angegebenen pattern. Nur Tabellen und Col names werden returned.
- `dbgrep -c <pat> -n` -> namenssuche sucht alle cols ~= pat aus allen Tabellen
- `dbgrep -t <pat> <word | pattern>` -> inhaltssuche durch alle Tabellen ~= pat nach angegebenem pattern
- `dbgrep -t <pat> -n <word | pattern>` -> inhaltssuche durch alle Tabellen ~= pat nach angegebenem pattern. Nur Tabellen und Col names werden returned.
- `dbgrep -t <pat> -n` -> namenssuche über alle Table names
- `dbgrep -t <pat> -c <pat> <word | pattern>` -> inhaltssuche durch alle cols ~= pat aus allen table ~= pat 
- `dbgrep -t <pat> -c <pat> -n <word | pattern>` -> inhaltssuche durch alle cols ~= pat aus allen table ~= pat. Nur Tabellen und Col names werden returned.
- `dbgrep -t <pat> -c <pat> -n` -> namenssuche durch alle col ~= pat aus allen table ~= pat