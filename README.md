# DBgrep: A Java-based Database Search Tool

DBgrep is a command-line application that allows you to search through databases using multiple parameters. It's similar to the grep command, but instead of searching through text files, it searches through databases, making it a powerful tool for data analysis and querying.

## Requirements
Requirements
DBgrep is built using Java. You'll need to have Java 16 or later installed on your system to run the application. You'll also need to have Maven installed if you want to build the application from source.

## Installation

To install DBgrep, you can download the latest release in our the GitHub repository. Alternatively, you can clone and build the application from source.


## Setting up a profile

To use DBgrep, you'll need to set up a profile in a YAML file. A profile is a configuration file that contains the information needed to connect to a database. You can create a profile following the syntax below:

```
driver: drivers\mariadb-java-client-3.1.0.jar
host: localhost
port: 3306
dbname: dbgrep
username: root
password: example
protocol: jdbc:mariadb
```

For MongoDB, the syntax is as follows:

```
host: localhost
port: 27017
dbname: dbgrep
username: root
password: example
protocol: mongodb
```

A driver is not needed for MongoDB.

## Usage
Once you've installed DBgrep, you can use it to search through databases. The basic syntax of the commands is as follows:

### Connection Properties

```
--dbname=<dbname>
--driver=<driver>
--host=<host>
--password=<password>
--port=<port>
--protocol=<protocol>
--username=<username>
```
### Connection Details

```
-p, --profile=<profile>  connection path/profile
```
### Search:

```
-c, --column=<columns>  column to be searched, table must be specified [if used without table search in every column the name provided matches]
-t, --table=<table>     specifies table to be searched
```
### Supported syntax

```
= (equals)
!= (not equals)
< (less than)
> (greater than) 
+ (exact match)
- (substring match)
```

If you get lost you can use dbgrep --help to get a list of all available commands and options.

```
dbgrep --help
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
