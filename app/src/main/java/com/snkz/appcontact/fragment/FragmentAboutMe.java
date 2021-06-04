package com.snkz.appcontact.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snkz.appcontact.R;
import com.snkz.appcontact.activity.EditProfileActivity;
import com.snkz.appcontact.activity.LoginActivity;
import com.snkz.appcontact.activity.MainActivity;
import com.snkz.appcontact.activity.ChangePasswordActivity;
import com.snkz.appcontact.model.Contact;
import com.snkz.appcontact.sqlite.SQLiteContactHelper;

public class FragmentAboutMe extends Fragment {

    private Button btnEditProFile, btnChangePassword, btnLogout;
    private TextView tvName, tvBirthday, tvPhone, tvEmail, tvAddress;
    private ImageView imgAvatar;
    private ImageButton btnReload;
    private SQLiteContactHelper sqLiteContactHelper;
    private  int id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);
        initView(view);
        savedInstanceState  = FragmentAboutMe.this.getActivity().getIntent().getExtras();
        id = savedInstanceState.getInt("id");
        loadData(id);
        editProfile(id);
        logOut(view);
        changePassword(id);
        return view;
    }

    @Override
    public void onResume() {
        loadData(id);
        super.onResume();
    }


    private void editProfile(int id) {
        btnEditProFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Coming edit profile screen 🛠....", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void changePassword( int id) {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Coming change password screen 🔐....", Toast.LENGTH_LONG).show();
               Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
               intent.putExtra("id", id);
               startActivity(intent);
            }
        });
    }

    private void logOut(View view){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FragmentAboutMe.this.getContext());
                alertDialogBuilder.setTitle(" ⚠ CONFIRM ⚠");
                alertDialogBuilder.setMessage("Are you sure to quit app?");
                        alertDialogBuilder.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast.makeText(FragmentAboutMe.this.getContext(),"See you again 👋😢",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(FragmentAboutMe.this.getContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });

                alertDialogBuilder.setNegativeButton("NO",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void initView(View view) {
        btnChangePassword = view.findViewById(R.id.btn_changepassword);
        btnEditProFile = view.findViewById(R.id.btn_editprofile);
        btnLogout = view.findViewById(R.id.btn_logout);
        tvName = view.findViewById(R.id.tv_name);
        tvBirthday = view.findViewById(R.id.tv_birthday);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAddress = view.findViewById(R.id.tv_address);
        imgAvatar = view.findViewById(R.id.img_avatarprofile);
    }

    private void loadData(int id){
        sqLiteContactHelper = new SQLiteContactHelper(FragmentAboutMe.this.getContext());

        Contact contact = sqLiteContactHelper.getContactByID(id);
        Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getAvatar(),0, contact.getAvatar().length);
        imgAvatar.setImageBitmap(bitmap);
        tvName.setText("😎 : "+contact.getName());
        tvBirthday.setText("🏢 : "+contact.getBirthday());
        tvPhone.setText("📞 : "+contact.getPhone());
        tvEmail.setText(  "💌 : "+contact.getEmail());
        tvAddress.setText("🏡 : "+contact.getAddress());
    }
}