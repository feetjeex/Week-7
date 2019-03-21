package com.example.trivia;

import java.sql.*;

public class HighScoreHelper {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/EMP";

    static final String USER = "username";
    static final String PASS = "password";

    Connection conn = null;
    Statement stmt = null;

    public void connecter() {

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }

        catch (Exception e) {

        }
    }

}
