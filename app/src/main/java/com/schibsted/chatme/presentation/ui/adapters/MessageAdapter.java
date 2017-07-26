package com.schibsted.chatme.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.schibsted.chatme.R;
import com.schibsted.chatme.data.MessageEntity;
import com.schibsted.chatme.presentation.app.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by diego.galico
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MessageEntity> mListMessages;

    public MessageAdapter(List<MessageEntity> listMessages) {
        mListMessages = listMessages;
    }

    public List<MessageEntity> getListMessages() {
        return mListMessages;
    }

    public enum ViewType {

        VIEW_TYPE_MY_MESSAGE(0),
        VIEW_TYPE_OTHER_MESSAGE(1);

        private int value;

        ViewType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        if (viewType == ViewType.VIEW_TYPE_MY_MESSAGE.getValue()) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_my_message, viewGroup, false);
            return new MyMessageViewHolder(view);
        } else if (viewType == ViewType.VIEW_TYPE_OTHER_MESSAGE.getValue()) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_other_message, viewGroup, false);
            return new OtherMessagesViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return mListMessages.get(position).isMyMessage() ? ViewType.VIEW_TYPE_MY_MESSAGE.getValue() : ViewType.VIEW_TYPE_OTHER_MESSAGE.getValue();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MessageEntity message = mListMessages.get(position);

        if (holder instanceof MyMessageViewHolder) {
            MyMessageViewHolder myMessageViewHolder = (MyMessageViewHolder) holder;
            myMessageViewHolder.myMessageTitle.setText(message.getContent());
            myMessageViewHolder.myMessageSubtitle.setText(message.getTime());
        } else if (holder instanceof OtherMessagesViewHolder) {
            OtherMessagesViewHolder otherMessageViewHolder = (OtherMessagesViewHolder) holder;
            otherMessageViewHolder.otherMessageTitle.setText(message.getContent());
            otherMessageViewHolder.otherMessageSubtitle.setText(message.getUsername() + Constants.SEPARATION_STRING + message.getTime());
            Picasso.with(mContext)
                    .load(message.getImageUrl())
                    .placeholder(R.drawable.user_placeholder)
                    .into(otherMessageViewHolder.userPicture);
        }
    }

    public void appendMessage(MessageEntity message) {
        mListMessages.add(message);
        notifyItemInserted(mListMessages.size());
    }

    @Override
    public int getItemCount() {
        return mListMessages.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof OtherMessagesViewHolder) {
            ((OtherMessagesViewHolder) holder).cleanup();
        }

    }

    private class MyMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView myMessageTitle;
        public TextView myMessageSubtitle;

        public MyMessageViewHolder(View itemView) {
            super(itemView);
            myMessageTitle = (TextView) itemView.findViewById(R.id.text_view_my_message_title);
            myMessageSubtitle = (TextView) itemView.findViewById(R.id.text_view_my_message_subtitle);
        }
    }

    class OtherMessagesViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView userPicture;
        public TextView otherMessageTitle;
        public TextView otherMessageSubtitle;

        public OtherMessagesViewHolder(View itemView) {
            super(itemView);
            userPicture = (CircleImageView) itemView.findViewById(R.id.circle_image_view_user_picture);
            otherMessageTitle = (TextView) itemView.findViewById(R.id.text_view_other_message_title);
            otherMessageSubtitle = (TextView) itemView.findViewById(R.id.text_view__other_message_subtitle);
        }

        // Clean Picasso request
        public void cleanup() {
            Picasso.with(mContext)
                    .cancelRequest(userPicture);
            userPicture.setImageDrawable(null);
        }
    }


}
