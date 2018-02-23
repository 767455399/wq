package com.example.hasee.wq.activity.fragment;

import android.os.Bundle;

import com.example.hasee.wq.base.BaseActivity;
import com.example.hasee.wq.fragment.MineFragment;

public class FragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);

    }

    @Override
    protected void initView() {
        replaceFragment(MineFragment.newInstance());

    }

    @Override
    public void initData() {

    }
}
