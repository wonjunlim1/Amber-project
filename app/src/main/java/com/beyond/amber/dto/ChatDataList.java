package com.beyond.amber.dto;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class ChatDataList implements Serializable {
    public ArrayList<String> user = new ArrayList<>();
    public ArrayList<ChatData> chatList = new ArrayList<>();

    @Exclude public String profile;
    @Exclude public String roomName;

    public ChatData getLastChatTest(){

        if (chatList.isEmpty()) return null;

        return chatList.get(chatList.size()-1);
    }
}
