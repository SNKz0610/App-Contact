package com.snkz.appcontact.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.snkz.appcontact.R;
import com.snkz.appcontact.activity.AddContactActivity;
import com.snkz.appcontact.model.Contact;
import com.snkz.appcontact.recyclerview.RecyclerViewContact;
import com.snkz.appcontact.sqlite.SQLiteContactHelper;

import java.util.ArrayList;

public class FragmentContact extends Fragment {
    private SearchView searchView;
    private ImageButton btnReload;
    private RecyclerView rvContact;
    private FloatingActionButton floatingActionButton;
    private RecyclerViewContact recyclerViewContact;
    private SQLiteContactHelper sqLiteContactHelper;
    private int id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        sqLiteContactHelper = new SQLiteContactHelper(view.getContext());
        initView(view);
        addContact();
        reloadRecycleView();
        searchContact();
        return view;
    }

    @Override
    public void onResume() {
        ArrayList<Contact> arrContact = sqLiteContactHelper.loadingContactIntoList();
        recyclerViewContact.setArrContact(arrContact);
        rvContact.setAdapter(recyclerViewContact);
        super.onResume();
    }

    private void searchContact() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Contact> arrContact = sqLiteContactHelper.getContactByName(newText);
                recyclerViewContact.setArrContact(arrContact);
                rvContact.setAdapter(recyclerViewContact);
                return true;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvContact.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewContact = new RecyclerViewContact();
    }

    private void reloadRecycleView() {
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Contact> arrContact = sqLiteContactHelper.loadingContactIntoList();
                recyclerViewContact.setArrContact(arrContact);
                rvContact.setAdapter(recyclerViewContact);
            }
        });
    }

    private void addContact() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Coming add contact screen ✈....", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FragmentContact.this.getContext(), AddContactActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        searchView = view.findViewById(R.id.searchview);
        btnReload = view.findViewById(R.id.btn_reloadcontact);
        rvContact = view.findViewById(R.id.rev_contact);
        floatingActionButton = view.findViewById(R.id.btn_floadadd);
    }


}