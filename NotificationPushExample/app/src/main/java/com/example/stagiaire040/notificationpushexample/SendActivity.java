package com.example.stagiaire040.notificationpushexample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity {


    TextView mTextViewUserId;

    String mCurrentId;

    String mUserId;
    String mUserName;

    EditText mMessageView;

    Button mButtonSend;

    FirebaseFirestore mFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);


        mTextViewUserId = findViewById(R.id.user_name_tv);
        mMessageView = findViewById(R.id.message_view);
        mButtonSend = findViewById(R.id.buttonViewSend);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCurrentId = FirebaseAuth.getInstance().getUid();

        mUserId = getIntent().getStringExtra("user_id");
        mUserName = getIntent().getStringExtra("user_name");
        mTextViewUserId.setText("Send to ...." + mUserName);


        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = mMessageView.getText().toString();

                if(!TextUtils.isEmpty(message)){

                    Map<String,Object> notificationMessage = new HashMap<>();

                    notificationMessage.put("message",message);
                    notificationMessage.put("from",mCurrentId);


                    mFirebaseFirestore.collection("users/"+ mUserId +"/notifications")
                            .add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SendActivity.this, "Notif Send", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(SendActivity.this, "Notif error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }



            }
        });



    }
}
