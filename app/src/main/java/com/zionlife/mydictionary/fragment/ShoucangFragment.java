package com.zionlife.mydictionary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zionlife.mydictionary.MyApplication;
import com.zionlife.mydictionary.R;
import com.zionlife.mydictionary.bean.ReturnInfo;
import com.zionlife.mydictionary.db.DbManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class ShoucangFragment extends Fragment {
    @Bind(R.id.lv_shoucang)
    ListView lvShoucang;

    DbManager dm = null;
    List<ReturnInfo> riList = new ArrayList<ReturnInfo>();
    MyAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoucang, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden != true){
            dm = new DbManager(getContext());
            riList.clear();
            riList.addAll(dm.getAll());
            mAdapter.notifyDataSetChanged();
        }
    }

    private void init() {
        dm = new DbManager(getContext());
        riList = dm.getAll();
        mAdapter = new MyAdapter(getContext(), R.layout.shoucang_list_item, riList);
        lvShoucang.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class MyAdapter extends ArrayAdapter<ReturnInfo> {
        private int resourceId;

        public MyAdapter(Context context, int resource, List<ReturnInfo> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ReturnInfo ri = getItem(position);
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
                viewHolder = new ViewHolder();
                viewHolder.tvWord = (TextView) view.findViewById(R.id.tv_word_shoucang);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tvWord.setText(ri.getResult().getName());
            return view;
        }

        private class ViewHolder {
            TextView tvWord;
        }
    }
}
