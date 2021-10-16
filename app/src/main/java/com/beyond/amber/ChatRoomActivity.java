package com.beyond.amber;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    ChatRoomModel chatRoomModel = ChatRoomModel.getInstance();
    ChatRoomAdapter chatRoomAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        RecyclerView rv = findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));

        chatRoomAdapter = new ChatRoomAdapter();

        chatRoomModel.loadData();
        chatRoomModel.setOnLoadListener(new ChatRoomModel.OnLoadListener() {
            @Override
            public void onLoad(ArrayList<ChatDataList> data) {
                chatRoomAdapter.list = new ArrayList();

                for (int i=0;i<data.size();i++) {
                    chatRoomAdapter.list.add(new Pair<>(i,data.get(i)));
                }

                chatRoomAdapter.notifyDataSetChanged();
            }
        });


        rv.setAdapter(chatRoomAdapter);

        Button btn = findViewById(R.id.plus);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatRoomModel.newChatRoom();
            }
        });

    }

}
