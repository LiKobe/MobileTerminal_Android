package com.robotlinker.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Created by gaowubin on 2017/4/25.
 */

public class BaseFragment extends Fragment {

    public final int STYLE_FULL_SCREEN = 0X01;  //content 占全屏
    public final int STYLE_UNDER_ACTIONBAR = 0x02;  //content 标题栏下面
    public final int STYLE_TRANSPARENT = 0x03;   //标题栏状态栏透明  content
    public Context mContext;
    private BaseActivity baseActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            baseActivity = (BaseActivity) activity;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public boolean onBackPressed() {
        Log.i("test", "执行了没有");
        return true;
    }

    public void setTitle(String title) {
        if (null != baseActivity) {
            baseActivity.setTitle(title);
        }
    }

    public void toNewFragment(Fragment fragment) {
        if (null != baseActivity) {
            baseActivity.toNewFragment(fragment);
        }
    }

    public void setContentViewStyle(int style) {
        if (baseActivity != null) {
            baseActivity.setContentViewStyle(style);
        }
    }

    public void setActionBarAlpha(int alpha) {
        if (baseActivity != null) {
            baseActivity.setActionBarAlpha(alpha);
        }
    }

    public void setMenu(String menu) {
        if (baseActivity != null) {
            TextView textView = baseActivity.setMenu(menu);
            RxView.clicks(textView)
                    .throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(@NonNull Object o) throws Exception {
                            onMenuClick();
                        }
                    });
        }
    }

    public void setMenu(int imageResId) {
        if (baseActivity != null) {
            ImageView imageView = baseActivity.setMenu(imageResId);
            RxView.clicks(imageView)
                    .throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(@NonNull Object o) throws Exception {
                            onMenuClick();
                        }
                    });
        }
    }


    public void onMenuClick() {

    }


}
