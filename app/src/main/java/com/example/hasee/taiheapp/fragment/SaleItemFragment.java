package com.example.hasee.taiheapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;

/**
 * Created by wangqing on 2018/1/18.
 */

public class SaleItemFragment extends Fragment {
    public static final String BUNDLE="BUNDLE";
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_item,container,false);
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
        textView=(TextView)view.findViewById(R.id.textView);
        textView.setText(getArguments().getString(BUNDLE));
    }
}
