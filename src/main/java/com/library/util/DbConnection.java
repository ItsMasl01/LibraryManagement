package com.library.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

public class DbConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static Connection dbConnection;

    private DbConnection() {
    }

    public static Connection getConnection() {
        try {
            if(dbConnection != null && !dbConnection.isClosed()) {
                return dbConnection;
            }
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream("config.properties")) {
                props.load(fis);
            }
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            return dbConnection;
        } catch (ClassNotFoundException | SQLException | IOException e) {
            return null;
        }
    }
}
