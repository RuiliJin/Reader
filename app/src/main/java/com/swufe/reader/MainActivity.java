package com.swufe.reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private String TAG="Login";
    private String Reader_Name;
    private String Reader_Password;
    EditText Name;
    EditText Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText) findViewById(R.id.Reader_Name);
        Password = (EditText) findViewById(R.id.Reader_Password);
    }
        public void login(View btn){
            Log.i(TAG, "登录检测");
            Reader_Name = Name.getText().toString();
            Reader_Password= Password.getText().toString();
            Log.i(TAG,"login:读者："+Reader_Name+"请求登录");

            ReaderManager manager = new ReaderManager(this);
            if(manager.login(Reader_Name,Reader_Password)){
            Intent show = new Intent(this,ShowActivity.class);
            show.putExtra("readerName",Reader_Name);
            startActivity(show);
            Log.i(TAG,"登陆成功，跳转至主页面");}
            else{
                Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
        public void register(View btn){
            Log.i(TAG, "register");
            Intent register = new Intent(this,RegisterActivity.class);
            startActivity(register);
            Log.i(TAG,"跳转至注册页面");
        }
}
