package io.agora.openvcall.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.agora.openvcall.R;
import io.agora.openvcall.model.Message;
import io.agora.openvcall.model.User;

public class InChannelMessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Message> mMsglist;
    private User localUser;
    protected final LayoutInflater mInflater;

    public InChannelMessageListAdapter(Activity activity, ArrayList<Message> list) {
        mInflater = activity.getLayoutInflater();
        mMsglist = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.in_channel_message, parent, false);
        return new MessageHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message msg = mMsglist.get(position);

        MessageHolder myHolder = (MessageHolder) holder;

        // 调取bitmap，需要了解服务器，新写缓存头像代码，生成bitmap
        BitmapFactory.Options opts = new BitmapFactory.Options(); // 限制条件，防止内存溢出。还需要继续设置
        Bitmap bitmap = BitmapFactory.decodeFile(msg.getIcon(),opts); // 通过路径找到头像bitmap

        myHolder.msgContent.setText(msg.getContent());

        if (true) { // 验证是否是本人发送消息，设想：新建类，存储所需所有信息，在User类中插一个值，一起传送，然后调本地对比
            myHolder.mIconR.setImageBitmap(bitmap); // 头像在右边
            myHolder.msgContent.setGravity(Gravity.END); // 文字右起
        } else {
            myHolder.mIconL.setImageBitmap(bitmap); // 头像在左边
            myHolder.msgContent.setGravity(Gravity.START); // 文字左起
        }
    }

    @Override
    public int getItemCount() {
        return mMsglist.size();
    }

    @Override
    public long getItemId(int position) {
        return mMsglist.get(position).hashCode();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        public TextView msgContent;
        public ImageView mIconL;
        public ImageView mIconR;


        public MessageHolder(View v) {
            super(v);
            msgContent = (TextView) v.findViewById(R.id.msg_content);
            mIconL = (ImageView) v.findViewById(R.id.image_profile_picture_left);
            mIconR = (ImageView) v.findViewById(R.id.image_profile_picture_right);
        }
    }
}
