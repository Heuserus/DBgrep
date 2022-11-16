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

dbgrep -t user --and age <= 90 --and name != Michael
