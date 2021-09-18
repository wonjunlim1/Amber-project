package com.beyond.amber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.zip.Inflater;

public class ChatRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<ChatRoomData> list = null;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_call, parent, false);
        return new ChatRoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ChatRoomViewHolder){
            ChatRoomViewHolder h = (ChatRoomViewHolder) holder;
            ChatRoomData item = list.get(position);

            Glide
                    .with(h.img)
                    .load(item.profile)
                    .circleCrop()
                    //.placeholder(R.drawable.loading_spinner)
                    .into(h.img);

            h.txt_name.setText(item.roomName);
//            h.txt_msg.setText(item.message);
        }

    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;

        return list.size();
    }
    class ChatRoomViewHolder extends RecyclerView.ViewHolder{
        TextView txt_name;
        TextView txt_msg;
        ImageView img;
        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.name);
            txt_msg = itemView.findViewById(R.id.message);
            img = itemView.findViewById(R.id.profile);

        }
    }
}
