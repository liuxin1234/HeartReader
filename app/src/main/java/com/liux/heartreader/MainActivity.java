package com.liux.heartreader;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liux.heartreader.adapter.MyFragmentPagerAdapter;
import com.liux.heartreader.constants.ConstantsImageUrl;
import com.liux.heartreader.ui.book.BookFragment;
import com.liux.heartreader.ui.gank.GankFragment;
import com.liux.heartreader.ui.menu.NavHomePageActivity;
import com.liux.heartreader.ui.one.OneFragment;
import com.liux.heartreader.utils.CommonUtils;
import com.liux.heartreader.utils.ImgLoadUtil;
import com.liux.heartreader.utils.statusbar.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {


    @BindView(R.id.iv_title_menu)
    ImageView ivTitleMenu;
    @BindView(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @BindView(R.id.iv_title_gank)
    ImageView ivTitleGank;
    @BindView(R.id.iv_title_one)
    ImageView ivTitleOne;
    @BindView(R.id.iv_title_dou)
    ImageView ivTitleDou;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawerLayout,
                CommonUtils.getColor(R.color.colorTheme));

        initContentFragment();
        initDrawerLayout();

    }

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private void initDrawerLayout() {
        ViewHolder viewHolder;
        View header = navView.inflateHeaderView(R.layout.nav_header_main);
        viewHolder = new ViewHolder(header);
        viewHolder.ivAvatar.setOnClickListener(this);
        viewHolder.llNavHomepage.setOnClickListener(this);
        viewHolder.llNavScanDownload.setOnClickListener(this);
        viewHolder.llNavDeedback.setOnClickListener(this);
        viewHolder.llNavAbout.setOnClickListener(this);
        viewHolder.llNavLogin.setOnClickListener(this);
        viewHolder.llNavExit.setOnClickListener(this);


//        dayNightSwitch.setChecked(SPUtils.getNightMode());
        ImgLoadUtil.displayCircle(viewHolder.ivAvatar, ConstantsImageUrl.IC_AVATAR);

    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new GankFragment());
        mFragmentList.add(new OneFragment());
        mFragmentList.add(new BookFragment());
        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        vpContent.setOffscreenPageLimit(2);
        vpContent.addOnPageChangeListener(this);
        ivTitleGank.setSelected(true);
        vpContent.setCurrentItem(0);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }

    /**
     * 主页上按钮监听事件
     *
     * @param view
     */
    @OnClick({R.id.ll_title_menu, R.id.iv_title_gank, R.id.iv_title_one, R.id.iv_title_dou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_menu:// 开启菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_gank:// 干货栏
                if (vpContent.getCurrentItem() != 0) {//不然cpu会有损耗
                    ivTitleGank.setSelected(true);
                    ivTitleOne.setSelected(false);
                    ivTitleDou.setSelected(false);
                    vpContent.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_one:// 电影栏
                if (vpContent.getCurrentItem() != 1) {
                    ivTitleGank.setSelected(false);
                    ivTitleOne.setSelected(true);
                    ivTitleDou.setSelected(false);
                    vpContent.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_dou:// 书籍栏
                if (vpContent.getCurrentItem() != 2) {
                    ivTitleGank.setSelected(false);
                    ivTitleOne.setSelected(false);
                    ivTitleDou.setSelected(true);
                    vpContent.setCurrentItem(2);
                }
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                ivTitleGank.setSelected(true);
                ivTitleOne.setSelected(false);
                ivTitleDou.setSelected(false);
                break;
            case 1:
                ivTitleOne.setSelected(true);
                ivTitleGank.setSelected(false);
                ivTitleDou.setSelected(false);
                break;
            case 2:
                ivTitleDou.setSelected(true);
                ivTitleOne.setSelected(false);
                ivTitleGank.setSelected(false);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 侧滑菜单的监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
                break;
            case R.id.ll_nav_homepage:
                NavHomePageActivity.startHome(MainActivity.this);
                break;
            case R.id.ll_nav_scan_download:
                NavHomePageActivity.startHome(MainActivity.this);
                break;
            case R.id.ll_nav_deedback:
                NavHomePageActivity.startHome(MainActivity.this);
                break;
            case R.id.ll_nav_about:
                NavHomePageActivity.startHome(MainActivity.this);
                break;
            case R.id.ll_nav_login:
                NavHomePageActivity.startHome(MainActivity.this);
                break;
            case R.id.ll_nav_exit:
                NavHomePageActivity.startHome(MainActivity.this);
                break;

        }
    }

        class ViewHolder {
            @BindView(R.id.iv_avatar)
            ImageView ivAvatar;
            @BindView(R.id.ll_nav_homepage)
            LinearLayout llNavHomepage;
            @BindView(R.id.ll_nav_scan_download)
            LinearLayout llNavScanDownload;
            @BindView(R.id.ll_nav_deedback)
            LinearLayout llNavDeedback;
            @BindView(R.id.ll_nav_about)
            LinearLayout llNavAbout;
            @BindView(R.id.ll_nav_login)
            LinearLayout llNavLogin;
            @BindView(R.id.ll_nav_exit)
            LinearLayout llNavExit;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

}
