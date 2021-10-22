package com.beyond.amber;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatRoomFragment extends Fragment {

    ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter();
    ChatRoomModel chatRoomModel = ChatRoomModel.getInstance();
    ProfileModel profileModel = ProfileModel.getInstance();
    UserModel userModel = new UserModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_call, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        chatRoomAdapter = new ChatRoomAdapter();


        chatRoomModel.setOnLoadListener(new ChatRoomModel.OnLoadListener() {
            @Override
            public void onLoad(ArrayList<ChatDataList> data) {
                update();
            }
        });
        profileModel.setOnLoadListener(new ProfileModel.OnLoadListener() {
            @Override
            public void onLoad(UserData data) {
                update();
            }
        });
        userModel.setOnLoadListener(new UserModel.OnLoadListener() {
            @Override
            public void onLoad(HashMap<String, UserData> data) {
                update();
            }
        });


        rv.setAdapter(chatRoomAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        chatRoomModel.loadData();
        userModel.loadData();

    }

    public void update(){
        UserData userData = profileModel.profileData;
        ArrayList<ChatDataList> chatDataList = chatRoomModel.data;
        HashMap<String, UserData> userListData = userModel.userData;

        if (userData == null || chatDataList == null || userListData == null) return;
        ArrayList<Pair<Integer, ChatDataList>> list = new ArrayList<>();

////////////////
        for (String uid : userData.chatRoomList.keySet()) {
            int chatId = userData.chatRoomList.get(uid);
            ChatDataList chatData = chatDataList.get(chatId);
            chatData.profile = userListData.get(uid).img;
            chatData.roomName = userListData.get(uid).name;

            list.add(new Pair<>(chatId, chatData));
        }
//////////////


        chatRoomAdapter.list = list;
        chatRoomAdapter.notifyDataSetChanged();
    }
}
