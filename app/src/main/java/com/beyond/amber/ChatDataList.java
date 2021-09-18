package com.beyond.amber;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ChatDataList implements Serializable {
    public ArrayList<Integer> user;
    public ArrayList<ChatData> chatList;
    public String profile;

}
