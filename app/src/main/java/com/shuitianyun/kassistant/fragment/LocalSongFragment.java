package com.shuitianyun.kassistant.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csmall.log.LogHelper;
import com.shuitianyun.kassistant.R;
import com.shuitianyun.kassistant.activity.LocalSongDetailActivity;
import com.shuitianyun.kassistant.biz.LocalSongBiz;
import com.shuitianyun.kassistant.json.LocalSong;
import com.shuitianyun.kassistant.presenter.LocalSongPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class LocalSongFragment extends Fragment implements PermissionFragment.OnFragmentInteractionListener{

    private static final java.lang.String TAG = "LocalSongFragment";

    private int mColumnCount = 1;

    private LocalSongPresenter localSongPresenter;

    private RecyclerView recyclerView;

    private OnListFragmentInteractionListener mListener = new OnListFragmentInteractionListener() {
        @Override
        public void onListFragmentInteraction(LocalSong item) {
            LogHelper.d(TAG, "onListFragmentInteraction:" + item);
//            localSongPresenter.uploadFile(item.fullPath);
            LocalSongBiz.getInstance().put(item);
            LocalSongDetailActivity.to(getActivity(), item.getId());
        }
    };
    private LocalSongRecyclerViewAdapter mAdapter = new LocalSongRecyclerViewAdapter(new ArrayList<LocalSong>(), mListener);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localsong_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        Context context = view.getContext();

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(mAdapter);

        localSongPresenter = new LocalSongPresenter(this);
        localSongPresenter.loadMain();
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        LogHelper.d(TAG, "onRequestPermissionsResult");
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    localSongPresenter.onSystemPermit();

                } else {
                    localSongPresenter.onSystemRefuse();
//                    localSongPresenter.
//                    ToastUtil.show("system permission denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

//            }

            // other 'case' lines to check for other
            // permissions this app might request
//        }
    }

    public void updateLocalSong(List<LocalSong> list){
        mAdapter.updateData(list);
    }

    @Override
    public void doPositiveClick() {
        localSongPresenter.requestPermission();
    }

    @Override
    public void doNegativeClick() {
        localSongPresenter.onPermitRefuse();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(LocalSong item);
    }
}
