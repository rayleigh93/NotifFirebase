package com.example.stagiaire040.notificationpushexample;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private TextView mProfileLabel;
    private TextView mUserLabel;
    private TextView mNotificationLabel;

    private ViewPager   mMainPager;

    private PagerViewAdapter mPagerViewAdapter;


    FirebaseAuth mAuth ;


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            sendToLogin();
        }

    }


    private void sendToLogin() {

        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //   startActivity(new Intent(MainActivity.this,LoginActivity.class));

         mAuth = FirebaseAuth.getInstance();




        mProfileLabel = (TextView)findViewById(R.id.profileLabel);
        mUserLabel = (TextView) findViewById(R.id.allUserLabel);
        mNotificationLabel = (TextView) findViewById(R.id.notifLabel);

        mMainPager = (ViewPager) findViewById(R.id.mainPager);
        mMainPager.setOffscreenPageLimit(2);


        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewAdapter);


        mProfileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(0);
            }
        });


        mUserLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(1);
            }
        });


        mNotificationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(2);
            }
        });



        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTabs(int position) {

        switch (position)
        {

            case 0 :

                mProfileLabel.setTextColor(getColor(R.color.textTabBright));
                mProfileLabel.setTextSize(22);

                mUserLabel.setTextColor(getColor(R.color.textTabLight));
                mUserLabel.setTextSize(16);

                mNotificationLabel.setTextColor(getColor(R.color.textTabLight));
                mNotificationLabel.setTextSize(16);

                break;


            case 1 :

                mProfileLabel.setTextColor(getColor(R.color.textTabLight));
                mProfileLabel.setTextSize(16);

                mUserLabel.setTextColor(getColor(R.color.textTabBright));
                mUserLabel.setTextSize(22);

                mNotificationLabel.setTextColor(getColor(R.color.textTabLight));
                mNotificationLabel.setTextSize(16);

                break;

            case 2 :

                mProfileLabel.setTextColor(getColor(R.color.textTabLight));
                mProfileLabel.setTextSize(16);

                mUserLabel.setTextColor(getColor(R.color.textTabLight));
                mUserLabel.setTextSize(16);

                mNotificationLabel.setTextColor(getColor(R.color.textTabBright));
                mNotificationLabel.setTextSize(22);


                break;

        }


    }


}
