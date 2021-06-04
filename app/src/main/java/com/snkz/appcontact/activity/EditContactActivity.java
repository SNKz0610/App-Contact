package com.snkz.appcontact.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snkz.appcontact.R;
import com.snkz.appcontact.fragment.FragmentAboutMe;
import com.snkz.appcontact.fragment.FragmentContact;
import com.snkz.appcontact.model.Contact;
import com.snkz.appcontact.sqlite.SQLiteContactHelper;

public class EditContactActivity extends AppCompatActivity {
    private Button btnEditContact, btnDeleteContact;
    private TextView tvName, tvBirthday, tvPhone, tvEmail, tvAddress;
    private ImageView imgAvatarContact;
    private SQLiteContactHelper sqLiteContactHelper;
    private String name;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        sqLiteContactHelper = new SQLiteContactHelper(this);
        initView();
        Intent intent = this.getIntent();
        String name = intent.getStringExtra("name");
        id = sqLiteContactHelper.getIdByName(name);
        System.out.println(id);
        loadData(id);
        deleteContact(id);
        editContact(id);
        makeCall();
        sendSMS();
        sendEmail();
    }

    @Override
    protected void onResume() {
        loadData(id);
        super.onResume();
    }

    private void sendEmail() {
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming send email screen ✉....", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditContactActivity.this, SendEmailActivity.class);
                intent.putExtra("email_address", tvEmail.getText().toString().trim().substring(4));
                startActivity(intent);
            }
        });
    }

    private void sendSMS() {
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"+tvPhone.getText().toString().trim()));
                intent.putExtra("sms_body","Hê lô " +tvName.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void makeCall() {
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = tvPhone.getText().toString().trim();
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +phoneNumber));
                startActivity(intent);
            }
        });
    }


    private void editContact(int id) {
        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming edit contact screen ⚙....", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditContactActivity.this, EditIn4ContactActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void loadData(int id) {
        Contact contact = sqLiteContactHelper.getContactByID(id);
        Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getAvatar(), 0, contact.getAvatar().length);
        imgAvatarContact.setImageBitmap(bitmap);
        tvName.setText("🤗 :" + contact.getName());
        tvBirthday.setText("🎉 :" + contact.getBirthday());
        tvPhone.setText("📞 :" + contact.getPhone());
        tvEmail.setText("💌 :" + contact.getEmail());
        tvAddress.setText("🏡 :" + contact.getAddress());
    }

    private void deleteContact(int id) {
        btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = sqLiteContactHelper.getContactByID(id);
                if (contact == null) {
                    Toast.makeText(getApplicationContext(), "⚠ You deleted this contact. Back to directory to check ❗", Toast.LENGTH_LONG).show();
                } else {
                    sqLiteContactHelper.delete(id);
                    Toast.makeText(getApplicationContext(), "DELETE CONTACT SUCCESS ❗", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initView() {
        btnDeleteContact = findViewById(R.id.btn_deletecontact);
        btnEditContact = findViewById(R.id.btn_editcontactinfor);
        tvName = findViewById(R.id.tv_editcontactname);
        tvBirthday = findViewById(R.id.tv_editcontactbirthday);
        tvPhone = findViewById(R.id.tv_editcontactphone);
        tvEmail = findViewById(R.id.tv_editcontactemail);
        tvAddress = findViewById(R.id.tv_editcontactaddress);
        imgAvatarContact = findViewById(R.id.img_contactimage);
    }
}