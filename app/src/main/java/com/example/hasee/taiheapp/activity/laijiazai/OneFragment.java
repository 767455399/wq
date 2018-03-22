package com.example.hasee.taiheapp.activity.laijiazai;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class OneFragment extends BaseLazyFragment {
    private static final String TAG = "OneFragment";
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ToastUtil.showNormalToast("111");
    }

    @Override
    protected void initView(View view) {
        textView = (TextView) view.findViewById(R.id.textView);
    }

    @Override
    protected void loadData() {
        textView.setText("界面1赖加载成功");

    }

    public static OneFragment newInstance() {
        Bundle args = new Bundle();
        OneFragment fragment = new OneFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void lazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
