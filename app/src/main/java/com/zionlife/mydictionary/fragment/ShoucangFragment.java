package com.zionlife.mydictionary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.util.Swappable;
import com.zionlife.mydictionary.R;
import com.zionlife.mydictionary.activity.ShoucangActivity;
import com.zionlife.mydictionary.bean.ReturnInfo;
import com.zionlife.mydictionary.bean.StoredInfo;
import com.zionlife.mydictionary.db.DbManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class ShoucangFragment extends Fragment {

    DbManager dm = null;
    List<StoredInfo> siList = new ArrayList<StoredInfo>();
    MyAdapter mAdapter;
    @Bind(R.id.lv_shoucang)
    DynamicListView lvShoucang;

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
        if (hidden != true) {
            dm = new DbManager(getContext());
            siList.clear();
            siList.addAll(dm.getAll());
            mAdapter.notifyDataSetChanged();
        }
    }

    private void init() {
        dm = new DbManager(getContext());
        siList = dm.getAll();
        mAdapter = new MyAdapter(getContext(), R.layout.shoucang_list_item, siList);
        final SwingRightInAnimationAdapter animationAdapter = new SwingRightInAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(lvShoucang);
        lvShoucang.setAdapter(animationAdapter);
        lvShoucang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShoucangFragment.this.getContext(), ShoucangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ri", siList.get(position).getRi());
                intent.putExtra("ReturnInfo", bundle);
                startActivity(intent);
            }
        });
        lvShoucang.enableSwipeToDismiss(new OnDismissCallback() {
            @Override
            public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {
                for(int position:reverseSortedPositions){
                    dm.delete(siList.get(position).getId());
                    mAdapter.remove(siList.get(position));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class MyAdapter extends ArrayAdapter<StoredInfo> {
        private int resourceId;

        public MyAdapter(Context context, int resource, List<StoredInfo> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }


        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ReturnInfo ri = getItem(position).getRi();
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
                viewHolder = new ViewHolder();
                viewHolder.tvWord = (TextView) view.findViewById(R.id.tv_word_shoucang);
                viewHolder.tvPinyin = (TextView) view.findViewById(R.id.tv_pinyin_shoucang);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            String pinyin = ri.getResult().getPinyin().replaceAll(",", ", ");
            viewHolder.tvWord.setText(ri.getResult().getName());
            viewHolder.tvPinyin.setText("[" +pinyin +"]");
            return view;
        }


        private class ViewHolder {
            TextView tvWord;
            TextView tvPinyin;
        }
    }
}
