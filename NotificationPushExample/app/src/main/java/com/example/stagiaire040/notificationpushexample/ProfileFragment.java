package com.example.stagiaire040.notificationpushexample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.support.v4.app.Fragment  {



    Button mButtonDeco;

    FirebaseAuth mAuth;

    TextView mTextViewName;

    FirebaseFirestore mFirebaseFirestore;
    String mUserId;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);


        mButtonDeco = (Button) v.findViewById(R.id.deco_btn);
        mTextViewName = v.findViewById(R.id.textViewFirstName);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();



        mFirebaseFirestore.collection("users").document(mUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // "name" is the name of the Key
                String user_name = documentSnapshot.getString("name");

                mTextViewName.setText(user_name);

            }
        });




        mButtonDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(container.getContext(),LoginActivity.class));
            }
        });



        return v;
    }

}
