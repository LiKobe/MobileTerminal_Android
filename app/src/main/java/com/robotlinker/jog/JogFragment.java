package com.robotlinker.jog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.robotlinker.R;
import com.robotlinker.base.BaseEventFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import ros.rosbridge.event.PublishEvent;


/**
 * Created by gaowubin on 2017/6/14.
 */

public class JogFragment extends BaseEventFragment {


    @BindView(R.id.tvJogInfo)
    TextView tvJogInfo;

    String s = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PublishEvent event) {
        if (s.length() > 2000) {
            s = "";
        }
        s = s + event.msg + "\n";
        tvJogInfo.setText("" + event.name + "\n\n" + s);
        Logger.i("info: " + event.msg);

    }


}
