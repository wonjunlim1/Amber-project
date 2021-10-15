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

public class ChatRoomFragment extends Fragment {

    ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter();
    ChatRoomModel chatRoomModel = new ChatRoomModel();
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

        chatRoomModel.loadData();
        chatRoomModel.setOnLoadListener(new ChatRoomModel.OnLoadListener() {
            @Override
            public void onLoad(ArrayList<ChatDataList> data) {
                ArrayList<Pair<Integer, ChatDataList>> list = new ArrayList<>();
                for(int i=1; i<=data.size(); i++) {
                    list.add(new Pair<>(i, data.get(i)));
                }

                chatRoomAdapter.list = list;
                chatRoomAdapter.notifyDataSetChanged();
            }
        });


        rv.setAdapter(chatRoomAdapter);

        Button btn = view.findViewById(R.id.plus);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatRoomModel.newChatRoom();
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}
