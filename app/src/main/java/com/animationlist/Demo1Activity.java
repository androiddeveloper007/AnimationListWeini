package com.animationlist;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Demo1Activity extends AppCompatActivity {
    private AnimationDrawable anim, anim1;
    private View view0, view1, view2, view3;
    private ObjectAnimator obj, obj1;
    private MHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);

        initView();

        handleAnimation();
    }

    private void handleAnimation() {
        startAnimType1();//当语音开始时，开始第一段动画
        mHandler = new MHandler(this);

        mHandler.postDelayed(new Runnable() {//当搜索开始时，开始第二段动画
            @Override
            public void run() {
                startAnimType2();
                mHandler.sendEmptyMessageDelayed(0, 4000);
            }
        }, 4000);
    }

    private void initView() {
        view0 = findViewById(R.id.view);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);

        anim = (AnimationDrawable) view2.getBackground();
        anim1 = (AnimationDrawable) view3.getBackground();
    }

    public void startAnimType1() {
        setViewObjectAnimator(obj, view0);
        setViewObjectAnimator(obj1, view1);
    }

    private void setViewObjectAnimator(ObjectAnimator o, View view) {
        o = ObjectAnimator.ofFloat(view, "scaleY",
                1f, 2f, 3f, 2f, 1f, 2f, 3f, 2f, 1f, 2f,
                3f, 2f, 1f, 2f, 1f, 2f, 3f, 4f);
        o.setDuration(4000);//动画时间
        o.setRepeatCount(-1);
        o.setRepeatMode(ValueAnimator.RESTART);
        o.start();
    }

    public void startAnimType2() {
        stopAnimType1();
        view2.setVisibility(View.VISIBLE);
        view3.setVisibility(View.VISIBLE);
        if (anim != null && !anim.isRunning()) {
            anim1.start();
            anim.start();
        }
    }

    public void stopAnimType1() {
        view0.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
        if (obj != null) {
            obj.cancel();
            obj1.cancel();
            obj = null;
            obj1 = null;
        }
    }

    public void stopAnimType2() {
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        if (anim != null) {
            anim.stop();
            anim1.stop();
            anim = null;
            anim1 = null;
        }
    }

    private static class MHandler extends UIHandler<Demo1Activity> {
        MHandler(Demo1Activity cls) {
            super(cls);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Demo1Activity act = getRef();
            if (act != null) {
                if (act.isFinishing()) return;
                if (msg.what == 0) {//搜索结束，释放资源
                    act.stopAnimType2();
                    act.finish();
                }
            }
        }
    }
}
