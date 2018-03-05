package com.example.hasee.taiheapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.fragment.CommunityFragment;
import com.example.hasee.taiheapp.fragment.FightGroupsFragment;
import com.example.hasee.taiheapp.fragment.SaleFragment;

public class TablayoutActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SaleFragment saleFragment;
    private CommunityFragment communityFragment;
    private FightGroupsFragment fightGroupsFragment;
    private String titles[]={"拼团","特卖","社区"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        initView();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (0 == position) {
                    return fightGroupsFragment = FightGroupsFragment.newInstance();
                } else if (1 == position) {
                    return saleFragment = SaleFragment.newInstance();
                } else {
                    return communityFragment = CommunityFragment.newInstance();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }
}
