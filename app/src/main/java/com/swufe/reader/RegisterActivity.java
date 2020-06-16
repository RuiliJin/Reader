package com.swufe.reader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private  String TAG="RegisterActivity";
    private  String Reader_Name;
    private  String Reader_Password;

    EditText inp1;
    EditText inp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inp1 =(EditText) findViewById(R.id.register_name);
        inp2 =(EditText)findViewById(R.id.register_password);
        Log.i(TAG,"onCreate");
    }
    public void save(View btn) {
        Log.i(TAG,"save");
        Reader_Name = inp1.getText().toString();
        Reader_Password = inp2.getText().toString();
        Log.i(TAG,"注册用户名："+Reader_Name+"密码："+Reader_Password);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认用户名："+Reader_Name+";密码:"+Reader_Password+"是否正确").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "OnItemLongClickListener:对话框处理事件");
                add(Reader_Name,Reader_Password);
            }
        })
                .setNegativeButton("否", null);
        builder.create().show();
        Log.i(TAG, "确认添加用户:" + Reader_Name + ";" + "password:" + Reader_Password);
    }
    public void add(String nm,String pw){
        ReaderItem item = new ReaderItem(nm,pw,"");
        ReaderManager manager = new ReaderManager(this);
        if(manager.query(nm)){
            Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
        }else{
        manager.register(item);
        Log.i(TAG,"添加用户："+nm+"成功");
        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        Intent back = new Intent(this,MainActivity.class);
        startActivity(back);
        }
    }
}
