package com.example.hasee.taiheapp.activity.fragment;

import android.os.Bundle;

import com.example.hasee.taiheapp.base.BaseActivity;
import com.example.hasee.taiheapp.fragment.TabLayoutFragment;

public class FragmentsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);

    }

    @Override
    protected void initView() {
        replaceFragment(TabLayoutFragment.newInstance());

    }

    @Override
    public void initData() {

    }
}
