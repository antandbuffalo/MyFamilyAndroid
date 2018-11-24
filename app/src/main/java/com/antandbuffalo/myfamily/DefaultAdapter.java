package com.antandbuffalo.myfamily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class DefaultAdapter extends BaseAdapter {
    public List<Member> members;

    @Override
    public int getCount() {
        if(members == null) {
            return 0;
        }
        return members.size();
    }

    @Override
    public Object getItem(int i) {
        return members.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.defualt_list_view_cell, viewGroup, false);
        }
        TextView memberName = view.findViewById(R.id.memberName);
        memberName.setText(members.get(i).name);
        return view;
    }
}
