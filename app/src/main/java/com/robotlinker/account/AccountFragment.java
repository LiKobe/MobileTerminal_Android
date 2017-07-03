package com.robotlinker.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.jakewharton.rxbinding2.view.RxView;
import com.robotlinker.R;
import com.robotlinker.base.AppHelper;
import com.robotlinker.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by gaowubin on 2017/6/14.
 */

public class AccountFragment extends BaseFragment {

    @BindView(R.id.tvRobotName)
    TextView tvRobotName;

    @BindView(R.id.btnSignOut)
    Button btnSignOut;

    private String username;
    private CognitoUser user;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RxView.clicks(btnSignOut)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        signOut();
                    }
                });

        init();
    }

    private void init() {
        // Get the user name
        username = AppHelper.getCurrUser();
        user = AppHelper.getPool().getUser(username);

        tvRobotName.setText(username);
    }

    private void signOut() {
        user.signOut();
        exit();
    }

    private void exit() {
        getActivity().finish();
    }
}
