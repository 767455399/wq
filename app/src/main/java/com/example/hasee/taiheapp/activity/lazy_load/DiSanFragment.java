package com.example.hasee.taiheapp.activity.lazy_load;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.tools.ToastUtil;

/**
 * Created by wangqing on 2018/3/22.
 */

public class DiSanFragment extends Fragment{
    private static final String TAG = "DiSanFragment";
    private boolean isViewVisible;
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view) {
        textView=(TextView)view.findViewById(R.id.textView);
        Log.v(TAG,"onViewCreated33333");
        isViewVisible=true;
        loadData();
    }

    public void loadData(){
        Log.v(TAG,"loaddata3333333");
        if(isViewVisible){
            lazyLoad();
            isViewVisible=false;
        }

    }

    public void lazyLoad(){
        Log.v(TAG,"lazyLoad3333333");
        textView.setText("赖加载界面三");
        ToastUtil.showNormalToast("赖加载界面三");
    }

    @Override
    public void onResume() {
        super.onResume();
//        textView.setText("赖加载界面三");
        Log.v(TAG,"onResume33333333");
    }

    public static DiSanFragment newInstance() {
        Bundle args = new Bundle();
        DiSanFragment fragment = new DiSanFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
