package com.beyond.amber;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.beyond.amber.dto.UserData;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    ImageView profilePic;

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
        profilePic = view.findViewById(R.id.profile_picture);

        if (profileModel.isMine()) {
            chatBtn.setVisibility(View.GONE);
        } else {
            nameTxt.setEnabled(false);
            roleTxt.setEnabled(false);
            menteeSwi.setClickable(false);
            mentorSwi.setClickable(false);

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
                    if(data.img != null){
                        FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
                        StorageReference storageRef = storage.getReference("images").child(data.img); // 스토리지 공간을 참조해서 이미지를 가져옴
                        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Glide.with(view)
                                        .load(task.getResult())
                                        .circleCrop()
                                        .placeholder(R.drawable.ic_face_black_48dp)
                                        .into(profilePic);

                            }
                        });
                    }

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

                int chatId = profileModel.newChat();
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("chatID", chatId);
                intent.putExtra("img", profileModel.profileData.img);
                intent.putExtra("name", profileModel.profileData.name);
                startActivity(intent);
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!profileModel.isMine()) return;

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                // 갤러리 액티비티로부터 가져온 결과 데이터를 처리하기 위해
                // StartActivityForResult() 함수를 통해 액티비티를 실행
                startActivityForResult(intent, 111);
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
    public void onResume() {
        super.onResume();

        profileModel.loadData();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && data != null) {
            Uri file = data.getData();
            String path = "images/" + file.getLastPathSegment();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference riversRef = storage.getReference().child(path);
            UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    exception.printStackTrace();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...

                    profilePic.setImageURI(file);
                    profilePic.setTag(file.getLastPathSegment());
                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                    Log.d("Upload",taskSnapshot.getUploadSessionUri().getEncodedPath());
                }
            });
        }
    }


    void submit() {
        UserData userData = profileModel.profileData;

        if (profilePic.getTag() != null){
            userData.img = profilePic.getTag().toString();
        }

        userData.name = nameTxt.getText().toString();
        userData.role = roleTxt.getText().toString();
        userData.findMentor = mentorSwi.isChecked();
        userData.findMentee = menteeSwi.isChecked();


        userData.mentorHashTag.clear();
        for (int i = 0; i < mentorGroup.getChildCount() - 1; i++) {
            Chip chip = (Chip) mentorGroup.getChildAt(i);
            userData.mentorHashTag.add(chip.getText().toString());
        }

        userData.menteeHashTag.clear();
        for (int i = 0; i < menteeGroup.getChildCount() - 1; i++) {
            Chip chip = (Chip) menteeGroup.getChildAt(i);
            userData.menteeHashTag.add(chip.getText().toString());
        }


        profileModel.saveData(userData);
        Toast.makeText(getContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
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
