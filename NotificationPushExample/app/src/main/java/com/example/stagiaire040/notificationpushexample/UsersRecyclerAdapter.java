package com.example.stagiaire040.notificationpushexample;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>

{


    List<Users> mUsersList;
    Context mContext;


    public UsersRecyclerAdapter(Context context,List<Users> usersList) {
        mContext = context;
        mUsersList = usersList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item,parent,false);

        return new ViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.mTextView.setText(mUsersList.get(position).getName());

        final String user_id = mUsersList.get(position).userId;
        final String user_name = mUsersList.get(position).getName();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SendActivity.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_name",user_name);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }










    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            mTextView = mView.findViewById(R.id.textViewListUser);


        }

    }






}
