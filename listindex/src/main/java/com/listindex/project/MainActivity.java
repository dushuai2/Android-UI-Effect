package com.listindex.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.listindex.project.adapter.ItemAdapter;
import com.listindex.project.bean.User;
import com.listindex.project.view.IndexView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import sdk.pendo.io.*;

public class MainActivity extends Activity {
    private ListView listView;
    private IndexView indexView;
    /**
     * 列表数据
     */
    ArrayList<User> itemArray = getListData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Pendo.PendoInitParams pendoParams = new Pendo.PendoInitParams();
        pendoParams.setVisitorId("John Smith");
        pendoParams.setAccountId("Acme Inc");

//send Visitor Level Data
        Map<String, Object> visitorData = new HashMap<>();
        visitorData.put("age", 27);
        visitorData.put("country", "USA");
        pendoParams.setVisitorData(visitorData);

//send Account Level Data
        Map<String, Object> accountData = new HashMap<>();
        accountData.put("Tier", 1);
        accountData.put("Size", "Enterprise");
        pendoParams.setAccountData(accountData);

        String pendoAppKey = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiIsInR5cCI6IkpXVCJ9.eyJkYXRhY2VudGVyIjoidXMiLCJrZXkiOiJiNGNhNDRhODRjNWFiODI3MmU1MjUzYWE3ZDI3MTI5MzkyNmJiYmIxY2Q4YWE4NDZkZmFhYzRjNjFkZGMzODgwN2NhNzg5NjE5NDBhZmFjZmE5NzY3OTRhYzAxZmQ1Yzc1NmY1YzllMTVmNmJkZTkwNTM4ODgxZDM4ZGExNTkwNDJkMTMyYmYwYzRjZjJiM2YxMzlkZDc0Mjg3NTQ0MzAzLmI1NDQzOGNlMWRmZGQ5Mjg5YjczNWJmY2ZiMzNmNDA5LmExOTk4Y2MwNjA0MzQyYjVlYTc4MTM4NGQ5Njk2YmQzNzU0ZWU3ODM1ZWJjNjA2OWQ5N2UxNTI4MTIwMDFlNTEifQ.l62qWJYBopjEakXuUqGx2_30uieav93YUdlMl-5GVDSMiWHZE35TYPr_xHlM3HWPimG9hLFEkK1PLJmfsYbyFj1S5T6ux59LrK678p3s1zflflf1RtfC8CGggwqRpEhCFU5FZ6khrBjDhXsZORajsyt9M1soVdKlEDLRJv7p120";
        Pendo.initSDK(
                this,
                pendoAppKey,
                pendoParams);

        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        indexView = (IndexView) findViewById(R.id.index);
        TextView selectIndexView = (TextView) findViewById(R.id.select_index);
        indexView.init(listView, selectIndexView);

        ItemAdapter itemAdapter = new ItemAdapter(this, itemArray, indexView);
        listView.setAdapter(itemAdapter);


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int position;

            /**
             * 滚动状态改变时调用
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 不滚动时保存当前滚动到的位置
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                }

            }

            /**
             * 滚动时调用
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (null != indexView) {
                        position = listView.getFirstVisiblePosition();
                        int i=0;
                        for(String type:ItemAdapter.IndexArrar){
                            if(type.equals(((User)listView.getAdapter().getItem(position)).getType())){
                                i++;
                                break;
                            }
                            i++;
                        }
                        indexView.changeTextViewState(i,false);
                        System.out.println("位置:" + i);
                    }
                }
        });
    }

    private ArrayList<User> getListData() {
        ArrayList<User> arrayList=new ArrayList<User>();
        for(String type:ItemAdapter.IndexArrar)
        for(int i=0;i<10;i++){
            User user=new User();
            user.setType(type);
            user.setName(type + "_item" + i);
            arrayList.add(user);
        }
        return arrayList;
    }


}
