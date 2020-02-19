package com.shuitianyun.kassistant.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.shuitianyun.kassistant.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PermissionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PermissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PermissionFragment extends DialogFragment {
    public static final String BUNDLE_TITLE = "BUNDLE_TITLE";
    public static final String BUNDLE_CONTENT = "BUNDLE_CONTENT";

    private OnFragmentInteractionListener mListener;

    public PermissionFragment() {
        // Required empty public constructor
    }

    public static PermissionFragment newInstance(String title, String content) {
        PermissionFragment fragment = new PermissionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putString(BUNDLE_CONTENT, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static PermissionFragment showDialog(FragmentManager fragmentManager,String title, String content) {
        PermissionFragment newFragment = PermissionFragment.newInstance(title, content);
        newFragment.show(fragmentManager, "dialog");
        return newFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String title = bundle.getString(BUNDLE_TITLE);
        String content = bundle.getString(BUNDLE_CONTENT);
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("明白",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((OnFragmentInteractionListener)getActivity()).doPositiveClick();
                            }
                        }
                )
                .setNegativeButton("拒绝",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((OnFragmentInteractionListener)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_permission, container, false);
//    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void doPositiveClick();
        void doNegativeClick();
    }
}
