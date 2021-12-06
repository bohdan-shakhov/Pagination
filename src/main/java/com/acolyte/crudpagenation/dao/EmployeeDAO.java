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
    Statement stmt;
    private int noOfRecords;

    public EmployeeDAO() {}

    private static Connection getConnection() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        return connection;
    }

    public List<Employee> viewAllEmployees(int offset, int noOfRecords) {
        String query = "select * from employee limit "
                + offset + ", " + noOfRecords;
        List<Employee> list = new ArrayList<>();
        Employee employee;
        try {
            connection = getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                employee = new Employee();
                employee.setEmployeeId(rs.getInt("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setDeptName(rs.getString("dept_name"));
                list.add(employee);
            }
            rs.close();

            rs = stmt.executeQuery("SELECT COUNT(*) FROM employee");
            if(rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            try {
                if(stmt != null)
                    stmt.close();
                if(connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }
}
