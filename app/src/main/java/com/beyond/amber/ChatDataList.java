package com.beyond.amber;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
