package org.example;



import org.example.db.DBConnection;
import org.example.models.User;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
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



                try(ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream("user.txt"))){
                    objOut.writeObject(user);
                }
                catch (FileNotFoundException e){
                    System.err.println("file not found!");
                }
                catch (IOException e){
                    System.err.println("io exception!");
                }

            }

            else if(command.equals("2")){
                query = "select * from users";
                try(Statement st = dbConnection.getConnection().createStatement()){
                    try(ResultSet rs = st.executeQuery(query)) {
                        System.out.println("------------------------------------------------");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(5) + " | " + rs.getString(3) + " | " + rs.getString(4));
                            System.out.println("------------------------------------------------");
                        }
                    }
                }
                catch (SQLException e){
                    System.err.println("problem with query!");
                    e.printStackTrace();
                }

                System.out.println("Enter user id which you want to change: ");
                int userId = sc.nextInt();

                try(ObjectInputStream objIn = new ObjectInputStream( new FileInputStream("user.txt"))){

                    User user = null;
//                    boolean flag = true;
//                    while(flag) {
//                        do{
//                            user = (User) objIn.readObject();
//                            if (user.getId() == userId) {
//                                flag = false;
//                                break;
//
//                            }
//                        }while(user!=null);
//
//                        if(flag) {
//                            System.out.println("Enter correct user id(if u want to exit from update user enter -1): ");
//                            userId = sc.nextInt();
//                        }
//                    }

                    while((user = (User)objIn.readObject()) != null){
                        if(user.getId() == userId){
                            break;
                        }
                    }

                    if(user !=null) {
                        System.out.println("user name: " + user.getName());
                        System.out.println("Enter user name: ");
                        user.setName(sc.next());

                        System.out.println("user surname: " + user.getSurname());
                        System.out.println("Enter user surname: ");
                        user.setSurname(sc.next());

                        System.out.println("user age: " + user.getAge());
                        System.out.println("Enter user age: ");
                        user.setAge(sc.nextInt());

                        System.out.println("user email: " + user.getEmail());
                        System.out.println("Enter user email: ");
                        user.setEmail(sc.next());
                        System.out.println("  ");

                        query = "update users set name = ?, surname = ? , age = ?, email = ? where id = ? ";

                        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(query)) {
                            statement.setString(1, user.getName());
                            statement.setString(2, user.getSurname());
                            statement.setInt(3, user.getAge());
                            statement.setString(4, user.getEmail());
                            statement.setInt(5, userId);
                            statement.execute();
                        } catch (SQLException e) {
                            System.err.println("problem with user dates!");
                        }
                    }
                }
                catch (FileNotFoundException e){
                    System.err.println("file not found!");
                    e.printStackTrace();
                }
                catch (IOException e){
                    System.err.println("io exception");
                    e.printStackTrace();
                }
                catch (ClassNotFoundException e){
                    System.err.println("class not found!");
                    e.printStackTrace();
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
