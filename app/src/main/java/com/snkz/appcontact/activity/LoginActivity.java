package com.snkz.appcontact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.snkz.appcontact.R;
import com.snkz.appcontact.model.Contact;
import com.snkz.appcontact.sqlite.SQLiteContactHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private SQLiteContactHelper sqLiteContactHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        sqLiteContactHelper = new SQLiteContactHelper(this);
        tvRegister.setPaintFlags(tvRegister.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        signUp();
        signIn();
    }


    private void signIn() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all those fields! ⛔", Toast.LENGTH_LONG).show();
                } else {
                    Contact contact = sqLiteContactHelper.checkLogin(username);
                    if (contact == null) {
                        Toast.makeText(LoginActivity.this, "This account hasn't not existed!! ⛔", Toast.LENGTH_LONG).show();
                    } else {
                        if (contact.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this, "🎉 ACCESS GRANTED! 🎉", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("id", contact.getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong password. Please try again! ⛔", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }



    private void signUp(){
        tvRegister.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Coming to Register sreen...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_signup);
        tvRegister = findViewById(R.id.tv_signup);
    }
}