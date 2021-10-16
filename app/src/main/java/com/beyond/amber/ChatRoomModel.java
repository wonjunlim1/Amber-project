package com.beyond.amber;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatRoomModel {
    ArrayList<ChatDataList> data;
    OnLoadListener onLoadListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    static ChatRoomModel instance;

    public static ChatRoomModel getInstance() {
        if (instance == null){
            instance = new ChatRoomModel();
            instance.loadData();
        }
        return instance;
    }

    private ChatRoomModel(){}

    public void loadData(){
        DatabaseReference myRef = database.getReference("chatRoom");

        //myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                data = dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<ChatDataList>>(){});

                if (onLoadListener != null){
                    onLoadListener.onLoad(data);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void setOnLoadListener(OnLoadListener listener){
        onLoadListener = listener;
    }

    public int newChatRoom(){
        ChatDataList item = new ChatDataList();

        //item.user = data.get(0).user;
        //item.chatList =  data.get(0).chatList;

        item.profile = "";
        item.roomName = "Test";

        data.add(item);
        saveData();
        return data.size() - 1;
    }

    public void saveData(){
        DatabaseReference myRef = database.getReference("chatRoom");
        myRef.setValue(data);
    }

    interface OnLoadListener{
        void onLoad(ArrayList<ChatDataList> data);
    }
}
