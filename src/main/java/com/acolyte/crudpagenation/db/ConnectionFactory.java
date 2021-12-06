package com.acolyte.crudpagenation.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class);

    private static ConnectionFactory instance = new ConnectionFactory();
    String url = "jdbc:mysql://localhost:3306/employeedb";
    String user = "root";
    String password = "root";
    String driverClass = "com.mysql.jdbc.Driver";

    private ConnectionFactory() {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Can't find JDBC driver");
        }
    }

    public static ConnectionFactory getInstance() {
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            LOGGER.error("Can't get connection");
        }
        return connection;
    }
}
