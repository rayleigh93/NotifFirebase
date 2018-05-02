package com.example.stagiaire040.notificationpushexample;

import android.support.annotation.NonNull;

public class UserId {


    String userId;


    public <T extends UserId> T withId(@NonNull final String id){
        this.userId = id;

        return (T) this;
    }



}
