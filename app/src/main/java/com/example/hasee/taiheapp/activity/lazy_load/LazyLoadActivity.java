package com.example.hasee.taiheapp.activity.lazy_load;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.hasee.taiheapp.R;

import java.util.ArrayList;
import java.util.List;

public class LazyLoadActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment>fragments=new ArrayList<>();
    private String[] titles={"界面一","界面二","界面三"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_load);
        initView();
    }

    private void initView() {
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        fragments.add(DiYiFragment.newInstance());
        fragments.add(DiErFragment.newInstance());
        fragments.add(DiSanFragment.newInstance());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(0==position){
                    return DiYiFragment.newInstance();
                }else if(1==position){
                    return DiErFragment.newInstance();
                }else{
                    return DiSanFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    DiYiFragment.newInstance().loadData();
                }else if(position==1){
                    DiErFragment.newInstance().loadData();
                }else{
                    DiSanFragment.newInstance().loadData();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
