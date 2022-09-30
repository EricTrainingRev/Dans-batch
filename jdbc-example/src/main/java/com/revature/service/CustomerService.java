package com.revature.service;

import com.revature.dao.CustomerDAO;
import com.revature.entities.Customer;

import java.util.List;

public class CustomerService {
    /*
        The service layer handles business logic: it is the gate-keeper section between the end user
        and the database
     */

    public CustomerDAO customerDao;

    public CustomerService(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }

    public String checkLogin(Customer customer){ // for get user by login
        Customer resultCustomer = this.customerDao.getCustomerByLogin(customer);
        if(resultCustomer.userId == 0){
            return "Login information incorrect: please try again";
        } else {
            return "login success";
        }
    }

    public List<Customer> getAllCustomers(){
        // this is an easy one :)
        return this.customerDao.getAllCustomers();

    }

    public String updateCustomer(Customer customer){
        if (this.customerDao.updateCustomer(customer)){
            return "Customer updated successfully";
        } else {
            return "Customer was not updated: please try again";
        }
    }

    public String deleteCustomer(int customerId){
        if (this.customerDao.deleteCustomer(customerId)){
            return "Customer deleted";
        } else {
            return "Customer was not deleted: please try again";
        }
    }

    public Customer createCustomer(Customer customer){
        return this.customerDao.createCustomer(customer);
    }

    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerService customerService = new CustomerService(customerDAO);
        Customer customer = new Customer();
        customer.firstName = "Papadopalis the magnifcent purple horse";
        customer.lastName = "Clooney";
        customer.userId = 8;

        System.out.println(customerService.deleteCustomer(8));
    }
}

/*
    1. We set environment variables
    2. create dao object
    3. inject dao object into service object
    4. service object calls checkLogin method

    service object passes the login info into the dao method getCustomerByLoginInfo
    dao object makes a query with login data and then returns a customer object with any relevant data
    service object reads the userId of the returned customer data and returns the appropriate message

 */
