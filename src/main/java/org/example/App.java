package org.example;



import org.example.db.DBConnection;
import org.example.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class App
{

    public static void main( String[] args ) {
        DBConnection dbConnection = new DBConnection();
        Scanner sc = new Scanner(System.in);
        String query;
        String command;
        //String userData = "";
        do{
            System.out.println("Please choice 1 option from this list: ");
            System.out.println("1 - Insert new User");
            System.out.println("2 - Update an existing user");
            System.out.println("3 - Delete user");
            System.out.println("4 - Show users");
            command = sc.next();

            if(command.equals("1")){
                User user = new User();
                System.out.println("Enter user name: ");
                user.setName(sc.next());
                System.out.println("user name: " + user.getName());
                System.out.println("Enter user surname: ");
                user.setSurname(sc.next());
                System.out.println("user surname: " + user.getSurname());
                System.out.println("Enter user age: ");
                user.setAge(sc.nextInt());
                System.out.println("user age: " + user.getAge());
                System.out.println("Enter user email: ");
                user.setEmail(sc.next());
                System.out.println("user email: " + user.getEmail());

                query = "insert into users(name,surname,age,email) values(?,?,?,?)";
                try(PreparedStatement statement = dbConnection.getConnection().prepareStatement(query)){
                    statement.setString(1,user.getName());
                    statement.setString(2,user.getSurname());
                    statement.setInt(3,user.getAge());
                    statement.setString(4,user.getEmail());
                    statement.execute();
                }
                catch (SQLException e){
                    System.err.println("problem with user dates!");
                }

            }
        }
        while(!command.equals("stop"));
        try{
            dbConnection.getConnection().close();
            System.out.println("connection was closed!");
        }
        catch (SQLException e){
            System.err.println("dbconnection was not closed");
        }
    }
}
