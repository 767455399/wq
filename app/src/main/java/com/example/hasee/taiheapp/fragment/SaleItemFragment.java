package com.example.hasee.taiheapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.tools.ToastUtil;

/**
 * Created by wangqing on 2018/1/18.
 */

public class SaleItemFragment extends Fragment {
    public static final String BUNDLE="BUNDLE";
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_item,container,false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static SaleItemFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(BUNDLE,content);
        SaleItemFragment fragment = new SaleItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=(Button) view.findViewById(R.id.button);
        button.setText(getArguments().getString(BUNDLE));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ToastUtil.showNormalToast("点击了");
            }
        });
    }
}
