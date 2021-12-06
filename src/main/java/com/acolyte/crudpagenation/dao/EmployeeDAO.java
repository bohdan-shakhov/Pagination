package com.acolyte.crudpagenation.dao;

import com.acolyte.crudpagenation.db.ConnectionFactory;
import com.acolyte.crudpagenation.to.Employee;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class);

    Connection connection;
    Statement statement;
    private int noOfRecords;

    public EmployeeDAO() {}

    private static Connection getConnection() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        return connection;
    }

    public List<Employee> viewAllEmployees(int offset, int noOfRecords) {
        String query = "select SQL_CALC_FOUND_ROWS * from employee limit " + offset + ", " + noOfRecords;
        List<Employee> list = new ArrayList<>();
        Employee employee = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                employee = new Employee();
                employee.setEmployeeId(rs.getInt("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setDeptName(rs.getString("dept_name"));
            }
            rs.close();

            rs = statement.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("error in EmployeeDAO.java in List<Employee> viewAllEmployees(int, int)");
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                LOGGER.error("can't close statement or connection in EmployeeDAO.java");
            }
        }
        return list;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }
}
