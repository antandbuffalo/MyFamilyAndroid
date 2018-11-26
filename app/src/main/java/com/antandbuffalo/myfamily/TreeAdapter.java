package com.antandbuffalo.myfamily;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeAdapter extends BaseAdapter {
    public List<TreeMember> data;


    public List<Member> getMembersFromTree(TreeMember treeMember) {
        List<Member> members = new ArrayList<Member>();
        if(treeMember.member1 != null) {
            members.add(treeMember.member1);
        }
        if(treeMember.member2 != null) {
            members.add(treeMember.member2);
        }
        return members;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_tree_cell, viewGroup, false);
        }
        TextView memberName = view.findViewById(R.id.treeMemberName);
        memberName.setText(data.get(i).title);

        Button editButton = view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
