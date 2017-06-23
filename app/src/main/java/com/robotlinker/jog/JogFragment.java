package com.robotlinker.jog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robotlinker.R;
import com.robotlinker.base.AppHelper;
import com.robotlinker.base.BaseFragment;
import com.robotlinker.base.PublishEvent;

import butterknife.ButterKnife;
import ros.rosbridge.ROSBridgeClient;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by gaowubin on 2017/6/14.
 */

public class JogFragment extends BaseFragment {

    private static final String TAG = "jogActivity";

    ROSBridgeClient client;


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

        EventBus.getDefault().register(this);

        client = AppHelper.getRosClient();
    }

//    public void onEvent(final PublishEvent event) {
//        if("/map".equals(event.name)) {
////            parseMapTopic(event);
//            return;
//        }
//
//        //show data on TextView
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if(tvLog.getText().length() > 2000) {
//                    tvLog.setText("");
//                }
//
//                tvLog.setText(tvLog.getText() + "\ninfo:  " + event.msg + "\n");
//
//                int offset=tvLog.getLineCount()*tvLog.getLineHeight();
//                if(offset>tvLog.getHeight()){
//                    tvLog.scrollTo(0,offset-tvLog.getHeight());
//                }
//            }
//        });
//        Log.d(TAG, event.msg);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
