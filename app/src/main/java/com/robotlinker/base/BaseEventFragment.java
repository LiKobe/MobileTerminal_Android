package com.robotlinker.base;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Michael on 15/10/27.
 */
public class BaseEventFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
