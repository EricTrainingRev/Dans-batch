package com.revature.entities;

public class Customer {
    public int userId;
    public String firstName;
    public String lastName;
    public String username;
    public String pass;

    public Customer() {}

    public Customer(int userId, String firstName, String lastName, String username, String pass) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.pass = pass;
    }



    @Override
    public String toString() {
        return "Customer{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }


}
