package com.snkz.appcontact.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.snkz.appcontact.R;
import com.snkz.appcontact.fragment.FragmentAdapter;
import com.snkz.appcontact.model.Contact;
import com.snkz.appcontact.sqlite.SQLiteContactHelper;

public class MainActivity extends AppCompatActivity {
    private SQLiteContactHelper sqLiteContactHelper;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bottom_nav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_contact:
                viewPager.setCurrentItem(0);
                bottomNavigationView.getMenu().findItem(R.id.action_contact).setChecked(true);
                break;
            case R.id.action_birthday:
                viewPager.setCurrentItem(1);
                bottomNavigationView.getMenu().findItem(R.id.action_birthday).setChecked(true);
                break;
            case R.id.action_aboutme:
                viewPager.setCurrentItem(2);
                bottomNavigationView.getMenu().findItem(R.id.action_aboutme).setChecked(true);
                break;
            default:
                viewPager.setCurrentItem(0);
                bottomNavigationView.getMenu().findItem(R.id.action_contact).setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.btn_navigation);
        viewPager = findViewById(R.id.viewpager);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        Bundle extras = getIntent().getExtras();
//        sqLiteContactHelper = new SQLiteContactHelper(this);
//
//
//        Intent intent = this.getIntent();
//        Contact contact = sqLiteContactHelper.getContactByID(extras.getInt("id"));
//        Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getAvatar(),0, contact.getAvatar().length);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_contact).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_birthday).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_aboutme).setChecked(true);
                        break;
                    default:
                        bottomNavigationView.getMenu().findItem(R.id.action_contact).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(fragmentAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_contact:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_birthday:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_aboutme:
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        viewPager.setCurrentItem(0);
                        break;
                }
                return true;
            }
        });
    }


}