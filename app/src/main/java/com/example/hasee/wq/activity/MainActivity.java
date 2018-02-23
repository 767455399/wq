package com.example.hasee.wq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.wq.R;
import com.example.hasee.wq.activity.fragment.FragmentActivity;
import com.example.hasee.wq.base.BaseActivity;
import com.example.hasee.wq.fragment.ContentFragment;
import com.example.hasee.wq.fragment.MineFragment;
import com.example.hasee.wq.fragment.OrderFragment;
import com.example.hasee.wq.fragment.ShopFragment;

public class MainActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MineFragment mineFragment;
    private OrderFragment orderFragment;
    private ShopFragment shopFragment;
    private ContentFragment contentFragment;
    private String tabTitles[] = new String[]{"主页", "商城", "订单", "我的", "主页1", "商城1", "订单1", "我的1", "主页2", "商城2", "订单2", "我的2"};
    private Handler handler = new Handler();
    private TextView textView;
    private TextView toOtherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void delay() {
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("小泰科技");
//                MenuActivity.actionStart(MainActivity.this,"王清","123");
                startActivity(new Intent(MainActivity.this, FragmentActivity.class));
//                FragmentHostActivity.openFragment(MainActivity.this,MineFragment.newInstance());
            }
        }, 1000);
    }

    public void toOtherActivity() {
        startActivity(new Intent(MainActivity.this, WebViewActivity.class));
    }

    private void newHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("王清");
            }
        }, 2000);
    }

    private void newThreads() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("wangqing");
                        }
                    }, 2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void initView() {
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        toOtherTextView = (TextView) findViewById(R.id.toOtherTextView);
        newThreads();
        newHandler();
        delay();
        toast("wqwqwq");
        toOtherTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOtherActivity();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        tabLayout.addTab(tabLayout.newTab().setText("主页"));
//        tabLayout.addTab(tabLayout.newTab().setText("商城"));
//        tabLayout.addTab(tabLayout.newTab().setText("订单"));
//        tabLayout.addTab(tabLayout.newTab().setText("我的"));
//        tabLayout.addTab(tabLayout.newTab().setText("主页1"));
//        tabLayout.addTab(tabLayout.newTab().setText("商城1"));
//        tabLayout.addTab(tabLayout.newTab().setText("订单1"));
//        tabLayout.addTab(tabLayout.newTab().setText("我的1"));
//        tabLayout.addTab(tabLayout.newTab().setText("主页2"));
//        tabLayout.addTab(tabLayout.newTab().setText("商城2"));
//        tabLayout.addTab(tabLayout.newTab().setText("订单2"));
//        tabLayout.addTab(tabLayout.newTab().setText("我的2"));
        tabLayout.setupWithViewPager(viewPager);
        if (tabTitles.length > 5) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (0 == position) {
                    return contentFragment = ContentFragment.newInstance();
                } else if (1 == position) {
                    return shopFragment = ShopFragment.newInstance();
                } else if (2 == position) {
                    return orderFragment = OrderFragment.newInstance();
                } else if (3 == position) {
                    return mineFragment = MineFragment.newInstance();
                } else {
                    return shopFragment = ShopFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return tabTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }

            //            @Override
//            public CharSequence getPageTitle(int position) {
//                switch (position) {
//                    case 0:
//                        return "主页";
//                    case 1:
//                        return "商城";
//                    case 2:
//                        return "订单";
//                    case 3:
//                        return "我的";
//                    default:
//                        return "主页";
//                }
//            }
        });


        tabLayout.getTabAt(0).select();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });


    }

    @Override
    public void initData() {

    }

    private void initTab(int postion) {
        if (0 == postion) {

        }
    }
}
