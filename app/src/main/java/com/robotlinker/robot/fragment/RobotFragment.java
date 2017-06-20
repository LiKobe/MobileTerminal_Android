package com.robotlinker.robot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.robotlinker.R;
import com.robotlinker.base.BaseFragment;
import com.robotlinker.robot.adapter.RobotAdapter;
import com.robotlinker.robot.bean.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gaowubin on 2017/6/14.
 */

public class RobotFragment extends BaseFragment {

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
        adapter.setData(getData());
        livRobot.setAdapter(adapter);

    }

    private List<Robot> getData() {
        List<Robot> rlist = new ArrayList<>();
        String robotJson = "{\"tcp post\":{\"x\":\"0.0mm\",\"y\":\"0.0mm\",\"z\":\"0.0mm\",\"Rx\":\"0.0°\",\"Ry\":\"0.0°\",\"Rz\":\"0.0°\"},\"joint value\":{\"J1\":\"180°\",\"J2\":\"180°\",\"J3\":\"180°\",\"J4\":\"180°\",\"J5\":\"180°\",\"J6\":\"180°\"}}";
        Map<String, Object> retMap = new Gson().fromJson(robotJson,
                new TypeToken<Map<String, Object>>() {
                }.getType());

        for (String p : retMap.keySet()) {
//            System.out.println("key:" + p + " values:" + retMap.get(p));
            Object o = retMap.get(p);
            Robot robot = new Robot();
            robot.setKey(p);
            robot.setValue(o);

//            String json=new Gson().toJson(o);
//            try {
//                JSONObject customJson = new JSONObject(json);
//                System.out.println(customJson.names());
//                List<String> list=new Gson().fromJson(customJson.names().toString(),new TypeToken<List<String>>(){}.getType());
//                for (String s : list){
//                    System.out.println(s);
//                    System.out.println(customJson.get(s));
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            rlist.add(robot);
        }
//        System.out.println(retMap);
        return rlist;
    }


}
