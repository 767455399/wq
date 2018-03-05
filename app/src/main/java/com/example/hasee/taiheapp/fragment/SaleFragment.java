package com.example.hasee.taiheapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.taiheapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqing on 2018/1/16.
 */

public class SaleFragment extends Fragment {
    private String titles[]={"1","2","3","4","5","6","7","8","9","10","11","12"};
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment>fragments=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale,container,false);
    }

    public static SaleFragment newInstance() {
        Bundle args = new Bundle();
        SaleFragment fragment = new SaleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        fragments.clear();
        //设置可以滑动；
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for(int i=0;i<titles.length;i++){
            fragments.add(SaleItemFragment.newInstance(titles[i]));
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).select();
    }
}
