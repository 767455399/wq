package com.example.hasee.taiheapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

/**
 * Created by wangqing on 2018/3/22.
 */

public abstract class BaseLazyFragment extends BaseFragment {
    private static final String TAG="BaseFragment";
    //Fragment的view加载完毕的标记；
    private boolean isViewCreat;
    //fragment对用户可见的标记；
    private boolean isUIVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(TAG,"base------onViewCreated");
        initView(view);
        isViewCreat=true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(TAG,"base------setUserVisibleHint");
        //isVisibleToUser这个boolean值表示：该fragment的UI用户是否可见；
        if(isVisibleToUser){
            isUIVisible=true;
            lazyLoad();
        }else{
            isUIVisible=false;
        }
    }

    protected abstract void initView(View view);




    public void lazyLoad(){
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if(isUIVisible&&isViewCreat){
            Log.v(TAG,"base------lazyLoad");
            loadData();
            //数据加载完毕，恢复标记，防止重复加载
            isUIVisible=false;
            isViewCreat=false;
        }
    }

    /**
     * 执行加载数据的地方
     */
    protected abstract void loadData();
}
