package com.example.stagiaire040.notificationpushexample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends android.support.v4.app.Fragment {


    RecyclerView mUsersListView;

    List<Users> mUsersList;
    UsersRecyclerAdapter mUsersRecyclerAdapter;
    RecyclerView mUserListView;

    FirebaseFirestore mFirebaseFirestore;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_users, container, false);


        mFirebaseFirestore = FirebaseFirestore.getInstance();

        mUsersListView = v.findViewById(R.id.users_list);

        mUsersList = new ArrayList<>();

        mUsersRecyclerAdapter = new UsersRecyclerAdapter(container.getContext(),mUsersList);

        mUsersListView.setHasFixedSize(true);
        mUsersListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mUsersListView.setAdapter(mUsersRecyclerAdapter);


        return v;

    }

    @Override
    public void onStart() {
        super.onStart();

        mUsersList.clear();

        // Si les data changent
        mFirebaseFirestore.collection("users").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots,FirebaseFirestoreException e) {


                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){

                    if(doc.getType() == DocumentChange.Type.ADDED){


                        String user_id = doc.getDocument().getId();

                        Users users = doc.getDocument().toObject(Users.class).withId(user_id);
                        mUsersList.add(users);
                        mUsersRecyclerAdapter.notifyDataSetChanged();

                    }



                }



            }
        });


    }


    @Override
    public void onStop() {
        super.onStop();

       // mUsersList = new ArrayList<>();

    }
}
