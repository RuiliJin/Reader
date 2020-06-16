package com.swufe.reader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

public class ShowActivity extends AppCompatActivity {
    private String TAG = "ShowActivity";
    private String User;

    public void setUser(String user){
        this.User = user;
    }
    public String getUser(){
        return User;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();
        setUser(intent.getStringExtra("readerName"));
        Log.i(TAG,"OnCreate:user:"+getUser()+"在线");

        ViewPager viewPager =(ViewPager) findViewById(R.id.viewpager);
        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mine,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.my_books){
            Intent mybooks = new Intent(this,MyBooksActivity.class);
            mybooks.putExtra("readerName",getUser());
            startActivity(mybooks);
        }
        return super.onOptionsItemSelected(item);
    }
}
