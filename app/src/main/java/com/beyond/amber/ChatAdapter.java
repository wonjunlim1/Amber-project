package com.beyond.amber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_OTHERS = 0;
    private final int VIEW_TYPE_USER = 1;
    List<ChatData> list = null;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(viewType== VIEW_TYPE_USER) {
            View v = inflater.inflate(R.layout.item_chat_user, parent, false);
            return new ChatViewHolder(v);
        }
        else{
            View v = inflater.inflate(R.layout.item_chat_others, parent, false);
            return new ChatViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).sender.equals(user.getUid())) {
            return VIEW_TYPE_USER;
        }
        else {
            return VIEW_TYPE_OTHERS;
        }
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
            txt = itemView.findViewById(R.id.chat);

        }
    }
}
