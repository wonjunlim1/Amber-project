package com.beyond.amber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ChatData> list = null;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v  = inflater.inflate(R.layout.item_chat_amber,parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ChatViewHolder) {
            ChatViewHolder h = (ChatViewHolder) holder;
            ChatData item = list.get(position);
            h.txt.setText(item.msg);

        }
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }
    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.amberitem);

        }
    }
}
