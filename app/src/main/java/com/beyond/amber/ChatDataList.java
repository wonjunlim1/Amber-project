package com.beyond.amber;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ChatDataList implements Serializable {
    @Nullable public ArrayList<Integer> user;
    @Nullable public ArrayList<ChatData> chatList;
    public String profile;
    public String roomName;


    public ChatData getLastChatTest(){

        return chatList.get(chatList.size()-1);
    }
}
