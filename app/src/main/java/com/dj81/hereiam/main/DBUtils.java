package com.dj81.hereiam.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class DBUtils extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "hereIamContactsDB";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public static DBUtils _instance=null;

    public DBUtils(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _instance=this;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " LONG PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * CRUD 함수
     */

    // 새로운 Contact 함수 추가
    public void addContact(ContactVO contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, contact.getID()); // Contact Id
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone

        // Inserting Row
        long rtn = db.insert(TABLE_CONTACTS, null, values);
        Log.v("알림", "데이터가 저장 되었습니다. " + rtn);
        db.close(); // Closing database connection
    }

    // id 에 해당하는 Contact 객체 가져오기
    public ContactVO getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_PH_NO}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ContactVO contact = new ContactVO(cursor.getString(0),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // 모든 Contact 정보 가져오기
    public List<ContactVO> getAllContacts() {
        List<ContactVO> contactList = new ArrayList<ContactVO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        Log.v("알림", "전체 조회한 리스트 수 : " + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                Log.d("debug", cursor.getString(0) + " : " + cursor.getString(1) + " : " + cursor.getString(2));
                ContactVO contact = new ContactVO();
                String pk=cursor.getString(0);
                contact.setID( pk );
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    //Contact 정보 업데이트
    public int updateContact(ContactVO contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
    }

    // Contact 정보 삭제하기
    public void deleteContact(int pk) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rtn = db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(pk)});
        db.close();

        Log.v("알림", pk + "번째 데이터가 삭제 되었습니다. " + rtn);
    }

    // Contact 정보 숫자
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rtn=cursor.getCount();
        db.close();

        // return count
        return rtn;
    }

    public String getNextPk(){
        return getContactsCount()+1 +"";
    }


    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        this.onUpgrade(db, -1, -1);
        Log.v("알림", "DB 삭제");
    }

}


