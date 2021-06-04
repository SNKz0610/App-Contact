package com.snkz.appcontact.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snkz.appcontact.R;
import com.snkz.appcontact.fragment.FragmentAboutMe;
import com.snkz.appcontact.sqlite.SQLiteContactHelper;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtOldPassword, edtNewPassword, edtConfirm;
    private Button btnChange;
    private int id;
    private SQLiteContactHelper sqLiteContactHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sqLiteContactHelper = new SQLiteContactHelper(getApplicationContext());
        initView();
        Intent intent = getIntent();
        savedInstanceState  = intent.getExtras();
        id = savedInstanceState.getInt("id");
        changePassword(id);
    }

    private void changePassword(int id) {
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edtOldPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                String confirm = edtConfirm.getText().toString().trim();
                if (newPassword.isEmpty() || oldPassword.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all thoes fields! ⛔", Toast.LENGTH_LONG).show();
                } else {
                    String check = sqLiteContactHelper.checkPassword(id);
                    if (!check.equals(oldPassword)) {
                        Toast.makeText(getApplicationContext(), "Old password is not correct! ⛔", Toast.LENGTH_LONG).show();
                    } else {
                        if (newPassword.equals(oldPassword)) {
                            Toast.makeText(getApplicationContext(), "New password must be different from olds! ⛔", Toast.LENGTH_LONG).show();
                        } else {
                            if (!newPassword.equals(confirm)) {
                                Toast.makeText(getApplicationContext(), "Confirm password not exactly! ⛔", Toast.LENGTH_LONG).show();
                            } else {
                                boolean result = sqLiteContactHelper.changePassword(id, newPassword);
                                if (result) {
                                    Toast.makeText(getApplicationContext(), "Change password succesful! 🎉", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        edtConfirm = findViewById(R.id.edt_confirm);
        edtNewPassword = findViewById(R.id.edt_newpassword);
        edtOldPassword = findViewById(R.id.edt_oldpassword);
        btnChange = findViewById(R.id.btn_save);
    }
}