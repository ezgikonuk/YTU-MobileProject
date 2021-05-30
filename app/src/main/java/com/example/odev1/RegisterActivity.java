package com.example.odev1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName;
    private EditText Password;
    private EditText PasswordConfirm;
    private EditText userMail;
    private EditText userPhone;
    private TextView Info;
    private Button Back;
    private Button RegisterAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText) findViewById(R.id.newAccountUserName);
        Password = (EditText) findViewById(R.id.newAccountPassword);
        PasswordConfirm = (EditText) findViewById(R.id.newAccountPasswordConfirm);
        userMail = (EditText) findViewById(R.id.newAccountUserMail);
        userPhone = (EditText) findViewById(R.id.newAccountPhone);
        Info = (TextView) findViewById(R.id.registerMessage);
        Back = (Button) findViewById(R.id.buttonBackToMain);
        RegisterAcc = (Button) findViewById(R.id.buttonNewAccount);
        Info.setText("");

        // ÜYE OL BUTTONU
        RegisterAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db.resetTables();
                if (userName.length() > 0 && Password.length() > 0 && PasswordConfirm.length() > 0 && userMail.length() > 0 && userPhone.length() > 0) {
                    if (isEmailValid(userMail.getText().toString())) {
                        String Pass = Password.getText().toString();
                        String PassC = PasswordConfirm.getText().toString();
                        if (Pass.compareTo(PassC) > 0) {
                            sendNotify("Şifreler eşleşmiyor.", "#fab83e", 3000, false);
                        } else {
                            Boolean response = db.kullaniciEkle(userName.getText().toString(), Password.getText().toString(), userMail.getText().toString(), userPhone.getText().toString());
                            if (response == true) {
                                sendNotify("Kayıt başarılı.", "#FFFFFF", 1000, true);
                            } else {
                                Info.setTextColor(Color.parseColor("#fab83e"));
                                sendNotify("Kullanıcı adı veya e-posta daha önce kullanılmış.", "#fab83e", 3000, false);
                            }
                        }
                    } else {
                        sendNotify("Lütfen geçerli bir eposta adresi girin.", "#fab83e", 3000, false);
                    }
                } else {
                    sendNotify("Lütfen tüm alanları doldurun.", "#fab83e", 3000, false);
                }
            }
        });

        // ANA SAYFAYA GERİ DÖNME BUTTONU
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // BİLDİRİM VERME FONKSİYONU
    public void sendNotify(String message, String color, Integer delay, Boolean redirect) {
        Info.setTextColor(Color.parseColor(color));
        Info.setText(message);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Info.setText("");
                        if (redirect == true) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }, delay);
    }

    // EPOSTA KONTROLU REGEX
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
