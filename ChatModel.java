package com.beyond.amber;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ChatModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ChatDataList data;
    OnLoadListener onLoadListener;

    public void loadData() {
        DatabaseReference myRef = database.getReference("chatRoom").child("1");

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

    interface OnLoadListener{
        void onLoad(ChatDataList data);
    }
}
