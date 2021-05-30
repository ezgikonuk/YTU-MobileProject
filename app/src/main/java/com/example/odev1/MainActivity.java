package com.example.odev1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.edittextusername);
        Password = (EditText) findViewById(R.id.edittextpassword);
        Info = (TextView) findViewById(R.id.tvInfoSuccess);
        Login = (Button) findViewById(R.id.buttonlogin);
        Register = (Button) findViewById(R.id.buttonRegister);
        Info.setText("");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // KULLANICI ADI VE ŞİFRE KONTROLÜ
    @SuppressLint("SetTextI18n")
    private void validate(String userName, String userPassword) {
        Database db = new Database(this);
        //Object questionObj = db.getQuest();
        if (userName.length() < 1 || userPassword.length() < 1) {
            Info.setText("Kullanıcı adı ya da parola boş bırakılamaz.");
        } else {
            Boolean response = db.loginAuth(userName, userPassword);
            if (response == true) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                counter--;
                Info.setText("Hatalı kullanıcı adı veya şifre. Kalan giriş hakkınız :" + String.valueOf(counter));
                if (counter == 0) {
                    Login.setEnabled(false);
                    this.finishAffinity();
                }
            }
        }
    }
}
