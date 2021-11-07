package com.beyond.amber;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {
    ChatAdapter chatAdapter;
    ChatModel chatmodel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int chatId = getIntent().getIntExtra("chatID", 0);
        String img = getIntent().getStringExtra("img");
        String name = getIntent().getStringExtra("name");
        chatmodel = new ChatModel(chatId);

        setContentView(R.layout.activity_chat);
        ImageView profile = findViewById(R.id.profile);
        TextView nametxt = findViewById(R.id.name);
        EditText text = findViewById(R.id.text);
        ImageView send = findViewById(R.id.send);
        RecyclerView rv = findViewById(R.id.recyclerview);


        nametxt.setText(name);

        FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
        StorageReference storageRef = storage.getReference("images").child(img); // 스토리지 공간을 참조해서 이미지를 가져옴
        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Glide.with(profile)
                        .load(task.getResult())
                        .placeholder(R.drawable.ic_face_black_48dp)
                        .into(profile);
            }
        });



        rv.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter();


        rv.setAdapter(chatAdapter);

        chatmodel.loadData();

        chatmodel.setOnLoadListener(new ChatModel.OnLoadListener() {
            @Override
            public void onLoad(ChatDataList data) {
                chatAdapter.list = data.chatList;
                chatAdapter.notifyDataSetChanged();
                rv.scrollToPosition(chatAdapter.list.size()-1);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatData item = new ChatData();
                item.msg = text.getText().toString();
                if(item.msg.replaceAll(" ", "").isEmpty()) return;
                item.time = Calendar.getInstance().getTime().getTime();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                item.sender = user.getUid();

                chatmodel.data.chatList.add(item);
                chatmodel.saveData();
                text.setText("");
                chatAdapter.notifyDataSetChanged();

            }
        });


    }
}
