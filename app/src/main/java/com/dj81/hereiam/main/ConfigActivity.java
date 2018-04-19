package com.dj81.hereiam.main;

import android.provider.ContactsContract;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Button;
import android.content.Intent;


public class ConfigActivity extends AppCompatActivity {

    private android.widget.TextView mTextMessage1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case com.dj81.hereiam.R.id.navigation_list:
                    android.content.Intent intent=new android.content.Intent(ConfigActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                case com.dj81.hereiam.R.id.navigation_config:
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dj81.hereiam.R.layout.activity_config);

        mTextMessage1 = (android.widget.TextView) findViewById(com.dj81.hereiam.R.id.message);

        mTextMessage1.setText(com.dj81.hereiam.R.string.guide_config);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(com.dj81.hereiam.R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        menu.findItem(com.dj81.hereiam.R.id.navigation_config).setChecked(true);

        Button configButton = super.findViewById(com.dj81.hereiam.R.id.configButton);
        configButton.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {

                try{
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(intent, 0);
                }catch(android.content.ActivityNotFoundException e){
                    e.printStackTrace();
                    mTextMessage1.setText(com.dj81.hereiam.R.string.guide_config_rejected);
                }

            }
        });

    }



}
