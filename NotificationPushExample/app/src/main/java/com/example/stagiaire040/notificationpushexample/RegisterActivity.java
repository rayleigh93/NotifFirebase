package com.example.stagiaire040.notificationpushexample;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {


    private static final int PICK_IMAGE = 1 ;

    EditText mEditTextName ;
    EditText mEditTextEmail;
    EditText mEditTextPassword;

    Button mButtonRegister;
    Button mButtonLogin;


    CircleImageView mImageButtonRegister;

    Uri imageUri;

    StorageReference mStorage;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;



    String name ;
    String email ;
    String password ;

    ProgressBar mProgressBarRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        imageUri = null;



        mEditTextName = findViewById(R.id.register_name);
        mEditTextEmail = findViewById(R.id.register_email);
        mEditTextPassword = findViewById(R.id.register_password);

        mButtonRegister = findViewById(R.id.register_btn);
        mButtonLogin = findViewById(R.id.register_login_btn);

        mImageButtonRegister = findViewById(R.id.image_register);

        mProgressBarRegister = findViewById(R.id.registerProgressBar);


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        mStorage = FirebaseStorage.getInstance().getReference().child("images");
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        mImageButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE);



            }
        });


        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    mProgressBarRegister.setVisibility(View.VISIBLE);

                     name = mEditTextName.getText().toString().trim();
                     email = mEditTextEmail.getText().toString().trim();
                     password = mEditTextPassword.getText().toString().trim();



                    if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){


                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener
                                (RegisterActivity.this ,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if(task.isSuccessful()){

                                    final String user_id = mAuth.getCurrentUser().getUid();



                                                Map<String,Object> userMap = new HashMap<>();
                                                userMap.put("name",name);




                                                mFirestore.collection("users").document(user_id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {



                                                        mProgressBarRegister.setVisibility(View.INVISIBLE);

                                                        Toast.makeText(RegisterActivity.this, name, Toast.LENGTH_SHORT).show();


                                                        sendToMain();


                                                    }
                                                });

                                            }


                                        }
                                    });



                                }

                                else {
                                    Toast.makeText(RegisterActivity.this, "Failed connection", Toast.LENGTH_SHORT).show();

                                    mProgressBarRegister.setVisibility(View.INVISIBLE);
                                }







                    }




        });



    }

    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {



            case PICK_IMAGE :



                imageUri = data.getData();
                mImageButtonRegister.setImageURI(imageUri);


                break;


             default:
                 break;


        }



    }
}
