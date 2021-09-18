package com.beyond.amber;

import android.os.Bundle;
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
    ChatRoomModel chatRoomModel = new ChatRoomModel();
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
            public void onLoad(ArrayList<ChatRoomData> data) {
                chatRoomAdapter.list = data;
                chatRoomAdapter.notifyDataSetChanged();
            }
        });


        rv.setAdapter(chatRoomAdapter);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
               Toast.makeText(ChatRoomActivity.this,value,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
