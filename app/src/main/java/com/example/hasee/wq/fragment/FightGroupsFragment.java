package com.example.hasee.wq.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hasee.wq.R;

/**
 * Created by wangqing on 2018/1/16.
 */

public class FightGroupsFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MineFragment mineFragment;
    private OrderFragment orderFragment;
    private ShopFragment shopFragment;
    private ContentFragment contentFragment;
    private String titles[]={"商品","订单","内容","我的"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fight_groups,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
                if(0==position){
                    return shopFragment=ShopFragment.newInstance();
                }else if(1==position){
                    return orderFragment=OrderFragment.newInstance();
                }else if(2==position){
                    return contentFragment=ContentFragment.newInstance();
                }else{
                    return mineFragment=MineFragment.newInstance();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),Math.abs(tab.getPosition()-viewPager.getCurrentItem())==1);
            }
        });
        tabLayout.getTabAt(0).select();
    }

    public static FightGroupsFragment newInstance() {
        Bundle args = new Bundle();
        FightGroupsFragment fragment = new FightGroupsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
