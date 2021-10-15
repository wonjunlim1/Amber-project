package com.beyond.amber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

public class TabFragment extends Fragment {
    UserListFragment mentorListFragment = new UserListFragment();
    UserListFragment menteeListFragment = new UserListFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle.putBoolean("findMentee", true);
        mentorListFragment.setArguments(bundle);


        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("findMentee", false);
        menteeListFragment.setArguments(bundle2);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.userlist_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout v = view.findViewById(R.id.tab_layout);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, mentorListFragment)
                .commit();

        v.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getText().equals("멘토")) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout, mentorListFragment)
                            .commit();
                }
                else {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout, menteeListFragment)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
