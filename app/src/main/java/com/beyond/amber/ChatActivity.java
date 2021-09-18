package com.beyond.amber;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    ChatAdapter chatAdapter;
    ChatModel chatmodel = new ChatModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            }
        });
    }
}
