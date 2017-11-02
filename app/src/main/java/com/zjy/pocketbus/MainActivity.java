package com.zjy.pocketbus;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zjy.pocketbus.entity.TabEntity;
import com.zjy.pocketbus.fragment.MineFragment;
import com.zjy.pocketbus.fragment.NearbyFragment;
import com.zjy.pocketbus.fragment.RoadFragment;
import com.zjy.pocketbus.utils.WindowUtils;

import java.util.ArrayList;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.vp_main)
    ViewPager mViewPager;
//    @BindView(R.id.tl_main)
    CommonTabLayout mTabLayout;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "路线", "我的"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_main_nearby_unselect, R.drawable.ic_main_road_unselect,
            R.drawable.ic_main_mine_unselect};
    private int[] mIconSelectIds = {
            R.drawable.ic_main_nearby, R.drawable.ic_main_road,
            R.drawable.ic_main_mine};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowUtils.setStatusBarColor(this, R.color.colorPrimary, false);
//        ButterKnife.bind(this);

        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mTabLayout = (CommonTabLayout) findViewById(R.id.tl_main);

        mFragments.add(new NearbyFragment());
        mFragments.add(new RoadFragment());
        mFragments.add(new MineFragment());
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities);

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
//                    mTabLayout.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
