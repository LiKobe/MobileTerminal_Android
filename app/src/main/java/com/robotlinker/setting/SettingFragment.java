package com.robotlinker.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.robotlinker.R;
import com.robotlinker.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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


}
