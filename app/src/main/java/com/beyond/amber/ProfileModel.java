package com.beyond.amber;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileModel {

    UserData ProfileData;
    OnLoadListener onLoadListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public void loadData() {
        DatabaseReference myRef = database.getReference("users").child(user.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ProfileData = dataSnapshot.getValue(UserData.class);

                if (onLoadListener != null) {
                    onLoadListener.onLoad(ProfileData);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void setOnLoadListener(OnLoadListener listener) {
        onLoadListener = listener;
    }


    interface OnLoadListener {
        void onLoad(UserData data);
    }

    public void saveData(UserData saveData) {
        DatabaseReference myRef = database.getReference("users").child(user.getUid());
        myRef.setValue(saveData);

    }

}