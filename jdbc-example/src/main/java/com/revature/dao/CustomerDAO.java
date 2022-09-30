package com.revature.dao;

import com.revature.entities.Customer;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    /*
        Normally you would create an interface first and then the implementation, but for the sake of time we will
        stick with just making the implementation class
     */

    /*
        Typically daos will have methods to perform your CRUD operations: Create, Read, Update, and Delete.
        Your daos should be the only classes that handle these kinds of operations.
     */

    /*
        there is a standard format we will follow for interacting with our database:
        1. create the query and store it in a String
        2. create either a statement or prepared statement object to execute our query
        3. pass in any relevant parameters and then actually execute the query
        4. handle the result
     */

    // Create

    public Customer createCustomer(Customer customer){
        // this is a "try with resources" example: the connection object will close automatically at the end of the method
        try(Connection connection = ConnectionUtil.createConnection()){
            String sql = "insert into customers values(default,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Prepared Statement indexing starts at 1
            ps.setString(1,customer.firstName);
            ps.setString(2, customer.lastName);
            ps.setString(3, customer.username);
            ps.setString(4, customer.pass);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                customer.userId = rs.getInt(1);
                return customer;
            } else {
                return new Customer();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return new Customer();
        }
    }

    // READ

    public List<Customer> getAllCustomers(){
        try (Connection connection = ConnectionUtil.createConnection()){
            String sql = "select * from customers";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            List<Customer> customers = new ArrayList<>();
            while(rs.next()){
                Customer customer = new Customer(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("pass")
                );
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Customer getCustomerByLogin(Customer customer){
        try (Connection connection = ConnectionUtil.createConnection()){
            String sql = "select * from customers where username = ? and pass = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.username);
            ps.setString(2, customer.pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                customer.userId = rs.getInt("user_id");
                return customer;
            } else {
                return new Customer();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return new Customer();
        }
    }

    public static void main(String[] args) {
        Customer loginInfo = new Customer();
        loginInfo.username = "iamTheNight";
        loginInfo.pass = "Supermanissilly";

        CustomerDAO customerDao = new CustomerDAO();

        System.out.println(customerDao.getCustomerByLogin(loginInfo));

    }
}
