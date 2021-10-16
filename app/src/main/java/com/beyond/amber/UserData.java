package com.beyond.amber;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class UserData {
    public String name;
    public String role;
    public boolean findMentor;
    public boolean findMentee;
    public ArrayList<String> mentorHashTag = new ArrayList<>();
    public ArrayList<String> menteeHashTag = new ArrayList<>();
    public HashMap<String, Integer> chatRoomList = new HashMap<>();
}
