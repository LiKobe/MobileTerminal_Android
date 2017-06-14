package com.robotlinker.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.robotlinker.MainActivity;
import com.robotlinker.R;
import com.robotlinker.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by gaowubin on 2017/6/14.
 */

public class LoginFragment extends BaseFragment {

    @BindView(R.id.edtTxtUserName)
    EditText edtTxtUserName;
    @BindView(R.id.edtTxtPwd)
    EditText edtTxtPwd;
    @BindView(R.id.btnSign)
    Button btnSign;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentViewStyle(STYLE_FULL_SCREEN);
        RxView.clicks(btnSign)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // TODO: 2017/6/14  sign
                    }
                });

        RxView.clicks(btnLogin)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        startActivity(new Intent(mContext, MainActivity.class));
                    }
                });

    }


}
