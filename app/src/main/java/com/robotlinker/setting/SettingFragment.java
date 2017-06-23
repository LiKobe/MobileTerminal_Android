package com.robotlinker.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.robotlinker.R;
import com.robotlinker.base.AppHelper;
import com.robotlinker.base.BaseFragment;
import com.robotlinker.robot.event.ConnectSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import ros.ROSClient;
import ros.rosbridge.ROSBridgeClient;

/**
 * Created by gaowubin on 2017/6/14.
 */

public class SettingFragment extends BaseFragment {

    @BindView(R.id.tvAccountSetings)
    TextView textView;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.tvIP)
    TextView tvIP;
    @BindView(R.id.edtIP)
    EditText edtIP;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.edtPort)
    EditText edtPort;
    @BindView(R.id.btnConnect)
    Button btnConnect;
    @BindView(R.id.btnTest)
    Button btnTest;
    @BindView(R.id.tvAws)
    TextView tvAws;
    @BindView(R.id.btnAWSTest)
    Button btnAWSTest;
    //rosbridge 连接
    ROSBridgeClient client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> list = new ArrayList<String>();
        list.add("URIO");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        RxView.clicks(btnConnect)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // TODO: 2017/6/14  connet
                        Logger.i("btnConnect1");
                        if (TextUtils.isEmpty(edtIP.getText().toString())) {
                            return;
                        }
                        if (TextUtils.isEmpty(edtPort.getText().toString())) {
                            return;
                        }
                        Logger.i("btnConnect");
                        connectRos(edtIP.getText().toString(), edtPort.getText().toString());
                    }
                });

        RxView.clicks(btnTest)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // TODO: 2017/6/21  test
                    }
                });

        RxView.clicks(btnAWSTest)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // TODO: 2017/6/21  awstest
                        startActivity(new Intent(getActivity(), PubSubActivity.class));
                    }
                });
    }

    private void connectRos(String ip, String port) {
        client = new ROSBridgeClient("ws://" + ip + ":" + port);
        boolean conneSucc = client.connect(new ROSClient.ConnectionStatusListener() {
            @Override
            public void onConnect() {
                client.setDebug(true);
                AppHelper.setRosClient(client);
//                showTip("Connect ROS success");
                Logger.i("Connect ROS success");
                EventBus.getDefault().post(new ConnectSuccessEvent());
            }

            @Override
            public void onDisconnect(boolean normal, String reason, int code) {
//                showTip("ROS disconnect");
                Logger.i("ROS disconnect");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
//                showTip("ROS communication error");
                Logger.i("ROS communication error");
            }
        });
    }

}
