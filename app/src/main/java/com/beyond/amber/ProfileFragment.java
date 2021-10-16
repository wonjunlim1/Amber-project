package com.beyond.amber;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    ProfileModel profileModel;

    TextView nameTxt;
    TextView roleTxt;
    SwitchCompat mentorSwi;
    SwitchCompat menteeSwi;
    ViewGroup mentorGroup;
    ViewGroup menteeGroup;
    Button confirmBtn;
    Button chatBtn;
    EditText mentorTxt;
    EditText menteeTxt;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String uid = null;
        if (getArguments() != null)
            uid = getArguments().getString("uid");
        profileModel = ProfileModel.getInstance(uid);

        nameTxt = view.findViewById(R.id.profile_name);
        roleTxt = view.findViewById(R.id.role);
        mentorSwi = view.findViewById(R.id.mentor_switch);
        menteeSwi = view.findViewById(R.id.mentee_switch);
        mentorGroup = view.findViewById(R.id.chip_group_mentor);
        mentorTxt = view.findViewById(R.id.mentor_txt);
        menteeGroup = view.findViewById(R.id.chip_group_mentee);
        menteeTxt = view.findViewById(R.id.mentee_txt);
        confirmBtn = view.findViewById(R.id.confirm);
        chatBtn = view.findViewById(R.id.chat_button);

        if (profileModel.isMine()) {
            chatBtn.setVisibility(View.GONE);
        } else {
            nameTxt.setEnabled(false);
            roleTxt.setEnabled(false);
            mentorSwi.setEnabled(false);
            menteeSwi.setEnabled(false);

            confirmBtn.setVisibility(View.GONE);
            mentorTxt.setVisibility(View.GONE);
            menteeTxt.setVisibility(View.GONE);
        }


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
                if (data != null) {
                    nameTxt.setText(data.name);
                    roleTxt.setText(data.role);
                    mentorSwi.setChecked(data.findMentor);
                    menteeSwi.setChecked(data.findMentee);


                    while (mentorGroup.getChildCount() > 1) {
                        mentorGroup.removeViewAt(0);
                    }
                    while (menteeGroup.getChildCount() > 1) {
                        menteeGroup.removeViewAt(0);
                    }

                    for (int i = 0; i < data.mentorHashTag.size(); i++) {
                        addChip(data.mentorHashTag.get(i), mentorGroup);
                    }
                    for (int i = 0; i < data.menteeHashTag.size(); i++) {
                        addChip(data.menteeHashTag.get(i), menteeGroup);
                    }

                }
                confirmBtn.setEnabled(true);
            }
        });


        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileModel.newChat();
            }
        });

        mentorTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addChip(mentorTxt.getText().toString(), mentorGroup);
                mentorTxt.setText("");
                return true;
            }
        });

        mentorTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    addChip(mentorTxt.getText().toString(), mentorGroup);
                    mentorTxt.setText("");
                }
            }
        });

        menteeTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addChip(menteeTxt.getText().toString(), menteeGroup);
                menteeTxt.setText("");
                return true;
            }
        });
        menteeTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    addChip(menteeTxt.getText().toString(), menteeGroup);
                    menteeTxt.setText("");
                }
            }
        });

    }

    void submit() {
        UserData userData = new UserData();

        userData.name = nameTxt.getText().toString();
        userData.role = roleTxt.getText().toString();
        userData.findMentor = mentorSwi.isChecked();
        userData.findMentee = menteeSwi.isChecked();

        for (int i = 0; i < mentorGroup.getChildCount() - 1; i++) {
            Chip chip = (Chip) mentorGroup.getChildAt(i);
            userData.mentorHashTag.add(chip.getText().toString());
        }
        for (int i = 0; i < menteeGroup.getChildCount() - 1; i++) {
            Chip chip = (Chip) menteeGroup.getChildAt(i);
            userData.menteeHashTag.add(chip.getText().toString());
        }


        profileModel.saveData(userData);
    }

    void addChip(String text, ViewGroup parent) {

        if (text.replaceAll(" ", "").isEmpty()) return;

        Chip chip = new Chip(getContext());
        chip.setText(text);
        chip.setCloseIconVisible(profileModel.isMine());
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.removeView(view);
            }
        });
        parent.addView(chip, parent.getChildCount() - 1);
    }
}
