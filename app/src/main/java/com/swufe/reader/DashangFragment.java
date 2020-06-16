package com.swufe.reader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DashangFragment extends Fragment implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    List<String> data=new ArrayList<String>();
    private String TAG ="DashangList";
    ArrayAdapter adapter;
    Handler handler;
    View view;
    ListView DashangView;
    private String[] href;

    public DashangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashang, container, false);
        DashangView =(ListView)view.findViewById(R.id.Dashanglist);

        Thread t= new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    data =(List<String>) msg.obj;
                    adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,data);
                    DashangView.setAdapter(adapter);
                }
            }
        };
        DashangView.setEmptyView(view.findViewById(R.id.nodata7));
        DashangView.setOnItemClickListener(this);
        DashangView.setOnItemLongClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent reading = new Intent(getActivity(),ReadingActivity.class);
        reading.putExtra("href",href[position]);
        startActivity(reading);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String user = ((ShowActivity)getActivity()).getUser();
        Log.i(TAG,"用户："+user+"在线");

        Log.i(TAG,"长按：position"+position);
        Log.i(TAG,"长按：View"+view);
        ListView book = (ListView)getActivity().findViewById(R.id.Dashanglist);
        final String Book_Name = (String) book.getItemAtPosition(position);
        Log.i(TAG,"长按：bookName:"+Book_Name);

        final ReaderManager manager = new ReaderManager(getActivity());
        final String books =  manager.queryBooks(user);;

        Log.i(TAG,"我的书籍："+books);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示").setMessage("是否将"+Book_Name+"加入书架").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "OnItemLongClickListener:对话框处理事件");

                if(books.indexOf(Book_Name) != -1){
                    Toast.makeText(getActivity(), Book_Name+"已在书架中", Toast.LENGTH_SHORT).show();
                }else{
                    String newBook = books+Book_Name+" ";
                    manager.updateBook(user,newBook);
                    Toast.makeText(getActivity(),Book_Name+"成功加入书架", Toast.LENGTH_SHORT).show();

                }

            }
        })
                .setNegativeButton("否", null);
        builder.create().show();


        return true;
    }

    @Override
    public void run() {
        List<String> retList = new ArrayList<String>();

        Document doc = null;
        try {
            Thread.sleep(300);
            doc = Jsoup.connect("https://www.hongxiu.com/rank").get();
            Log.i(TAG, "run:" + doc.title());
            Elements DS = doc.getElementsByClass("book-rank-list");
            Element books = DS.get(6);
            Log.i(TAG, "run:" + books);
            Elements book = books.getElementsByTag("a");
            Log.i(TAG, "run:" + book);
            for (int i = 0; i < book.size(); i += 1) {
                Element td1 = book.get(i);
                String str1 = td1.text();
                Log.i(TAG, "run:" + str1 );
                retList.add(str1);
            }

            Elements elements = books.select("a");
            href=new String[elements.size()];
            for(int i = 0; i < elements.size(); i += 1){
                Element element = elements.get(i);
                href[i] =element .attr("href");
                Log.i(TAG, "run:element" + elements.attr("href"));
                Log.i(TAG, "run:href" + href[i]);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        // msg.what=5;
        //msg.obj="hello from Run()";
        msg.obj = retList;
        handler.sendMessage(msg);
    }
}
