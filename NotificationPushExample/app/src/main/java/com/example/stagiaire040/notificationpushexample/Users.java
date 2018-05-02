package com.example.stagiaire040.notificationpushexample;

public class Users extends UserId{



    String name ;


    public Users() {
    }

    public Users(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
