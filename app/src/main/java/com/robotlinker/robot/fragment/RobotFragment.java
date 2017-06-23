package com.robotlinker.robot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.robotlinker.R;
import com.robotlinker.base.AppHelper;
import com.robotlinker.base.BaseEventFragment;
import com.robotlinker.robot.adapter.RobotAdapter;
import com.robotlinker.robot.bean.Robot;
import com.robotlinker.robot.event.ConnectSuccessEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ros.rosbridge.ROSBridgeClient;

/**
 * Created by gaowubin on 2017/6/14.
 */

public class RobotFragment extends BaseEventFragment {

    ROSBridgeClient client;
    @BindView(R.id.livRobot)
    ListView livRobot;
    RobotAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_robot, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RobotAdapter(mContext);
        livRobot.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectSuccessEvent event) {
        Logger.i("ConnectSuccessEvent");
        try {
            String[] topicList = AppHelper.getRosClient().getTopics();
            Logger.i("topicList: " + new Gson().toJson(topicList));
            List list = java.util.Arrays.asList(topicList);
            adapter.setData(list);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private List<Robot> getData() {
        List<Robot> rlist = new ArrayList<>();
        String robotJson = "{\"tcp post\":{\"x\":\"0.0mm\",\"y\":\"0.0mm\",\"z\":\"0.0mm\",\"Rx\":\"0.0°\",\"Ry\":\"0.0°\",\"Rz\":\"0.0°\"},\"joint value\":{\"J1\":\"180°\",\"J2\":\"180°\",\"J3\":\"180°\",\"J4\":\"180°\",\"J5\":\"180°\",\"J6\":\"180°\"}}";
        Map<String, Object> retMap = new Gson().fromJson(robotJson,
                new TypeToken<Map<String, Object>>() {
                }.getType());

        for (String p : retMap.keySet()) {
            Object o = retMap.get(p);
            Robot robot = new Robot();
            robot.setKey(p);
            robot.setValue(o);

            rlist.add(robot);
        }
        return rlist;
    }


}
