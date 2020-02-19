package com.shuitianyun.kassistant.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.shuitianyun.kassistant.R;
import com.shuitianyun.kassistant.fragment.LocalSongFragment;
import com.shuitianyun.kassistant.fragment.PermissionFragment;

public class LocalSongActivity extends BaseActivity implements PermissionFragment.OnFragmentInteractionListener {
    LocalSongFragment localSongFragment;
    PermissionFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_song);

        localSongFragment = new LocalSongFragment();
        onFragmentInteractionListener = localSongFragment;
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.layout_main, localSongFragment);
        ft.commit();
    }

    @Override
    public void doPositiveClick() {
        onFragmentInteractionListener.doPositiveClick();
    }

    @Override
    public void doNegativeClick() {
        onFragmentInteractionListener.doNegativeClick();
    }
}
