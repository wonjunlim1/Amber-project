package com.beyond.amber;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        EditText text = findViewById(R.id.text);
        ImageView send = findViewById(R.id.send);
        RecyclerView rv = findViewById(R.id.recyclerview);
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
