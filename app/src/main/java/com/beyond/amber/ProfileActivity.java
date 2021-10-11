package com.beyond.amber;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class ProfileActivity extends AppCompatActivity {
    ProfileModel profileModel = new ProfileModel();

    TextView nameTxt;
    TextView roleTxt;
    SwitchCompat mentorSwi;
    SwitchCompat menteeSwi;
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTxt = findViewById(R.id.profile_name);
        roleTxt = findViewById(R.id.role);
        mentorSwi = findViewById(R.id.mentor_switch);
        menteeSwi = findViewById(R.id.mentee_switch);
        confirmBtn = findViewById(R.id.confirm);

        confirmBtn.setEnabled(false);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });


        profileModel.loadData();
        profileModel.setOnLoadListener(new ProfileModel.OnLoadListener() {
            @Override
            public void onLoad(UserData data) {
                nameTxt.setText(data.name);
                roleTxt.setText(data.role);
                mentorSwi.setChecked(data.findMentor);
                menteeSwi.setChecked(data.findMentee);
                confirmBtn.setEnabled(true);
            }
        });
    }

    void submit(){
        UserData userData = new UserData();

        userData.name = nameTxt.getText().toString();
        userData.role = roleTxt.getText().toString();
        userData.findMentor = mentorSwi.isChecked();
        userData.findMentee = menteeSwi.isChecked();

        profileModel.saveData(userData);
    }
}
