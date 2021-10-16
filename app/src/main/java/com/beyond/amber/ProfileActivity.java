package com.beyond.amber;

import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String uid = getIntent().getStringExtra("uid");

        Fragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        fragment.setArguments(bundle);



        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout, fragment)
                .commit();
    }

}
