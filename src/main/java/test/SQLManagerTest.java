package test;

import data.persistence.SQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * File: SQLManagerTest.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SQLManagerTest {
    public static void main(String[] args) {
        int successes = 0, failures = 0;
        int tmp = 0;
        // Test calls
        tmp = testSQLConnection() ? successes++ : failures++;
        tmp = testCreateUserInTable() ? successes++ : failures++;
        //tmp = testSelectFromTable() ? successes++ : failures++;
        System.out.println("SQLManager Test Results: "
                + successes + " succeeded, " + failures + " failed.");
    }

    /**
     * Simply test whether or not a connection to the database can be established.
     * @return true/false = pass/fail
     */
    private static boolean testSQLConnection(){
        System.out.println("Testing PostgreSQL...");
        try (Connection connection = SQLManager.getConnection()){
            SQLManager.getConnection();
            System.out.println("\tConnection successful.");
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("\tConnection failed.");
            return false;
        }
    }

    /**
     * Test if all the rows in table 'test' can be accessed
     * @return true/false = pass/fail
     */
    private static boolean testSelectFromTable(){
        System.out.println("Testing SELECT * FROM table_name");
        try (Connection connection = SQLManager.getConnection()){
            ResultSet rs = connection.prepareStatement("SELECT * FROM user_table").executeQuery();
            while (rs.next()){
                String id = rs.getString("id");
                String test_field = rs.getString("test_field");
                System.out.println(id + ": " + test_field);
            }
            connection.close();
            System.out.println("\tSELECT * FROM table_name successful.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("\tSELECT * FROM table_name failed.");
            return false;
        }

    }

    private static boolean testCreateUserInTable(){

        System.out.println("insert table_name");
        try (Connection connection = SQLManager.getConnection()){
            PreparedStatement statement = connection.prepareStatement("insert into dataknights_spotifyuser values (?)");
            statement.setString(1, "1e4c");

            statement.executeUpdate();
            connection.close();
            System.out.println("\tinsert table_name successful.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("\tinsert table_name failed.");
            return false;
        }
    }
}
