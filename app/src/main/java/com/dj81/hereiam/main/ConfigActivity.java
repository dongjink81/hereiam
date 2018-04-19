package com.dj81.hereiam.main;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.ListView;

import com.dj81.hereiam.R;
import com.dj81.hereiam.utils.ContactVO;
import com.dj81.hereiam.utils.CoreUtils;
import com.dj81.hereiam.utils.DBUtils;

import static com.dj81.hereiam.utils.CoreUtils.*;

import java.util.ArrayList;
import java.util.List;


public class ConfigActivity extends AppCompatActivity {

    private android.widget.TextView mTextMessage1;

    private static final List<String> list = new ArrayList<>();
    private static ArrayAdapter<String> adapter = null;
    private static ListView listview = null;

    //selected_item_textview = (TextView)findViewById(R.id.selected_item_textview);


    /**
     * 하단 네비바
     */
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


    /**
     * 연락처 가져온후
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            String name = cursor.getString(0);        //0은 이름을 얻어옵니다.
            String number = cursor.getString(1);   //1은 번호를 받아옵니다.
            cursor.close();

            //adapter.addAll(name + " : " + number);
            DBUtils._instance.addContact(new ContactVO(name, number));
            refreshList(adapter, list);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dj81.hereiam.R.layout.activity_config);

        mTextMessage1 = (android.widget.TextView) findViewById(com.dj81.hereiam.R.id.message);
        mTextMessage1.setText(com.dj81.hereiam.R.string.guide_config);

        /** 하단 네비 */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(com.dj81.hereiam.R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        menu.findItem(com.dj81.hereiam.R.id.navigation_config).setChecked(true);

        /** 연락처 관련 */
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



        /**  리스트뿌림    */

        listview = (ListView)findViewById(R.id.listview);

        list.clear();
        for(ContactVO vo : DBUtils._instance.getAllContacts()){
            list.add(vo.toString());
        }
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);


        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                String selected_item = (String)adapterView.getItemAtPosition(position);

                //텍스트뷰에 출력
               // selected_item_textview.setText(selected_item);
                //mTextMessage1.setText(position + " : " + id);

                try{
                    String pk=String.valueOf(selected_item.split(" ")[0]);
                    System.out.println("["+pk +"] " + selected_item);
                    DBUtils._instance.deleteContact(Integer.parseInt(pk));
                    CoreUtils.refreshList(adapter, list);
                }catch(Exception ignr){}
            }
        });

    }





}
