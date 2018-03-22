package com.example.hasee.taiheapp.activity.laijiazai;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.base.BaseLazyFragment;
import com.example.hasee.taiheapp.tools.ToastUtil;

/**
 * Created by wangqing on 2018/3/21.
 */

public class TwoFragment  extends BaseLazyFragment {
    private static final String TAG="TwoFragment";
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ToastUtil.showNormalToast("222222");
        Log.v(TAG,"onViewCreated222");

    }


    @Override
    protected void initView(View view) {
     textView=(TextView)view.findViewById(R.id.textView);
    }

    @Override
    protected void loadData() {
        textView.setText("赖加载界面数据成功了");
    }


    public static TwoFragment newInstance() {
        Bundle args = new Bundle();
        TwoFragment fragment = new TwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG,"onResume222");
    }
}