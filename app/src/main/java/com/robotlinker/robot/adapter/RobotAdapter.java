package com.robotlinker.robot.adapter;


import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.robotlinker.R;
import com.robotlinker.base.ActivityUtil;
import com.robotlinker.base.BaseAdapterInject;
import com.robotlinker.base.BaseViewHolderInject;
import com.robotlinker.base.ListStringComparator;
import com.robotlinker.robot.bean.Robot;
import com.robotlinker.robot.widget.FlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gaowubin on 2017/6/18.
 */

public class RobotAdapter extends BaseAdapterInject<Robot> {

    int width;

    public RobotAdapter(Context context) {
        super(context);
        width = ActivityUtil.getScreenWidth(mContext) / 2;
        System.out.println("width: " + width);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.listitem_robot;
    }

    @Override
    public BaseViewHolderInject<Robot> getNewHolder(int position) {
        return new ViewHolder();
    }


    class ViewHolder extends BaseViewHolderInject<Robot> {

        @BindView(R.id.tvRobotName)
        TextView tvRobotName;
        @BindView(R.id.flRobotAttribute)
        FlowLayout flRobotAttribute;

        @Override
        public void loadData(Robot data, int position) {
            tvRobotName.setText(data.getKey());
            String json = new Gson().toJson(data.getValue());
            try {
                JSONObject customJson = new JSONObject(json);
                System.out.println(customJson.names());
                List<String> list = new Gson().fromJson(customJson.names().toString(), new TypeToken<List<String>>() {
                }.getType());
                Collections.sort(list, new ListStringComparator());
                flRobotAttribute.removeAllViews();
                for (String s : list) {
                    TextView textView = new TextView(mContext);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setText("" + s + ": \t" + customJson.get(s));
                    textView.setTextSize(28);
                    textView.setPadding(30, 10, 30, 10);
                    System.out.println(s);
                    System.out.println(customJson.get(s));
                    flRobotAttribute.addView(textView);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
