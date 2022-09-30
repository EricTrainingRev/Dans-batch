# SQL
### Data Definition Language
```sql
--these are the commands that let us set up and alter tables within our database

--create allows us to make new tables. We define the columns and their types inside parenthesis
create table simple_table(
	first_name varchar(50),
	last_name varchar(50)
);

--alter lets us make changes to tables and their columns
alter table simple_table add column person_id serial;

--truncate will remove all the data within a table without deleting the table itself, as long as their are no constraint issues
truncate table simple_table;

--drop will delete a table and its data, as long as their are no constraint issues
drop table simple_table;
```

### Data Manipulation Language
```sql
--these are the CRUD operators (Create, Read, Update, Delete)
    -- some people consider the insert statement to fall under Data Query Language (DQL)

--insert is used to add data into a table
insert into names values(default, 'Bruce', 'Wayne');

--update is used to change data in a table
update names set first_name = 'Alfred' where person_id = 1;

--select is used to get data from a table. This is sometimes categorized under DQL
select * from names where person_id = 1;

--delete is used to remove data from a table
delete from names where person_id = 1;
```

### Data Control Language
```sql
--these commands manage users and privliges on the database

--this creates a basic user with the name test with password test. Useful for allowing limited access
CREATE ROLE tester NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN PASSWORD '1234';

--this allows anyone who logs in as test to perform all actions on the given table
GRANT ALL ON TABLE practice.names TO tester;

--this removes all rights to perform actions on the table from test user
REVOKE ALL ON TABLE practice.names FROM tester;
```

### Transaction Control Language
```sql
--these commands can start, end, and rollback transactions. Use them if you want fine control over your queries

--this starts a new transaction
begin;
	insert into names values(default, 'Clark', 'Kent');
    --this creates a spot we can rollback to if needed
	savepoint my_savepoint;
	insert into names values(default, 'Lois', 'Lane');
    --this will rollback the transaction to the save point, so we lose the Sherri info
	rollback to savepoint my_savepoint;
    --this removes the savepoint, good pratice
	release savepoint my_savepoint;
--this will commit the transaction, can also use end
commit; -- or end
```

# JDBC
```xml
        <!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.2.22</version> <!-- version may change -->
        </dependency>
    </dependencies>
```

```java

package com.revature.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
    This ConnectionUtil class is used to handle creating a connection object for us to interact with our
    database. It includes a method that utilizes a properties file and one that uses environment variables
    to abstract database credentials away from the developer. Make sure you don't accidentally upload any
    sensitive data to your central repository, especially if said repository is public.
 */

public class ConnectionUtil {

    public static Connection createConnectionUsingPropertiesFile(){
        /*
            This method requires a properties file to work correctly. The properties file should have the following key/value pairs:
            url=jdbc:postgresql://{host-url}:{port #s}/
            user={username}
            password={password}
        */
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/creds.properties"))) {
            Properties creds = new Properties();
            creds.load(input);
            return DriverManager.getConnection(
                    creds.getProperty("url"),
                    creds.getProperty("user"),
                    creds.getProperty("password")
            );
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection createConnectionUsingEnvironmentVariables(){
        // this version matches the example application
        try {
            return DriverManager.getConnection(String.format(
                    "jdbc:postgresql://%s:%s/%s?user=%s&password=%s",
                    System.getenv("HOST"),
                    System.getenv("PORT"),
                    System.getenv("NAME"),
                    System.getenv("USER"),
                    System.getenv("PASSWORD")
            ));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(createConnectionUsingPropertiesFile());
        System.out.println(createConnectionUsingEnvironmentVariables());
    }

}
```

### JDBC
- Statement
    - use with static queries (no data needs to be passed into sql)
- PrepparedStatement
    - use with dynamic quieries (need to pass data into sql)
- Connection
    - used to create a connection with the database
    - usin in a try-with-resource block to auto close it
- ResultSet
    - used to interact with the results of your queries
        - need to call the next() method one time before data can be interacted with