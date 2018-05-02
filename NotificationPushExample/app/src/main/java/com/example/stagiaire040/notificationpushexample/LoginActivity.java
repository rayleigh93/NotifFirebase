package com.example.stagiaire040.notificationpushexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    EditText mEditTextEmail;
    EditText mEditTextPassword;
    Button  mButtonLogin;
    Button mButtonRegister;

    FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
           sendToMain();
        }

    }

    private void sendToMain() {


        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mAuth = FirebaseAuth.getInstance();
        mEditTextEmail = findViewById(R.id.editTextEmail);
        mEditTextPassword = findViewById(R.id.editTextPassword);

        mButtonLogin = findViewById(R.id.login_btn);
        mButtonRegister = findViewById(R.id.login_register_btn);





        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });






    }
}
