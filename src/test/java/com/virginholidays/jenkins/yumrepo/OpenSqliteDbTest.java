package com.virginholidays.jenkins.yumrepo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * yum-paramater
 *
 * @author Declan Newman (467689)
 * @since 13/02/15
 */
public class OpenSqliteDbTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:/Users/vhmit009/Downloads/filelists.sqlite");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE TYPE = 'table'");
        while (resultSet.next()) {
            System.out.println("name = " + resultSet.getString("name"));
        }
        resultSet = statement.executeQuery("SELECT name FROM filelist");

        while (resultSet.next()) {
            System.out.println("name = " + resultSet.getString("name"));
        }
    }

}
