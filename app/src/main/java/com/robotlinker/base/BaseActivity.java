package com.robotlinker.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.robotlinker.R;

/**
 * Created by gaowubin on 2017/4/25.
 */

public class BaseActivity extends FragmentActivity {


    public final int STYLE_FULL_SCREEN = 0X01;  //content 占全屏
    public final int STYLE_UNDER_ACTIONBAR = 0x02;  //content 标题栏下面
    public final int STYLE_TRANSPARENT = 0x03;   //标题栏状态栏透明  content
    ImageView ivReturn;
    TextView tvTitle;
    private int titleHeight;
    private FrameLayout parent;
    private SystemBarTintManager tintManager;
    private RelativeLayout lvTitle;
    private int statusBarHeight;
    private LinearLayout lvMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        super.setContentView(R.layout.activity_base);
        statusBarHeight = getStatusBarHeight();
        lvTitle = (RelativeLayout) findViewById(R.id.lv_title);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivReturn = (ImageView) findViewById(R.id.ivReturn);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lvMenu = (LinearLayout) findViewById(R.id.lvMenu);
        parent = (FrameLayout) findViewById(R.id.content);
        titleHeight = getResources().getDimensionPixelOffset(R.dimen.statusMargin);
        setContentViewStyle(STYLE_UNDER_ACTIONBAR);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void setContentView(int layoutId) {
        parent.addView(LayoutInflater.from(this).inflate(layoutId, parent, false));
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public TextView setMenu(String menu) {
        lvMenu.removeAllViews();
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL));
        textView.setText(menu);
        textView.setPadding(30, 0, 30, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(18);
        textView.setTextColor(getResources().getColor(R.color.white));
        lvMenu.addView(textView);
        return textView;
    }

    public ImageView setMenu(int imageResId) {
        lvMenu.removeAllViews();
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL));
        imageView.setImageResource(imageResId);
        imageView.setPadding(30, 0, 30, 0);
        lvMenu.addView(imageView);
        return imageView;
    }


    public void onPopBackStack() {
//        BaseFragment fragment=(BaseFragment)getSupportFragmentManager().findFragmentById(R.id.content);
//        if(fragment==null||!fragment.onBackPressed()){
//
//        }
        Log.i("test", "onPopBackStack");
    }

    public void onHomeAsUpClick() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            onPopBackStack();
        } else {
            supportFinishAfterTransition();
        }
    }

    @Override
    public void onBackPressed() {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (null != fragment && !fragment.onBackPressed()) {
            return;
        }
        onHomeAsUpClick();
    }

    public void toNewFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(fragment.getClass().getName()).commit();
    }

    public void toNewFragmentHideAndShow(Fragment from, Fragment to) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(to.getClass().getName()).hide(from).add(R.id.content, to)
                .commit();
    }

    public void setContentViewStyle(int style) {
        switch (style) {
            case STYLE_FULL_SCREEN:
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                setMainPadding(0);
                setActionBarAlpha(0);
                setMargins(lvTitle, 0, statusBarHeight, 0, 0);
                break;
            case STYLE_UNDER_ACTIONBAR:
                setMainPadding(titleHeight);
                break;
        }
    }

    private void setMainPadding(int padding) {
        parent.setPadding(0, padding, 0, 0);
    }

    public void setActionBarAlpha(int alpha) {
        int top = alpha;
        if (top > 250) {
            top = 230;
        }
        if (top < 0) {
            top = 0;

        }
        tintManager.setStatusBarAlpha(top);
        lvTitle.setAlpha(alpha);
        tvTitle.setTextColor(alpha == 0 ? getResources().getColor(R.color.colorPrimaryDark) : getResources().getColor(R.color.white));
    }

    private void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    private int getStatusBarHeight() {
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }
}
