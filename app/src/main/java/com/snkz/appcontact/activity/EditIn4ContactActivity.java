package com.snkz.appcontact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class EditIn4ContactActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtEmail, edtAddress, edtBirthday;
    private ImageView imgAvatar;
    private Button btnSave, btnUpload;
    private ImageButton btnBack;
    private SQLiteContactHelper sqLiteContactHelper;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_in4_contact);
        SQLiteContactHelper sqLiteContactHelper = new SQLiteContactHelper(getApplicationContext());
        Intent intent = getIntent();
        savedInstanceState  = intent.getExtras();
        id = savedInstanceState.getInt("id");
        initView();
        loadInformation(id);
        editAvatar();
        saveEdition(id);
    }

    private void saveEdition(int id) {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String birthday = edtBirthday.getText().toString();
                String phone = edtPhone.getText().toString();
                String email = edtEmail.getText().toString();
                String address = edtAddress.getText().toString();

                BitmapDrawable drawable = (BitmapDrawable) imgAvatar.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] avatar = stream.toByteArray();

                if (name.isEmpty() || birthday.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    Toast.makeText(EditIn4ContactActivity.this, "Please fill all those fields! ⛔", Toast.LENGTH_LONG).show();
                } else {

                    try {
                        Contact contact = new Contact(avatar, name, birthday, phone, email, address);
                        boolean check = sqLiteContactHelper.editProfile(id, contact);
                        if(check) {
                            Toast.makeText(EditIn4ContactActivity.this, "🎉 EDIT CONTACT SUCCESS! 🎉", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EditIn4ContactActivity.this, "Edit contact failed! ⛔", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });
    }

    private void loadInformation(int id) {
        sqLiteContactHelper = new SQLiteContactHelper(getApplicationContext());

        Contact contact = sqLiteContactHelper.getContactByID(id);
        Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getAvatar(),0, contact.getAvatar().length);
        imgAvatar.setImageBitmap(bitmap);
        edtName.setText(""+contact.getName());
        edtBirthday.setText(""+contact.getBirthday());
        edtPhone.setText(""+contact.getPhone());
        edtEmail.setText(""+contact.getEmail());
        edtAddress.setText(""+contact.getAddress());
    }

    private void editAvatar() {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(EditIn4ContactActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
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

                        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(EditIn4ContactActivity.this)
                                .setOnImageSelectedListener(listener)
                                .create();
                        tedBottomPicker.show(getSupportFragmentManager());
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(EditIn4ContactActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }


                };
                TedPermission.with(EditIn4ContactActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }
        });
    }

    private void initView() {
        edtName = findViewById(R.id.edt_newname);
        edtPhone = findViewById(R.id.edt_newphone);
        edtBirthday = findViewById(R.id.edt_newbirthday);
        edtEmail = findViewById(R.id.edt_newemail);
        edtAddress = findViewById(R.id.edt_newaddress);
        btnSave = findViewById(R.id.btn_savenew);
        btnUpload = findViewById(R.id.btn_buttonupload);
        imgAvatar = findViewById(R.id.img_newavatar);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int date = calendar.get(Calendar.DAY_OF_MONTH);
        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditIn4ContactActivity.this, new DatePickerDialog.OnDateSetListener() {
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