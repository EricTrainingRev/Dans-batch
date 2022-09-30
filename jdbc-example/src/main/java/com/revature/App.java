package com.revature;

import com.revature.controller.CustomerController;
import com.revature.dao.CustomerDAO;
import com.revature.service.CustomerService;
import io.javalin.Javalin;

public class App {


    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
            config.enableDevLogging();
        });

        CustomerDAO customerDAO = new CustomerDAO();
        CustomerService customerService = new CustomerService(customerDAO);
        CustomerController customerController = new CustomerController(customerService);

        app.get("/customer", customerController.getAllCustomers);

        app.post("/customer/login", customerController.login);

        app.post("/customer", customerController.createCustomer);

        app.start();
    }
}
