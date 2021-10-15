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

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Pair<Integer, ChatDataList>> list = null;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_call, parent, false);
        return new ChatRoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatRoomViewHolder) {
            ChatRoomViewHolder h = (ChatRoomViewHolder) holder;
            ChatDataList item = list.get(position).second;

            if (item.profile != null) {
                Glide
                        .with(h.img)
                        .load(item.profile)
                        .circleCrop()
                        //.placeholder(R.drawable.loading_spinner)
                        .into(h.img);
            }

            h.txt_name.setText(item.roomName);
            h.txt_msg.setText(item.getLastChatTest().msg);
        }

    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;

        return list.size();
    }

    class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        TextView txt_msg;
        ImageView img;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();


                    Intent intent = new Intent(itemView.getContext(), ChatActivity.class);
                    intent.putExtra("chatID", list.get(position).first);
                    itemView.getContext().startActivity(intent);
                }
            });

            txt_name = itemView.findViewById(R.id.name);
            txt_msg = itemView.findViewById(R.id.message);
            img = itemView.findViewById(R.id.profile);

        }
    }
}
