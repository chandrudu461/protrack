package com.example.testapp;

// This is the user class, This User class variable holds all the data that should send to the firebase database*

public class User {
    public String email;
    public String firstName;
    public String lastName;
    public String age;
    public String gender;
    public String lang;

    public User(){ // This is empty constructor

    }

    // this is the actual constructor, that holds data.
    public User(String email,String firstName,String lastName,String age,String gender,String lang){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.lang = lang;
    }

}
