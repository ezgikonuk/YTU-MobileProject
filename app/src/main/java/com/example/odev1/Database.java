package com.example.odev1;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "login_database";//database adı

    private static final String TABLE_NAME = "login";
    private static String KULLANICI_ID = "id";
    private static String KULLANICI_ADI = "username";
    private static String KULLANICI_MAIL = "mail";
    private static String KULLANICI_SIFRE = "password";
    private static String KULLANICI_PHONE = "phone";
    private static String KAYIT_TARIHI = "createdAt";
    private static final String QUESTIONS_TABLE = "questions";
    private static String QUESTION = "question";
    private static String OPTION1 = "option1";
    private static String OPTION2 = "option2";
    private static String OPTION3 = "option3";
    private static String OPTION4 = "option4";
    private static String OPTION5 = "option5";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {  // Database i oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KULLANICI_ID + " INTEGER PRIMARY KEY,"
                + KULLANICI_ADI + " TEXT,"
                + KULLANICI_MAIL + " TEXT,"
                + KULLANICI_SIFRE + " TEXT,"
                + KULLANICI_PHONE + " TEXT,"
                + KAYIT_TARIHI + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

        String CREATE_QUESTIONSTABLE = "CREATE TABLE " + QUESTIONS_TABLE +
                "(id INTEGER PRIMARY KEY,\n" +
                "question TEXT,\n" +
                "option1 TEXT,\n" +
                "option2 TEXT,\n" +
                "option3 TEXT,\n" +
                "option4 TEXT,\n" +
                "level TEXT,\n" +
                "answer TEXT)";
        db.execSQL(CREATE_QUESTIONSTABLE);
    }

    // KULLANICI ADI - MAİL DAHA ÖNCE KAYDEDİLMİŞ Mİ KONTROLÜ
    public Boolean checkForCredentials(String userName, String mail) {
        boolean check = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c1 = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE username=? OR mail=?",
                new String[]{userName, mail}
        );
        check = c1.moveToFirst();
        c1.close();
        return check;
    }


    public Boolean kullaniciEkle(String userName, String password, String mail, String phone) {
        //kullanıcıEkle methodu Database e veri eklemek için
        if (checkForCredentials(userName, mail) == false) {
            SQLiteDatabase db = getReadableDatabase();

            String selectQuery = "INSERT INTO " + TABLE_NAME + " (username, password, mail, phone, createdAt) VALUES ( '" + userName + "','" + password + "','" + mail + "','" + phone + "', DATE()) ";

            SQLiteStatement statement = db.compileStatement(selectQuery);
            int affectedRows = statement.executeUpdateDelete();
            //db.close();

            if (affectedRows > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean loginAuth(String userName, String password) {
        //Kullanıcı girişi için
       boolean check = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c1 = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE username=? AND password=?",
                new String[]{userName, password}
        );
        check = c1.moveToFirst();
        c1.close();
        return check;
    }

    // SORU EKLEME
    public Boolean addQuestion(String question, String option1, String option2, String option3, String option4, String level, String answer) {
        SQLiteDatabase db = getReadableDatabase();

        String insertAnswer = "INSERT INTO " + QUESTIONS_TABLE + " (question, option1, option2, option3, option4, level, answer) VALUES ('" + question + "','" + option1 + "','" + option2 + "','" + option3 + "','" + option4 + "','" + level + "','" + answer + "')";
        SQLiteStatement statement = db.compileStatement(insertAnswer);
        int affectedRows = statement.executeUpdateDelete();
        //db.close();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

/*    public Object getQuest() {
        String[][] aryDB = new String[7][7];
        String query="SELECT * FROM " + QUESTIONS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i = 0;
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex("id"));
            String question = c.getString(c.getColumnIndex("question"));
            String option1 = c.getString(c.getColumnIndex("option1"));
            String option2 = c.getString(c.getColumnIndex("option2"));
            String option3 = c.getString(c.getColumnIndex("option3"));
            String option4 = c.getString(c.getColumnIndex("option4"));
            String level = c.getString(c.getColumnIndex("level"));
            String answer = c.getString(c.getColumnIndex("answer"));
            aryDB[i][0] = id;
            aryDB[i][1] = question;
            aryDB[i][2] = option1;
            aryDB[i][3] = option2;
            aryDB[i][4] = option3;
            aryDB[i][5] = option4;
            aryDB[i][6] = level;
            aryDB[i][7] = answer;
            i = i + 1;
        }
        c.close();
        //db.close();
        return aryDB;
    }*/


    public HashMap<String, String> kullaniciDetay() {
        //Bu methodda sadece tek row değerleri alınır.

        //HashMap bir çift boyutlu arraydir.anahtar-değer ikililerini bir arada tutmak için tasarlanmıştır.
        //mesala map.put("x","300"); mesala burda anahtar x değeri 300.

        HashMap<String, String> kisi = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            kisi.put(KULLANICI_MAIL, cursor.getString(1));
            kisi.put(KULLANICI_SIFRE, cursor.getString(2));
            kisi.put(KAYIT_TARIHI, cursor.getString(3));
        }
        cursor.close();
        db.close();
        return kisi;
    }

    public Object getQuestions() {
        String countQuery = "SELECT  * FROM " + QUESTIONS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        String question[] = new String[rowCount];
        String option1[] = new String[rowCount];
        String option2[] = new String[rowCount];
        String option3[] = new String[rowCount];
        String option4[] = new String[rowCount];
        String level[] = new String[rowCount];
        String answer[] = new String[rowCount];
        for (int i = 0; i < rowCount; i++) {

            cursor = db.rawQuery(countQuery, null);
            cursor.moveToPosition(i);

            question[i] = cursor.getString(0);
            option1[i] = cursor.getString(1);
            option2[i] = cursor.getString(2);
            option3[i] = cursor.getString(3);
            option4[i] = cursor.getString(4);
            level[i] = cursor.getString(5);
            answer[i] = cursor.getString(6);
        }
        db.close();
        cursor.close();
        return false;
    }


    public int getRowCount() { //tabloda kaç satır kayıtlı olduğunu geri döner

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }


    public void resetTables() {
        // Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
