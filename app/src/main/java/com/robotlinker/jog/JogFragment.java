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

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(PublishEvent event) {
//        if ("/map".equals(event.name)) {
////            parseMapTopic(event);
//            return;
//        }

        Logger.i("info: " + event.msg);

        //show data on TextView
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
    }


//
//    public void Subscribe(String detailName, boolean isSubscribe) {
//        if(isSubscribe) {
//            client.send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");
//            btnSubTopic.setText("Subscribe");
//        } else {
//            client.send("{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}");
//            btnSubTopic.setText("Unsubscribe");
//        }
//        isSubscribe = !isSubscribe;
//
//    }

}
