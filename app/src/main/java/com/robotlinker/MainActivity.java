package com.robotlinker;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robotlinker.account.AccountFragment;
import com.robotlinker.base.BaseActivity;
import com.robotlinker.base.BaseFragment;
import com.robotlinker.jog.JogFragment;
import com.robotlinker.robot.fragment.RobotFragment;
import com.robotlinker.setting.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tabs)
    LinearLayout mainTabs;
    BaseFragment currFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addTabItem(addTab(mainTabs, R.string.robot, 0, 0, 0, 0), RobotFragment.class);
        addTabItem(addTab(mainTabs, R.string.jog, 0, 0, 0, 0), JogFragment.class);
        addTabItem(addTab(mainTabs, R.string.setting, 0, 0, 0, 0), SettingFragment.class);
        addTabItem(addTab(mainTabs, R.string.account, 0, 0, 0, 0), AccountFragment.class);

    }

    private View addTab(LinearLayout parent, int text, int drawableLeft, int drawableTop, int drawableRight, int drawableBottom) {
        View v = LayoutInflater.from(this).inflate(R.layout.view_main_tab_item, parent, false);
        parent.addView(v);
        TextView tabItem = (TextView) v.findViewById(R.id.tab_item);
        tabItem.setText(text);
        tabItem.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        return v;
    }

    private void addTabItem(View tab, Class<? extends BaseFragment> f) {
        tab.setTag(f);
        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = mainTabs.getChildCount() - 1; i >= 0; i--) {
                    mainTabs.getChildAt(i).setSelected(false);
                }
                v.setSelected(true);
                switchContent((Class<? extends BaseFragment>) v.getTag());
            }
        });
        if (mainTabs.getChildCount() == 1) {
            tab.setSelected(true);
            switchContent(f);
        }
    }

    private void switchContent(Class<? extends BaseFragment> cls) {
        pushFragment(cls, false);
    }

    private void pushFragment(Class<? extends BaseFragment> cls, boolean isAddToBackStack) {
        if (cls != null) {
            try {
                FragmentManager e = getSupportFragmentManager();
                String fragmentTag = getFragmentTag(cls);
                BaseFragment baseFragment = (BaseFragment) e.findFragmentByTag(fragmentTag);
                if (null == baseFragment) {
                    baseFragment = cls.newInstance();
                }
                FragmentTransaction ft = e.beginTransaction();
                if (this.currFragment != null && currFragment != baseFragment) {
                    ft.hide(currFragment);
                }
                if (baseFragment.isAdded()) {
                    ft.show(baseFragment);
                } else {
                    ft.add(R.id.fl_main_container, baseFragment, fragmentTag);
                    if (isAddToBackStack)
                        ft.addToBackStack(fragmentTag);
                }
                this.currFragment = baseFragment;
                ft.commitAllowingStateLoss();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
    }


    protected String getFragmentTag(Class<? extends BaseFragment> cls) {
        return cls.toString();
    }
}
