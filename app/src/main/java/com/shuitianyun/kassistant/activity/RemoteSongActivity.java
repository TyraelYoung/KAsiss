package com.shuitianyun.kassistant.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;

import com.shuitianyun.kassistant.R;
import com.shuitianyun.kassistant.fragment.RemoteSongFragment;

public class RemoteSongActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_song);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.layout_main, new RemoteSongFragment());
        ft.commit();
    }
}
