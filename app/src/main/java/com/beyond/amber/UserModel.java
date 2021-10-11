package com.beyond.amber;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserModel {

    HashMap<String, UserData> userData;
    UserModel.OnLoadListener onLoadListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public void loadData(){
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userData = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, UserData>>(){});

                if (onLoadListener != null){
                    onLoadListener.onLoad(userData);
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
        void onLoad(HashMap<String, UserData> data);
    }

}
