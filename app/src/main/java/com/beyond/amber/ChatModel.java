package com.beyond.amber;

import com.beyond.amber.dto.ChatDataList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ChatDataList data;
    OnLoadListener onLoadListener;
    String chatId;

    public ChatModel(int chatId){
        this.chatId = String.valueOf(chatId);
    }

    public ChatModel(String chatId){
        this.chatId = chatId;
    }

    public void loadData() {
        DatabaseReference myRef = database.getReference("chatRoom").child(chatId);

//        myRef.setValue("Hello, World!");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                data = dataSnapshot.getValue(ChatDataList.class);
                if (onLoadListener != null){
                    onLoadListener.onLoad(data);
                }
//                Log.e("ss", data);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public void saveData() {
        DatabaseReference myRef = database.getReference("chatRoom").child(chatId);
        myRef.setValue(data);
    }


    interface OnLoadListener{
        void onLoad(ChatDataList data);
    }
}
