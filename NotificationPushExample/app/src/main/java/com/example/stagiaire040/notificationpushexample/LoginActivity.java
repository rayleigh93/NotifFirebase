package com.example.stagiaire040.notificationpushexample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    EditText mEditTextEmail;
    EditText mEditTextPassword;
    Button  mButtonLogin;
    Button mButtonRegister;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirebaseFirestore;


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
        mFirebaseFirestore = FirebaseFirestore.getInstance();

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



        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = mEditTextEmail.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();



                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){


                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){





                                        String token_id = FirebaseInstanceId.getInstance().getToken();
                                        String current_id = mAuth.getCurrentUser().getUid();

                                        Map<String,Object>  tokenMap = new HashMap<>();

                                        tokenMap.put("token_id",token_id);



                                        mFirebaseFirestore.collection("users")
                                                .document(current_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                sendToMain();


                                            }
                                        });




                            }else{

                                Toast.makeText(LoginActivity.this, "Erreur : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }




            }
        });






    }
}
