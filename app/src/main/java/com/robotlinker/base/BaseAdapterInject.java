package com.robotlinker.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseAdapterInject<T> extends BaseAdapter {
    public LayoutInflater mInflater;
    public Context mContext;
    public List<T> dataList = new ArrayList<T>();

    public BaseAdapterInject(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置数据
     *
     * @param data
     * @see
     */
    public void setData(List<T> data) {
        if (dataList == null)
            dataList = new ArrayList<T>();
        dataList.clear();
        if (data != null)
            dataList.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 增加数据
     *
     * @param data
     * @see
     */
    public void addData(List<T> data) {
        if (dataList == null)
            dataList = new ArrayList<T>();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void removeAllData() {
        if (dataList != null && dataList.size() > 0) {
            dataList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (dataList != null)
            return dataList.size();
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (dataList != null)
            try {
                return dataList.get(position);
            } catch (Exception g) {
                return null;
            }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract int getConvertViewId(int position);

    public abstract BaseViewHolderInject<T> getNewHolder(int position);

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolderInject<T> holder;
        if (convertView == null) {
            convertView = mInflater.inflate(getConvertViewId(position), parent, false);
            holder = getNewHolder(position);
            ButterKnife.bind(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolderInject<T>) convertView.getTag();
        }
        holder.loadData(getItem(position), position);
        return convertView;
    }

}
