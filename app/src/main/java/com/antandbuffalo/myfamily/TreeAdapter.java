package com.antandbuffalo.myfamily;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import java.util.Set;

public class TreeAdapter extends BaseAdapter {
    public List<TreeMember> data;


    public List<String> getMemberKeysFromTree(TreeMember treeMember) {
        List<String> memberKeys = new ArrayList<String>();
        if(treeMember.member1 != null) {
            memberKeys.add(treeMember.member1.uniqueId);
        }
        if(treeMember.member2 != null) {
            memberKeys.add(treeMember.member2.uniqueId);
        }
        return memberKeys;
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
        editButton.setTag(i);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TreeMember treeMember = data.get((int)view.getTag());
                List<String> memberKeys = getMemberKeysFromTree(treeMember);

                Intent intent = new Intent(viewGroup.getContext(), Members.class);
                intent.putExtra("memberKeys", (Serializable) memberKeys);
                viewGroup.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
