package com.example.odev1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private Button ListQuestions;
    private Button AddQuestions;
    private Button AddExam;
    private Button ExamSettings;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ListQuestions = (Button) findViewById(R.id.menuListQuestions);
        AddQuestions = (Button) findViewById(R.id.menuAddQuestions);
        AddExam = (Button) findViewById(R.id.menuCreateExam);
        ExamSettings = (Button) findViewById(R.id.menuExamSettings);

        // SORU EKLEME
        AddQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AddQuestionActivity.class);
                startActivity(intent);
            }
        });

        // SORU LİSTELEME
        ListQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // SINAV OLUŞTUR
        AddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // SINAV AYARLARI
        ExamSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}

