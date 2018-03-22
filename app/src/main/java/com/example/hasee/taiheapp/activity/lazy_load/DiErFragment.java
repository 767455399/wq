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

public class DiErFragment extends Fragment{

    private static final String TAG = "DiErFragment";
    private TextView textView;
    private boolean isViewVisible;
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
        Log.v(TAG,"onViewCreated222222");
        isViewVisible=true;
        loadData();
    }

    public void loadData(){
        Log.v(TAG,"loaddata222222");
        if(isViewVisible){
            lazyLoad();
            isViewVisible=false;
        }
    }

    public void lazyLoad(){
        Log.v(TAG,"lazyLoad222222");
        textView.setText("赖加载界面一");
        ToastUtil.showNormalToast("赖加载界面一");
    }

    @Override
    public void onResume() {
        super.onResume();
//        textView.setText("赖加载界面二");
        Log.v(TAG,"onResume222222");
    }

    public static DiErFragment newInstance() {
        Bundle args = new Bundle();
        DiErFragment fragment = new DiErFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
