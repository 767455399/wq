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

public class DiYiFragment extends Fragment {
    private static final String TAG = "DiYiFragment";
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
        Log.v(TAG,"onViewCreated111111");
        isViewVisible=true;
        loadData();
    }

    public void loadData(){
        Log.v(TAG,"loaddata11111111111");
        if(isViewVisible){
            lazyLoad();
            isViewVisible=false;
        }
    }

    public void lazyLoad(){
        Log.v(TAG,"lazyLoad11111111111");
        textView.setText("赖加载界面一");
        ToastUtil.showNormalToast("赖加载界面一");
    }

    @Override
    public void onResume() {
        super.onResume();
//        textView.setText("赖加载界面一");
        Log.v(TAG,"onResume11111111111");
    }

    public static DiYiFragment newInstance() {
        Bundle args = new Bundle();
        DiYiFragment fragment = new DiYiFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
