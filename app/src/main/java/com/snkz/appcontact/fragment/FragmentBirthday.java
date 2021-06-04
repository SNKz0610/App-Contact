package com.snkz.appcontact.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snkz.appcontact.R;
import com.snkz.appcontact.model.Contact;
import com.snkz.appcontact.recyclerview.RecyclerViewBirthday;
import com.snkz.appcontact.sqlite.SQLiteContactHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentBirthday extends Fragment {
    private TextView tvNotification;
    private RecyclerView rvBirthday;
    private RecyclerViewBirthday adapter;
    private SQLiteContactHelper sqLiteContactHelper;
    private ArrayList<Contact> arrBirthday;
    private ArrayList<Contact> arrContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_birthday, container, false);
        sqLiteContactHelper = new SQLiteContactHelper(view.getContext());
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvBirthday.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerViewBirthday();
    }

    @Override
    public void onResume() {
        int check = 0;
        arrBirthday = new ArrayList<>();
        sqLiteContactHelper = new SQLiteContactHelper(getContext());
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String curentDate = simpleDateFormat.format(calendar1.getTime());
        arrContact = sqLiteContactHelper.getAllContactBirthday();
        String [] time1_spilt=curentDate.split("/");
        int day1=Integer.parseInt(time1_spilt[0]);
        int month1=Integer.parseInt(time1_spilt[1])-1;
        int year1=Integer.parseInt(time1_spilt[2]);
        String date2 = day1+"/"+month1;

        for (int i = 0; i < arrContact.size(); i++) {
            String birthday = arrContact.get(i).getBirthday();
            String [] time_spilt=birthday.split("/");
            int day=Integer.parseInt(time_spilt[0]);
            int month=Integer.parseInt(time_spilt[1])-1;
            int year=Integer.parseInt(time_spilt[2]);
            String date = day+"/"+month;

            if(date2.compareTo(date) == 0) {
                String name = arrContact.get(i).getName();
                byte[] avatar = arrContact.get(i).getAvatar();
                arrBirthday.add(new Contact(avatar, name));
                check++;
            }
        }

        if (check == 0){
            tvNotification.setText("No birthday found... 🥺");
            arrBirthday.clear();
            adapter.setArrContact(arrBirthday);
            rvBirthday.setAdapter(adapter);
        } else {
            tvNotification.setText("Today: " +curentDate);
            adapter.setArrContact(arrBirthday);
            rvBirthday.setAdapter(adapter);
        }
        super.onResume();
    }

    private void initView(View view) {
        tvNotification = view.findViewById(R.id.tv_notification);
        rvBirthday = view.findViewById(R.id.rev_birthday);
    }
}