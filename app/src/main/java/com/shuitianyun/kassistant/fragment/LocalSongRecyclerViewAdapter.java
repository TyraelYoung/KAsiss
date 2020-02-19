package com.shuitianyun.kassistant.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuitianyun.kassistant.R;
import com.shuitianyun.kassistant.fragment.LocalSongFragment.OnListFragmentInteractionListener;

import com.shuitianyun.kassistant.json.LocalSong;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a { DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class LocalSongRecyclerViewAdapter extends RecyclerView.Adapter<LocalSongRecyclerViewAdapter.ViewHolder> {

    private List<LocalSong> mValues;
    private final OnListFragmentInteractionListener mListener;

    public LocalSongRecyclerViewAdapter(List<LocalSong> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_localsong_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvFileName.setText(holder.mItem.fileName);
        holder.tvCreateAt.setText(holder.mItem.displayDisplayCreateAt());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateData(List<LocalSong> list){
        this.mValues = list;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvFileName;
        public final TextView tvCreateAt;
        public LocalSong mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvFileName = (TextView) view.findViewById(R.id.item_localsong_filename);
            tvCreateAt = (TextView) view.findViewById(R.id.item_localsong_createat);
        }

    }
}
