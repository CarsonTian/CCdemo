package io.agora.openvcall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import io.agora.openvcall.R;
import io.agora.openvcall.model.ConstantApp;

/**
 * Created by tianjq1 on 2017/12/5.
 */

public class RoomActivity  extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
    }

    @Override
    protected void initUIandEvent() {

        Intent i = getIntent();

        String channelName = i.getStringExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME);

        final String encryptionKey = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY);

        final String encryptionMode = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE);

        TextView textChannelName = (TextView) findViewById(R.id.channel_name);
        textChannelName.setText(channelName);
    }

    @Override
    protected void deInitUIandEvent() {

    }
}
