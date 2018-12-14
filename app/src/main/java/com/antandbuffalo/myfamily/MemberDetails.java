package com.antandbuffalo.myfamily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberDetails extends AppCompatActivity implements DataServiceListener {
    List<Member> members;
    MemberDetails self = this;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Member member = (Member) intent.getSerializableExtra("member");
        //String index = intent.getStringExtra("index");
        Log.d("index - ", member + "");

        DataService dataService = DataService.getInstance();
        members = dataService.getMembers();

        //HashMap<String, String> member = firebaseDataService.getMember(index);
        //HashMap<String, String> member = (HashMap<String, String>) members.get(index);

        progressBar = findViewById(R.id.progress_member_update);
        progressBar.setVisibility(View.INVISIBLE);

        EditText name = (EditText)findViewById(R.id.name);
        EditText date = (EditText)findViewById(R.id.date);
        EditText month = (EditText)findViewById(R.id.month);
        EditText year = (EditText)findViewById(R.id.year);
        EditText nickName = (EditText)findViewById(R.id.nickName);

        name.setText(member.name);
        nickName.setText(member.nickName);

        date.setText(getDate(member.dob));
        month.setText(getMonth(member.dob));
        year.setText(getYear(member.dob));

        Button save = findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(member.name == null) {
                    Utility.showToast(getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT);
                    return;
                }

                if(!Utility.isConnected(getApplicationContext())) {
                    Utility.showToast(getApplicationContext(), "Please connect to Internet", Toast.LENGTH_LONG);
                    return;
                }
                member.name = name.getText().toString();
                member.nickName = nickName.getText().toString();
                String dob = year.getText().toString() + "-" + month.getText().toString() + "-" +  date.getText().toString();
                member.dob = dob;

                HashMap<String, Member> memberHashMap = DataService.getInstance().getMembersMap();
                memberHashMap.put(member.uniqueId, member);
                DataService.getInstance().setMembersMap(memberHashMap);

                dataService.addUpdateListener("memberDetail", self);

                progressBar.setVisibility(View.VISIBLE);
                dataService.update(member);

                //finish();
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public String getYear(String dob) {
        if(dob != null) {
            String[] dobComponents = dob.split("-");
            if(dobComponents.length > 0) {
               return dobComponents[0];
            }
         }
        return "";
    }

    public String getMonth(String dob) {
        if(dob != null) {
            String[] dobComponents = dob.split("-");
            if(dobComponents.length > 1) {
                return dobComponents[1];
            }
        }
        return "";
    }

    public String getDate(String dob) {
        if(dob != null) {
            String[] dobComponents = dob.split("-");
            if(dobComponents.length > 2) {
                return dobComponents[2];
            }
        }
        return "";
    }

    @Override
    public void onDataChange(List members) {

    }

    @Override
    public void onUpdated(boolean status) {
        Log.i("status", status + "");
        progressBar.setVisibility(View.INVISIBLE);
        Utility.showToast(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT);
    }
}
