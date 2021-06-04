package com.snkz.appcontact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.snkz.appcontact.R;
import com.snkz.appcontact.model.Contact;
import com.snkz.appcontact.sqlite.SQLiteContactHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword, edtName, edtPhone, edtEmail, edtAddress, edtBirthday;
    private ImageView imgAvatar;
    private Button btnRegister, btnUpload;
    private ImageButton btnBack;
    private SQLiteContactHelper  sqLiteContactHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        sqLiteContactHelper = new SQLiteContactHelper(RegisterActivity.this);
        back();
        uploadAvatar();
        register();
    }



    private void back(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming to Login sreen...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void uploadAvatar(){
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(RegisterActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    imgAvatar.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(RegisterActivity.this)
                                .setOnImageSelectedListener(listener)
                                .create();
                        tedBottomPicker.show(getSupportFragmentManager());
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(RegisterActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }


                };
                TedPermission.with(RegisterActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }
        });
    }

    private void register() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String birthday = edtBirthday.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();

                BitmapDrawable drawable = (BitmapDrawable) imgAvatar.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] avatar = stream.toByteArray();

                if (username.isEmpty() || password.isEmpty() || name.isEmpty() || birthday.isEmpty()
                        || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all those fields! ⛔" , Toast.LENGTH_LONG).show();
                } else {
                    boolean checkUsername = sqLiteContactHelper.checkUsername(username);
                    boolean checkPhone = sqLiteContactHelper.checkPhoneNumber(phone);
                    boolean checkNameContact = sqLiteContactHelper.checkNameContact(name);

                    if (checkUsername && checkNameContact && checkPhone) {
                        Toast.makeText(RegisterActivity.this, "This contact has been existed! ⛔", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            Contact contact = new Contact(username, password, avatar, name, birthday, phone, email, address);
                            sqLiteContactHelper.add(contact);
                            Toast.makeText(RegisterActivity.this, "🎉 REGISTER SUCCESS! 🎉", Toast.LENGTH_LONG).show();
                        } catch (Exception e){
                            System.out.println(e);
                        }
                    }
                }
            }
        });
    }

    public void initView() {
        edtUsername = findViewById(R.id.edt_usernameregister);
        edtPassword = findViewById(R.id.edt_passwordregister);
        edtName = findViewById(R.id.edt_nameregister);
        edtPhone = findViewById(R.id.edt_phoneregister);
        edtBirthday = findViewById(R.id.edt_birthdayregister);
        edtEmail = findViewById(R.id.edt_emailregister);
        edtAddress = findViewById(R.id.edt_addressregister);
        btnBack = findViewById(R.id.btn_backtologin);
        btnRegister = findViewById(R.id.btn_signup);
        btnUpload = findViewById(R.id.btn_uploadphotoregister);
        imgAvatar = findViewById(R.id.img_avatarregister);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int date = calendar.get(Calendar.DAY_OF_MONTH);
        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtBirthday.setText( dayOfMonth+ "/" +(month+1)+ "/" +year);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });
    }
}