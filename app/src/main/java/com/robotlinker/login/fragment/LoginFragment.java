package com.robotlinker.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    //
    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice device) {
            Logger.i("Auth Success");
            AppHelper.setCurrSession(cognitoUserSession);
//            closeWaitDialog();
            launchUser();
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String username) {
//            closeWaitDialog();
            Locale.setDefault(Locale.US);
            getUserAuthentication(authenticationContinuation, username);
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {

        }

        @Override
        public void onFailure(Exception e) {
//            closeWaitDialog();
//            TextView label = (TextView) findViewById(R.id.textViewUserIdMessage);
//            label.setText("Sign-in failed");
//            inPassword.setBackground(getDrawable(R.drawable.text_border_error));
//
//            label = (TextView) findViewById(R.id.textViewUserIdMessage);
//            label.setText("Sign-in failed");
//            inUsername.setBackground(getDrawable(R.drawable.text_border_error));
//
//            showDialogMessage("Sign-in failed", AppHelper.formatException(e));
        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            /**
             * For Custom authentication challenge, implement your logic to present challenge to the
             * user and pass the user's responses to the continuation.
             */
//            if ("NEW_PASSWORD_REQUIRED".equals(continuation.getChallengeName())) {
//                // This is the first sign-in attempt for an admin created user
//                newPasswordContinuation = (NewPasswordContinuation) continuation;
//                AppHelper.setUserAttributeForDisplayFirstLogIn(newPasswordContinuation.getCurrentUserAttributes(),
//                        newPasswordContinuation.getRequiredAttributes());
//                closeWaitDialog();
//                firstTimeSignIn();
//            }
        }
    };

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
//                        connectRos("192.168.1.102","9090");
                    }
                });

    }

    private void launchUser() {
        startActivity(new Intent(mContext, MainActivity.class));
//        Intent userActivity = new Intent(this, UserActivity.class);
//        userActivity.putExtra("name", username);
//        startActivityForResult(userActivity, 4);
    }

    private void signInUser() {
        username = edtTxtUserName.getText().toString();
        if(username == null || username.length() < 1) {
//            TextView label = (TextView) findViewById(R.id.textViewUserIdMessage);
//            label.setText(inUsername.getHint()+" cannot be empty");
//            inUsername.setBackground(getDrawable(R.drawable.text_border_error));
            return;
        }

        AppHelper.setUser(username);

        password = edtTxtPwd.getText().toString();
        if(password == null || password.length() < 1) {
//            TextView label = (TextView) findViewById(R.id.textViewUserPasswordMessage);
//            label.setText(inPassword.getHint()+" cannot be empty");
//            inPassword.setBackground(getDrawable(R.drawable.text_border_error));
            return;
        }

//        showWaitDialog("Signing in...");
        AppHelper.getPool().getUser(username).getSessionInBackground(authenticationHandler);
    }

    private void getUserAuthentication(AuthenticationContinuation continuation, String username) {
        if(username != null) {
            this.username = username;
            AppHelper.setUser(username);
        }
        if(this.password == null) {
//            inUsername.setText(username);
//            password = inPassword.getText().toString();
//            if(password == null) {
//                TextView label = (TextView) findViewById(R.id.textViewUserPasswordMessage);
//                label.setText(inPassword.getHint()+" enter password");
//                inPassword.setBackground(getDrawable(R.drawable.text_border_error));
//                return;
//            }
//
//            if(password.length() < 1) {
//                TextView label = (TextView) findViewById(R.id.textViewUserPasswordMessage);
//                label.setText(inPassword.getHint()+" enter password");
//                inPassword.setBackground(getDrawable(R.drawable.text_border_error));
//                return;
//            }
        }
        AuthenticationDetails authenticationDetails = new AuthenticationDetails(this.username, password, null);
        continuation.setAuthenticationDetails(authenticationDetails);
        continuation.continueTask();
    }





}
