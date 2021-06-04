package com.snkz.appcontact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.snkz.appcontact.R;

import javax.security.auth.Subject;

public class SendEmailActivity extends AppCompatActivity {
    private EditText edtEmailAddress, edtObject, edtContent;
    private Button btnSend;
    private String emailAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        Intent intent = this.getIntent();
        emailAddress = intent.getStringExtra("email_address");

        initView();
        edtEmailAddress.setText("" +emailAddress);
        edtEmailAddress.setClickable(false);
        sendEmail(emailAddress);
    }

    private void sendEmail(String emailAddress) {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] recipients = emailAddress.split(",");
                String subject = edtObject.getText().toString().trim();
                String messga = edtContent.getText().toString().trim();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, messga);

                intent.setType("message/rfc882");
                startActivity(Intent.createChooser(intent, "Choose an email client ‍🙆‍♀"));
            }
        });
    }

    private void initView() {
        edtEmailAddress = findViewById(R.id.edt_emailaddress);
        edtObject = findViewById(R.id.edt_object);
        edtContent = findViewById(R.id.edt_mail);
        btnSend = findViewById(R.id.btn_send);
    }
}