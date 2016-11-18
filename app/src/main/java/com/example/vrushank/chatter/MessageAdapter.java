package com.example.vrushank.chatter;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vrushank on 18/11/16.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<com.example.vrushank.chatter.Message> list;
    private Context context;

    public MessageAdapter(Context context, ArrayList<com.example.vrushank.chatter.Message> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 1:
                return new MessageOtherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.graphic_other, parent, false));
            case 0:
                return new MessageSelfViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.graphic_self, parent, false));
            case 2:
                return new BlankViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.blank_chat, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemType = getItemViewType(position);
        if (itemType == 0) {
            ((MessageSelfViewHolder) holder).message.setText(list.get(position).getMessage());
            ((MessageSelfViewHolder) holder).sender.setText(list.get(position).getSender());
        } else if (itemType == 1) {
            ((MessageOtherViewHolder) holder).message.setText(list.get(position).getMessage());
            ((MessageOtherViewHolder) holder).sender.setText(list.get(position).getSender());
        } else if (itemType == 2) {
        } else {
            Log.d("MessageAdapter", "Invalid item type");
        }
    }

    @Override
    public int getItemCount() {

        if(list.size() > 0)
            return list.size();
        else
            return 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (list.size() > 0) {
            if (list.get(position).isSelf())
                return 0;
            else
                return 1;
        } else {
            return 2;
        }
    }

    private class MessageSelfViewHolder extends RecyclerView.ViewHolder {

        private TextView sender, message;

        public MessageSelfViewHolder(View itemView) {
            super(itemView);
            sender = (TextView) itemView.findViewById(R.id.senderTextSelf);
            message = (TextView) itemView.findViewById(R.id.message_text_self);
        }
    }

    private class MessageOtherViewHolder extends RecyclerView.ViewHolder {

        private TextView sender, message;

        public MessageOtherViewHolder(View itemView) {
            super(itemView);
            sender = (TextView) itemView.findViewById(R.id.senderTextOther);
            message = (TextView) itemView.findViewById(R.id.message_text_other);
        }
    }

    private class BlankViewHolder extends RecyclerView.ViewHolder {
        public BlankViewHolder(View itemView) {
            super(itemView);
        }
    }
}