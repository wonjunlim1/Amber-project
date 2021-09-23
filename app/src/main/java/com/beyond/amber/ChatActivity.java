package com.beyond.amber;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {
    ChatAdapter chatAdapter;
    ChatModel chatmodel = new ChatModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                item.time = Calendar.getInstance().getTime().getTime();
                item.sender = 0;

                chatmodel.data.chatList.add(item);
                chatmodel.saveData();
                text.setText("");
                chatAdapter.notifyDataSetChanged();

            }
        });


    }
}
