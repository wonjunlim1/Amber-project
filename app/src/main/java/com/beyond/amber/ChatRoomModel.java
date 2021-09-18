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
    ArrayList<ChatRoomData> data;
    OnLoadListener onLoadListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public void loadData(){
        DatabaseReference myRef = database.getReference("chatRoom");

        //myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                data = dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<ChatRoomData>>(){});

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


    interface OnLoadListener{
        void onLoad(ArrayList<ChatRoomData> data);
    }
}
