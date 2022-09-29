package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection(){
        try{
            return DriverManager.getConnection(String.format(
                    "jdbc:postgresql://%s:%s/%s?user=%s&password=%s",
                    System.getenv("HOST"),
                    System.getenv("PORT"),
                    System.getenv("DB"),
                    System.getenv("USER"),
                    System.getenv("PASS")
            ));
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        System.out.println(createConnection());
    }

}
