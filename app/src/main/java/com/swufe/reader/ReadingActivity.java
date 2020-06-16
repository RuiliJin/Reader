package com.swufe.reader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class ReadingActivity extends AppCompatActivity implements Runnable {
    private String TAG="ReadingActivity";
    private String href;
    private TextView title_writer;
    private TextView tag;
    private TextView intro;
    private Handler handler;
    private  String[] str = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        title_writer = (TextView)findViewById(R.id.title_writer);
        tag = (TextView)findViewById(R.id.tag);
        intro = (TextView)findViewById(R.id.intro);
        Intent intent = getIntent();
        href = intent.getStringExtra("href");
        Log.i(TAG,"onCreat:getHref:"+href);

       Thread t= new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    Bundle bdl = (Bundle) msg.obj;

                    Log.i(TAG,"onCreate:"+bdl.getString("title_writer"));
                    Log.i(TAG,"onCreate:"+bdl.getString("tag"));
                    Log.i(TAG,"onCreate:"+bdl.getString("intro"));
                    title_writer.setText(bdl.getString("title_writer"));
                    tag.setText(bdl.getString("tag"));
                    intro.setText(bdl.getString("intro"));

                } super.handleMessage(msg);
            }
        };
    }

    @Override
    public void run() {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            Thread.sleep(300);
            doc = Jsoup.connect("https:"+href).get();
            Log.i(TAG, "run:" + doc.title());
            String twstr = ((doc.title()).split("_"))[0]+" "+((doc.title()).split("_"))[1];
            bundle.putString("title_writer",twstr);
            Log.i(TAG, "run:题目和作者" + twstr);

            Elements t = doc.getElementsByClass("tag");
            Log.i(TAG, "run:tag:" + t);
            String tagstr = "";
            for(Element element : t){
                tagstr=tagstr+element.text()+" ";
                Log.i(TAG, "run:tagstr:" + tagstr);

            }
            bundle.putString("tag",tagstr);
            Elements det = doc.getElementsByClass("intro");
            Element info = det.get(0);
            Log.i(TAG,"简介"+info);
            String infoStr = info.text();
            bundle.putString("intro",infoStr);

            Log.i(TAG,"简介infostr"+infoStr);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
         msg.obj = bundle;
         handler.sendMessage(msg);

    }
}
