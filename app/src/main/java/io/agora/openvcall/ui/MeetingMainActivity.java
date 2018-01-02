package io.agora.openvcall.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.agora.openvcall.R;
import io.agora.openvcall.model.ConstantApp;
import io.agora.recycleview.RoomInfo;
import io.agora.recycleview.RoomViewAdapter;

public class MeetingMainActivity extends MeetingBaseActivity {

    private SearchView searchView;
    private List<RoomInfo> list;
    private List<RoomInfo> findList;
    private RoomViewAdapter adapter;
    private RoomViewAdapter findAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_list);
    }

    @Override
    protected void initUIandEvent() {
        searchView = (SearchView) findViewById(R.id.searchEdit);
        findList = new ArrayList<RoomInfo>();
        iniData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MeetingMainActivity.this));
        adapter = new RoomViewAdapter(list);
        adapter.setOnItemClickListener(new RoomViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int p = (mRecyclerView.getChildAdapterPosition(view)); //  Item 位置序号
                RoomInfo ri = list.get(p); // 当前item
                // 加验证有无加密
                if (ri.getLock()) {
                    keyDialog(ri.getKey(), ri.getName());
                } else {
                    String cName = list.get(p).getName();
                    forwardToRoom(cName, false);
                }
            }
        });
        mRecyclerView.setAdapter(adapter);

        searchView.setSubmitButtonEnabled(true); // 增加提交建
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // 搜索框监听
            public boolean onQueryTextSubmit(String res) { // 当提交时监听
                if (TextUtils.isEmpty(res)) {
                    Toast.makeText(MeetingMainActivity.this, "请输入查找内容！", Toast.LENGTH_SHORT).show();
                    mRecyclerView.setAdapter(adapter);
                } else {
                    findList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        RoomInfo info = list.get(i);
                        if (info.getName().equals(res)) { // 遍历所有item查找是否匹配搜索值
                            findList.add(info);
                            break;
                        }
                    }
                    if (findList.size() == 0) {
                        Toast.makeText(MeetingMainActivity.this, "查找的商品不在列表中", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MeetingMainActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                        findAdapter = new RoomViewAdapter(findList);
                        findAdapter.setOnItemClickListener(new RoomViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view) {
                                int p = (mRecyclerView.getChildAdapterPosition(view)); //  Item 位置序号
                                RoomInfo ri = list.get(p); // 当前item
                                // 加验证有无加密
                                if (ri.getLock()) {
                                    keyDialog(ri.getKey(), ri.getName());
                                } else {
                                    String cName = list.get(p).getName();
                                    forwardToRoom(cName, false);
                                }
                            }
                        });
                        mRecyclerView.setAdapter(findAdapter);
                    }
                }
                return true;
            }

            public boolean onQueryTextChange(String res) { // 搜索框文本改变监听，用于模糊搜索
                if (TextUtils.isEmpty(res)) {
                    mRecyclerView.setAdapter(adapter);
                } else {
                    findList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        RoomInfo information = list.get(i);
                        if (information.getName().contains(res)) { // 包含输入值的所有结果
                            findList.add(information);
                        }
                    }
                    findAdapter = new RoomViewAdapter(findList);
                    findAdapter.notifyDataSetChanged();
                    findAdapter.setOnItemClickListener(new RoomViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view) {
                            int p = (mRecyclerView.getChildAdapterPosition(view)); //  Item 位置序号
                            RoomInfo ri = list.get(p); // 当前item
                            // 加验证有无加密
                            if (ri.getLock()) {
                                keyDialog(ri.getKey(), ri.getName());
                            } else {
                                String cName = list.get(p).getName();
                                forwardToRoom(cName, false);
                            }
                        }
                    });
                    mRecyclerView.setAdapter(findAdapter);
                }
                return true;
            }
        });

        ImageView btn_create_room = (ImageView) findViewById(R.id.button_create_room);
        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(v);
            }
        });

        //EditText v_channel = (EditText) findViewById(R.id.channel_name);
        //Spinner encryptionSpinner = (Spinner) findViewById(R.id.encryption_mode);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.encryption_mode_values, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //encryptionSpinner.setAdapter(adapter);

        /**
         encryptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vSettings().mEncryptionModeIndex = position;
        }

        @Override public void onNothingSelected(AdapterView<?> parent) {

        }
        });
         */

        //encryptionSpinner.setSelection(vSettings().mEncryptionModeIndex);

        /**
         String lastChannelName = vSettings().mChannelName;
         if (!TextUtils.isEmpty(lastChannelName)) {
         v_channel.setText(lastChannelName);
         v_channel.setSelection(lastChannelName.length());
         }

         EditText v_encryption_key = (EditText) findViewById(R.id.encryption_key);
         String lastEncryptionKey = vSettings().mEncryptionKey;
         if (!TextUtils.isEmpty(lastEncryptionKey)) {
         v_encryption_key.setText(lastEncryptionKey);
         }
         */
    }

    private void createDialog(View view) {
        final EditText edt_room_name = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_title_room_name)
                .setView(edt_room_name)
                .setPositiveButton(R.string.dialog_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String roomName = edt_room_name.getText().toString().trim();
                        if (roomName.length() == 0) {
                            Toast.makeText(MeetingMainActivity.this, R.string.dialog_tips_no_name, Toast.LENGTH_SHORT).show();
                        } else {
                            int max = Integer.valueOf(getString(R.string.random_max));
                            int min = Integer.valueOf(getString(R.string.random_min));
                            int s = (int) (Math.random() * max) + min;
                            String name = roomName + "#" + s;
                            forwardToRoom(name, true);
                            dialog.dismiss();
                            // 创建上传服务器
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_negative_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog viewDialog = builder.create();
        viewDialog.setCanceledOnTouchOutside(false);
        viewDialog.show();
    }

    @Override
    protected void deInitUIandEvent() {
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                forwardToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 匹配出事数据
    private void iniData() {
        list = new ArrayList<RoomInfo>();
        RoomInfo iconInfo1 = new RoomInfo();
        iconInfo1.setName("Beer");
        iconInfo1.setTime("11.11");
        iconInfo1.setPeople("12");
        iconInfo1.setLock(true);
        iconInfo1.setKey("123456");
        list.add(iconInfo1);
        RoomInfo iconInfo2 = new RoomInfo();
        iconInfo2.setName("face");
        iconInfo2.setTime("11.11");
        iconInfo2.setPeople("12");
        iconInfo2.setLock(false);
        list.add(iconInfo2);
    }

    private void keyDialog(final String k, final String name) {
        final Dialog passwordDialog = new Dialog(MeetingMainActivity.this);
        passwordDialog.setContentView(R.layout.dialog_input_password);
        //passwordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final EditText pInput = (EditText)passwordDialog.findViewById(R.id.password_input);
        TextView pTitle = (TextView)passwordDialog.findViewById(R.id.password_title);
        ImageView pConfirm = (ImageView)passwordDialog.findViewById(R.id.password_confirm);
        ImageView pCancel = (ImageView)passwordDialog.findViewById(R.id.password_cancel);

        pTitle.setText(R.string.dialog_title_password_input);

        pConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = pInput.getText().toString().trim();
                if (k.equals(key)) {
                    forwardToRoom(name, false);
                    passwordDialog.dismiss();
                } else {
                    Toast.makeText(MeetingMainActivity.this, R.string.dialog_tips_wrong_password, Toast.LENGTH_SHORT).show();
                    pInput.setText("");
                }
            }
        });

        pCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordDialog.dismiss();
            }
        });

        if (passwordDialog != null) {
            passwordDialog.show();
        }
    }


    public void forwardToRoom(String cn, boolean io) {
        vSettings().mChannelName = cn;
        String encryption = ""; // 声望自带加密密码
        vSettings().mEncryptionKey = encryption;

        Intent i = new Intent(MeetingMainActivity.this, MeetingChatActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, cn);
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, encryption);
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, getResources().getStringArray(R.array.encryption_mode_values)[0]); // 0 为默认加密方式
        i.putExtra(ConstantApp.IF_OWNER, io); // 是否为创建着
        startActivity(i);
    }

    public void forwardToSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

}
