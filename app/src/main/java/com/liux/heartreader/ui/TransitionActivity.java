package com.liux.heartreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liux.heartreader.MainActivity;
import com.liux.heartreader.R;
import com.liux.heartreader.constants.ConstantsImageUrl;
import com.liux.heartreader.utils.CommonUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.heartreader.ui
 * 项目日期：2017/9/30
 * 作者：liux
 * 功能：
 */

public class TransitionActivity extends AppCompatActivity {

    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_jump)
    TextView tvJump;
    @BindView(R.id.iv_defult_pic)
    ImageView ivDefultPic;



    private boolean animationEnd;
    private boolean isIn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ButterKnife.bind(this);

        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        //先显示默认图
        ivDefultPic.setImageDrawable(CommonUtils.getDrawable(R.drawable.img_transition_default));
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(ivPic);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivDefultPic.setVisibility(View.GONE);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 3500);


    }

    private void toMainActivity() {
        if (isIn){
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in,R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }

    @OnClick(R.id.tv_jump)
    public void onViewClicked() {
        toMainActivity();
    }
}
