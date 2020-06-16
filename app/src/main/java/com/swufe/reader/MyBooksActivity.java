package com.swufe.reader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyBooksActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private String TAG ="MyBooksActivity";
    private String user;
    private String booksStr;
    private String[] books={"您的书单正在加载中"};
    TextView name;
    ArrayAdapter adapter;
    ListView MyBooksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        Intent intent = getIntent();
        user = intent.getStringExtra("readerName");

        name = (TextView)findViewById(R.id.user);
        name.setText(user+"登陆中");

        MyBooksView = (ListView)findViewById(R.id.booksList);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,books);
        MyBooksView.setAdapter(adapter);

        ReaderManager manager = new ReaderManager(this);
        booksStr=manager.queryBooks(user);
        books=(booksStr.split("\\s+"));
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,books);
        MyBooksView.setAdapter(adapter);

        MyBooksView.setOnItemClickListener(this);
        MyBooksView.setOnItemLongClickListener(this);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.exit,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.exit){
            Intent exit = new Intent(this,MainActivity.class);
            startActivity(exit);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.i(TAG,"长按：position"+position);
        Log.i(TAG,"长按：View"+view);
        ListView book = (ListView)this.findViewById(R.id.booksList);
        final String Book_Name = (String) book.getItemAtPosition(position);
        Log.i(TAG,"长按：bookName:"+Book_Name);

        final ReaderManager manager = new ReaderManager(this);
        Log.i(TAG,"我的书籍："+booksStr);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("是否将"+Book_Name+"移除").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "OnItemLongClickListener:对话框处理事件");
                    String newBooks = booksStr.replaceAll(Book_Name," ");

                    manager.updateBook(user,newBooks);
                    Toast.makeText(MyBooksActivity.this, Book_Name+"已移出书架,可返回进行刷新", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("否", null);
        builder.create().show();

        return true;
    }
}
