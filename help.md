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