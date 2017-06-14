package com.robotlinker.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.robotlinker.R;
import com.robotlinker.base.BaseActivity;
import com.robotlinker.login.fragment.LoginFragment;

/**
 * Created by gaowubin on 2017/6/14.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, new LoginFragment()).commit();
    }
}
