package com.dj81.hereiam.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;



public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case com.dj81.hereiam.R.id.navigation_list:
                    DBUtils._instance.deleteAll();
                    return true;
                case com.dj81.hereiam.R.id.navigation_config:
                    Intent intent=new Intent(MainActivity.this, ConfigActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dj81.hereiam.R.layout.activity_main);

        mTextMessage = (TextView) findViewById(com.dj81.hereiam.R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(com.dj81.hereiam.R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        android.view.Menu menu = navigation.getMenu();
        menu.findItem(com.dj81.hereiam.R.id.navigation_list).setChecked(true);

        /**  DB 초기화 */
        new DBUtils(this);
    }

}
