package com.robotlinker.login.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.robotlinker.MainActivity;
import com.robotlinker.R;
import com.robotlinker.base.AppHelper;
import com.robotlinker.base.BaseFragment;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by gaowubin on 2017/6/14.
 */

public class LoginFragment extends BaseFragment {

    private final String TAG="LoginActivity";

    @BindView(R.id.edtTxtUserName)
    EditText edtTxtUserName;
    @BindView(R.id.edtTxtPwd)
    EditText edtTxtPwd;
    @BindView(R.id.btnSign)
    Button btnSign;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    // User Details
    private String username;
    private String password;

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

        AppHelper.init(getActivity().getApplicationContext());

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
                        signInUser();
                    }
                });

    }

    private void launchUser() {
        startActivity(new Intent(mContext, MainActivity.class));
    }

    private void signInUser() {
        username = edtTxtUserName.getText().toString();
        if(username == null || username.length() < 1) {
            Toast.makeText(getActivity().getApplicationContext(),"username cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        AppHelper.setUser(username);

        password = edtTxtPwd.getText().toString();
        if(password == null || password.length() < 1) {
            Toast.makeText(getActivity().getApplicationContext(),"password cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        showWaitDialog("Signing in...");
        AppHelper.getPool().getUser(username).getSessionInBackground(authenticationHandler);
    }

    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice device) {
            Logger.i("Auth Success");
            closeWaitDialog();
            AppHelper.setCurrSession(cognitoUserSession);
            launchUser();
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String username) {
            Locale.setDefault(Locale.US);
            getUserAuthentication(authenticationContinuation, username);
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {

        }

        @Override
        public void onFailure(Exception e) {
            closeWaitDialog();
            Toast.makeText(getActivity().getApplicationContext(),"Sign-in failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            /**
             * For Custom authentication challenge, implement your logic to present challenge to the
             * user and pass the user's responses to the continuation.
             */
        }
    };

    private void getUserAuthentication(AuthenticationContinuation continuation, String username) {
        if(username != null) {
            this.username = username;
            AppHelper.setUser(username);
        }
        if(this.password == null) {
            password = edtTxtPwd.getText().toString();
            if(password == null) {
                Toast.makeText(getActivity().getApplicationContext(),"password cannot be empty",Toast.LENGTH_SHORT).show();
                return;
            }

            if(password.length() < 1) {
                Toast.makeText(getActivity().getApplicationContext(),"password cannot be empty",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        AuthenticationDetails authenticationDetails = new AuthenticationDetails(this.username, password, null);
        continuation.setAuthenticationDetails(authenticationDetails);
        continuation.continueTask();
    }



    private ProgressDialog waitDialog;

    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(getActivity());
        waitDialog.setTitle(message);
        waitDialog.show();
    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }

}
