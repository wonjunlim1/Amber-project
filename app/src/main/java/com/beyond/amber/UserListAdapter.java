package com.beyond.amber;

import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<Pair<String, UserData>> list = null;
    ChatRoomActivity chatRoomActivity = new ChatRoomActivity();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_call, parent, false);
        return new UserListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserListAdapter.UserListViewHolder) {
            UserListAdapter.UserListViewHolder h = (UserListAdapter.UserListViewHolder) holder;
            UserData item = list.get(position).second;

            if (item.name != null) {
                h.txt_name.setText(item.name);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    class UserListViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        TextView txt_msg;
        ImageView img;

        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.name);
            txt_msg = itemView.findViewById(R.id.message);
            img = itemView.findViewById(R.id.profile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    intent.putExtra("uid",list.get(position).first);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
