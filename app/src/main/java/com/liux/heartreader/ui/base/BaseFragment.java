package com.liux.heartreader.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.liux.heartreader.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目：MVP_Dagger_RxJava
 * 作者：nbcei
 * 时间：2017/6/2
 * 功能：
 */

public abstract class BaseFragment extends Fragment{

    private static final String TAG = "BaseFragment";

    private View mRootView;//缓存Fragment view

    private Unbinder mUnbinder;

    private boolean isViewCreated;
    private boolean isLoadDataCompleted;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView==null){
            mRootView= inflater.inflate(setLayout(), container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        mUnbinder=  ButterKnife.bind(this, mRootView);

        Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

            // 默认不显示原生标题
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            setHasOptionsMenu(true);

        }

        isViewCreated=true;
        return mRootView;
    }



    /***
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //View的初始化View
        initView();
        if (getUserVisibleHint()){
            loadData();
        }
    }

    /***
     * 当用户可见当时候加载数据
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser&&isViewCreated&&isLoadDataCompleted){
            loadData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    /***
     * 初始化view
     */
    public abstract void  initView();

    /***
     * 加载数据
     */
    public  void  loadData(){
        isLoadDataCompleted=true;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }

    }


    public abstract int setLayout();




}
