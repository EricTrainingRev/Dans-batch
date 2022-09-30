package com.revature.controller;

import com.google.gson.Gson;
import com.revature.entities.Customer;
import com.revature.service.CustomerService;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerController {
    public CustomerService customerService;
    public Gson gson;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        this.gson = new Gson();
    }
    /*
        template: public Handler {lambda name} = ctx -> {};
     */
    public Handler getAllCustomers = ctx ->{
        // get customers
        List<Customer> customers = this.customerService.getAllCustomers();
        // convert customer data to json
        String customersJSON = this.gson.toJson(customers);
        // attach customer json to response body
        ctx.result(customersJSON);
        // assign status code for response
        ctx.status(200);
    };

    public Handler login = ctx -> {
        // get the body of the request
        String body = ctx.body();
        // convert the data into the Java object we need
        Customer loginInfo = this.gson.fromJson(body, Customer.class);
        // get the result from the service method
        String message = this.customerService.checkLogin(loginInfo);
        // attach the message into a map that we will convert into a json
        Map<String,String> messageMap = new HashMap<>();
        messageMap.put("message",message);
        String messageJSON = this.gson.toJson(messageMap);
        ctx.result(messageJSON);
        if (message.equals("login success")){
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    };

    public Handler createCustomer = ctx -> {
        String body = ctx.body();
        Customer newCustomer = this.gson.fromJson(body, Customer.class);
        Customer returnCustomer = this.customerService.createCustomer(newCustomer);
        String returnCustomerJSON = this.gson.toJson(returnCustomer);
        ctx.result(returnCustomerJSON);
        if(returnCustomer.userId == 0){
            ctx.status(400);
        } else {
            ctx.status(201);
        }
    };
}
