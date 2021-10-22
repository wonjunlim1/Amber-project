package com.beyond.amber;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class ProfileModel {

    UserData profileData;
    OnLoadListener onLoadListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid;

    private static ProfileModel me;

    public static ProfileModel getInstance() {
        return getInstance(null);
    }

    public static ProfileModel getInstance(String uid) {
        if (uid == null || uid.equals(user.getUid())) {
            if (me == null) {
                me = new ProfileModel();
                me.loadData();
            }
            return me;
        } else {
            return new ProfileModel(uid);
        }
    }

    private ProfileModel(String uid) {
        if (uid == null) {
            this.uid = user.getUid();
        } else {
            this.uid = uid;
        }
    }

    private ProfileModel() {
        this(null);
    }

    public boolean isMine() {
        return uid.equals(user.getUid());
    }

    public void loadData() {
        DatabaseReference myRef = database.getReference("users").child(uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                profileData = dataSnapshot.getValue(UserData.class);

                if (onLoadListener != null) {
                    onLoadListener.onLoad(profileData);
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
        DatabaseReference myRef = database.getReference("users").child(uid);
        profileData = saveData;
        myRef.setValue(saveData);
    }


    public int newChat() {
        for (String uid : profileData.chatRoomList.keySet()) {
            if (uid.equals(user.getUid())) return profileData.chatRoomList.get(uid);
        }

        ChatRoomModel model = ChatRoomModel.getInstance();

        int newChatId = model.newChatRoom();

        profileData.chatRoomList.put(user.getUid(), newChatId);
        me.profileData.chatRoomList.put(uid, newChatId);

        saveData(profileData);
        me.saveData(me.profileData);
        return newChatId;
    }
}