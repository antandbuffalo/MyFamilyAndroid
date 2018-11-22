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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MemberDetails extends AppCompatActivity {
    ArrayList members;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Integer index  = intent.getIntExtra("index", 0);
        //String index = intent.getStringExtra("index");
        Log.d("index - ", index + "");

        FirebaseDataService firebaseDataService = FirebaseDataService.getInstance();
        members = firebaseDataService.getMembers();

        //HashMap<String, String> member = firebaseDataService.getMember(index);
        HashMap<String, String> member = (HashMap<String, String>) members.get(index);

        EditText name = (EditText)findViewById(R.id.name);
        EditText date = (EditText)findViewById(R.id.date);
        EditText month = (EditText)findViewById(R.id.month);
        EditText year = (EditText)findViewById(R.id.year);
        EditText nickName = (EditText)findViewById(R.id.nickName);

        name.setText((String)member.get("name"));
        nickName.setText(member.get("nickName"));

        date.setText(getDate(member.get("dob")));
        month.setText(getMonth(member.get("dob")));
        year.setText(getYear(member.get("dob")));

        Button save = findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            CharSequence text = "Please enter name";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            @Override
            public void onClick(View view) {
                if(member.get("name") == null) {
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }

                member.put("name", name.getText().toString());
                member.put("nickName", nickName.getText().toString());
                String dob = year.getText().toString() + "-" + month.getText().toString() + "-" +  date.getText().toString();
                member.put("dob", dob);

                members.set(index, member);
                firebaseDataService.myRef.child("members").setValue(members);
                text = "Successfully Updated";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                finish();
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
}
