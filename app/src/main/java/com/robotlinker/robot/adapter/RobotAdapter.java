package com.robotlinker.robot.adapter;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.robotlinker.R;
import com.robotlinker.base.AppHelper;
import com.robotlinker.base.BaseAdapterInject;
import com.robotlinker.base.BaseViewHolderInject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaowubin on 2017/6/18.
 */

public class RobotAdapter extends BaseAdapterInject<String> {


    public RobotAdapter(Context context) {
        super(context);
    }


    @Override
    public int getConvertViewId(int position) {
        return R.layout.listitem_robot;
    }

    @Override
    public BaseViewHolderInject<String> getNewHolder(int position) {
        return new ViewHolder();
    }

    public void subscribe(String detailName, boolean isSubscribe) {
        if (!isSubscribe) {
            AppHelper.getRosClient().send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");
        } else {
            AppHelper.getRosClient().send("{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}");
        }
    }


    class ViewHolder extends BaseViewHolderInject<String> {

        @BindView(R.id.tvRobotName)
        TextView tvRobotName;
        @BindView(R.id.btnSubscribe)
        Button btnSubscribe;

        @OnClick(R.id.btnSubscribe)
        public void onClickSubscribe(View v) {
            Logger.i("onClickSubscribe");
            String data = btnSubscribe.getText().toString();
            boolean isSubscribe = data.equals("subscribe") ? true : false;
            btnSubscribe.setText(isSubscribe ? "unSubscribe" : "subscribe");
            subscribe(data, isSubscribe);

        }

        @Override
        public void loadData(String data, int position) {
            tvRobotName.setText(data);

//            String json = new Gson().toJson(data);
//            try {
//                JSONObject customJson = new JSONObject(json);
//                System.out.println(customJson.names());
//                List<String> list = new Gson().fromJson(customJson.names().toString(), new TypeToken<List<String>>() {
//                }.getType());
//                Collections.sort(list, new ListStringComparator());
//                flRobotAttribute.removeAllViews();
//                for (String s : list) {
//                    TextView textView = new TextView(mContext);
//                    textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    textView.setText("" + s + ": \t" + customJson.get(s));
//                    textView.setTextSize(24);
//                    textView.setPadding(30, 10, 30, 10);
//                    System.out.println(s);
//                    System.out.println(customJson.get(s));
//                    flRobotAttribute.addView(textView);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }
    }
}
