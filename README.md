# DBgrep: A Java-based Database Search Tool

DBgrep is a command-line application that allows you to search through databases using multiple parameters. It's similar to the grep command, but instead of searching through text files, it searches through databases, making it a powerful tool for data analysis and querying.

## Requirements

DBgrep is built using Java. You'll need to have Java 16 or later installed on your system to run the application. You'll also need to have Maven installed if you want to build the application from source.

## Installation

To install DBgrep, you can download the latest release in our the GitHub repository. Alternatively, you can clone and build the application from source.


## Setting up a profile

To use DBgrep, you'll need to set up a connection to your database. This is either possible using ``profile.yml`` file or by providing the information as arguments. Providing a profile ``AND`` the database properties as arguments at the same time is not possible.

### Connection profile

A profile is a configuration file that contains the information needed to connect to a database. You can create a profile following the syntax below. After creating the profile.yml file you are able to use it via the commandline by providing the ``--profile <path/to/profile.yml>`` argument and specifying the profile path.

```yaml
driver: drivers\mariadb-java-client-3.1.0.jar
host: localhost
port: 3306
dbname: dbgrep
username: root
password: example
protocol: jdbc:mariadb
```

For MongoDB, the syntax is as follows:

```yaml
host: localhost
port: 27017
dbname: dbgrep
username: root
password: example
protocol: mongodb
```

A driver is not needed for MongoDB.

### Connection Properties as commandline arguments

Alternatively, you can also set up a database connection by providing the required information as commandline arguments. When doing so, the ``--profile`` argument is not required.

```shell
--dbname=<dbname>
--driver=<driver>
--host=<host>
--password=<password>
--port=<port>
--protocol=<protocol>
--username=<username>
```

### Connection Details

```shell
-p, --profile=<profile>  connection path/profile
```

## Usage
Once you've installed DBgrep, you can use it to search through databases. The basic syntax of the commands is as follows:

### Search:

```
-c, --column=<pattern>                  Search through the column names for the provided pattern.
-c, --column=<column name> <pattern>    Search through the values of the specified column name.
-t, --table=<table>                     Specifies table to be searched.
```

### Supported syntax

```
= (equals)
!= (not equals)
< (less than)
> (greater than) 
+ (exact match)
- (substring match)

There must not be spaces after operators or the expression must be written in quotation marks
```

If you get lost you can use `dbgrep --help` to get a list of all available commands and options.

``` shell
dbgrep --help
```

### Examples

Search for all table names beginning with a capital "K":

```shell
java --jar ./dbgrep.jar -p "path/to/profile.yml" -t "K.*" # for MongoDB
java --jar ./dbgrep.jar -p "path/to/profile.yml" -t "K" # for SQL databases
```

Search for all column names in containing named something with "price" in all tables beginning with "K":

```shell
java --jar ./dbgrep.jar -p "path/to/profile.yml" -t "K.*" -c ".*price.*" # for MongoDB
java --jar ./dbgrep.jar -p "path/to/profile.yml" -t "K" -c "price" # for SQL databases
```

Search for values of the column "price" in the Table "laptops" which are smaller than 500:

```shell
java --jar ./dbgrep.jar -p "path/to/profile.yml" -t "laptops" -c "price" "<500"
```

## Setting up a test database

Docker and Docker-Compose need to be installed for the following steps.

To start up a MongoDB, a MariaDB and a Postgresql database you can use the provided docker-compose file.
Either execute:

```shell
docker-compose -f ./docker/docker-compose.yml up
```

Or use the provided startup script:

```shell
./start_db.sh
```

It is possible to insert some test data in these databases using the provided `FillDB.jar`.
Simply execute:

```shell
java -jar ./FillDB.jar
```

## Dependencies

DBgrep relies on several external libraries, the most important ones are:

- Junit 5
- Picocli
- Jackson
- Apache Commons Collections
- MongoDB Driver
- PostgreSQL Driver


## License
DBgrep is released under the MIT License.
