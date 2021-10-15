package com.beyond.amber;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class UserListFragment extends Fragment {
    UserListAdapter userListAdapter = new UserListAdapter();
    UserModel userModel = new UserModel();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyclerview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        userListAdapter = new UserListAdapter();

        userModel.loadData();
        userModel.setOnLoadListener(new UserModel.OnLoadListener() {
            @Override
            public void onLoad(HashMap<String, UserData> data) {
                userListAdapter.list = new ArrayList<>();
                for (String uid: data.keySet()) {
                    UserData user = data.get(uid);
                    FirebaseUser loginData = FirebaseAuth.getInstance().getCurrentUser();
                    if(loginData.getUid().equals(uid))
                        continue;

                    boolean type = getArguments().getBoolean("findMentee",true);

                    if((type && user.findMentee) || (!type && user.findMentor)) {
                        userListAdapter.list.add(new Pair<>(uid, user));
                    }
                }
                userListAdapter.notifyDataSetChanged();
            }
        });


        rv.setAdapter(userListAdapter);
    }
}
