package com.shuitianyun.kassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.csmall.android.ApplicationHolder;
import com.shuitianyun.kassistant.R;

import java.util.Arrays;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class HiddenActivity extends BaseActivity {
    public static void strat(){
        Intent intent = new Intent(ApplicationHolder.getApplication(), HiddenActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        ApplicationHolder.getApplication().startActivity(intent);
    }


    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    private List<String> textList = Arrays.asList("测试崩溃");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden);
        ListView lv = (ListView) findViewById(R.id.lv_hidden);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return textList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = LayoutInflater.from(HiddenActivity.this).inflate(R.layout.item_hidden, parent, false);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.tv_item_hidden);
                tv.setText(textList.get(position));
                return convertView;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        throw new RuntimeException("测试崩溃");
                }
            }
        });
    }
}
