package com.beyond.amber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
    UserListFragment userListFragment = new UserListFragment();
    TabFragment tabFragment = new TabFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, profileFragment)
                .commit();


        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_search:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_layout, tabFragment)
                                .commit();
                        break;
                    case R.id.page_chat:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_layout, chatRoomFragment)
                                .commit();
                        break;
                    case R.id.page_profile:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_layout, profileFragment)
                                .commit();
                        break;
                }

                return true;
            }
        });
    }
}