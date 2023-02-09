package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/JDBC_Practice";
    static final String USER = "postgres";
    static final String PASS = "joinIsDificult558";

    private Connection connection = null;

    public DBConnection(){
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e){
            System.err.println("Driver is not found");
            e.printStackTrace();
        }
        try{
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connection successfull!");
        }
        catch (SQLException e){
            System.err.println("Connection failed");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }


}
