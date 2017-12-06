package io.agora.openvcall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.agora.openvcall.R;
import io.agora.openvcall.model.ConstantApp;
import io.agora.openvcall.model.User;

/**
 * Created by tianjq1 on 2017/12/5.
 */

public class RoomActivity extends BaseActivity {

    String channelName;
    String encryptionKey;
    String encryptionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
    }

    @Override
    protected void initUIandEvent() {

        Intent i = getIntent();

        channelName = i.getStringExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME);

        encryptionKey = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY);

        encryptionMode = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE);

        TextView textChannelName = (TextView) findViewById(R.id.channel_name);
        textChannelName.setText(channelName);
    }

    @Override
    protected void deInitUIandEvent() {

    }

    public void onClickJoinPosition(View view) {
        boolean ifAudience = false;
        forwardToRoom(ifAudience);
    }

    public void forwardToRoom(boolean b) {
        //EditText v_channel = (EditText) findViewById(R.id.channel_name);
        //String channel = v_channel.getText().toString();
        //vSettings().mChannelName = channel;

        //EditText v_encryption_key = (EditText) findViewById(R.id.encryption_key);
        //String encryption = v_encryption_key.getText().toString();
        //vSettings().mEncryptionKey = encryption;


        Intent i = new Intent(RoomActivity.this, ChatActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, channelName);
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, encryptionKey);
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, getResources().getStringArray(R.array.encryption_mode_values)[vSettings().mEncryptionModeIndex]);
        i.putExtra(ConstantApp.ACTION_KEY_MEMBER_STUTES, b);
        startActivity(i);
    }

    public void onClickJoinAudience(View view) {
        boolean ifAudience = true;
        forwardToRoom(ifAudience);
    }
}
