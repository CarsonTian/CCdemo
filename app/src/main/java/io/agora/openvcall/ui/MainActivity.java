package io.agora.openvcall.ui;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.agora.openvcall.R;
import io.agora.openvcall.model.ConstantApp;
import io.agora.recycleview.RoomInfo;
import io.agora.recycleview.ViewAdapter;

public class MainActivity extends BaseActivity {

    private SearchView searchView;
    private List<RoomInfo> list;
    private List<RoomInfo> findList;
    private ViewAdapter adapter;
    private ViewAdapter findAdapter;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ViewAdapter(list);
        adapter.setOnItemClickListener(new ViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int p = (mRecyclerView.getChildAdapterPosition(view)); //  Item 位置序号
                // 加验证有无加密
                if (list.get(p).getLock()) {
                    secreteDialog();
                    if (flag) {
                        if (checkSecrete(sec)) {
                            String cName = list.get(p).getName();
                            forwardToRoom(cName, false);
                        } else {
                            secreteDialog();
                        }
                    } else {
                        // 输入密码时取消
                    }
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
                    Toast.makeText(MainActivity.this, "请输入查找内容！", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this, "查找的商品不在列表中", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                        findAdapter = new ViewAdapter(findList);
                        findAdapter.setOnItemClickListener(new ViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view) {
                                if (checkSecrete(secreteDialog())) {
                                    int p = (mRecyclerView.getChildAdapterPosition(view)); //  Item 位置序号
                                    String cName = findList.get(p).getName();
                                    forwardToRoom(cName, false);
                                } else {
                                    // 密码错误提示
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
                    findAdapter = new ViewAdapter(findList);
                    findAdapter.notifyDataSetChanged();
                    findAdapter.setOnItemClickListener(new ViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view) {
                            int p = (mRecyclerView.getChildAdapterPosition(view)); //  Item 位置序号
                            String cName = findList.get(p).getName();
                            forwardToRoom(cName, false);
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
        builder.setTitle(R.string.lable_title_room_name)
                .setView(edt_room_name)
                .setPositiveButton(R.string.dialog_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String roomName = edt_room_name.getText().toString().trim();
                        if (roomName.length() == 0) {
                            Toast.makeText(MainActivity.this, R.string.dialog_tips_no_name, Toast.LENGTH_SHORT).show();
                        } else {
                            int max = Integer.valueOf(getString(R.string.random_max));
                            int min = Integer.valueOf(getString(R.string.random_min));
                            int s = (int) (Math.random() * max) + min;
                            String name = roomName + "#" + s;
                            forwardToRoom(name, true);
                            dialog.dismiss();
                            //edt_room_name.setText("");
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_negetive_btn, new DialogInterface.OnClickListener() {
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
        iconInfo1.setLock(false);
        list.add(iconInfo1);
        RoomInfo iconInfo2 = new RoomInfo();
        iconInfo2.setName("face");
        iconInfo2.setTime("11.11");
        iconInfo2.setPeople("12");
        iconInfo2.setLock(true);
        list.add(iconInfo2);
    }

    private boolean checkSecrete(String s) {
        if (true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean flag;
    private String sec;
    private void secreteDialog() {
        final EditText edt_sec = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.lable_title_room_name)
                .setView(edt_sec)
                .setPositiveButton(R.string.dialog_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag = true;
                        sec = edt_sec.getText().toString().trim();
                }
                })
                .setNegativeButton(R.string.dialog_negetive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag = false;
                        dialog.dismiss();
                    }
                });
        AlertDialog viewDialog = builder.create();
        viewDialog.setCanceledOnTouchOutside(false);
        viewDialog.show();
    }



    public void forwardToRoom(String cn, boolean io) {
        vSettings().mChannelName = cn;
        String encryption = ""; // 声望自带加密密码
        vSettings().mEncryptionKey = encryption;

        Intent i = new Intent(MainActivity.this, ChatActivity.class);
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
