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
import java.util.List;

public class MemberDetails extends AppCompatActivity {
    List<Member> members;
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

        FirebaseDataService firebaseDataService = FirebaseDataService.getInstance();
        members = firebaseDataService.getMembers();

        //HashMap<String, String> member = firebaseDataService.getMember(index);
        //HashMap<String, String> member = (HashMap<String, String>) members.get(index);

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
            CharSequence text = "Please enter name";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            @Override
            public void onClick(View view) {
                if(member.name == null) {
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }

                member.name = name.getText().toString();
                member.nickName = nickName.getText().toString();
                String dob = year.getText().toString() + "-" + month.getText().toString() + "-" +  date.getText().toString();
                member.dob = dob;

                int index = Utility.getMemberIndex(members, member);
                if(index > -1) {
                    members.set(index, member);
                }

                firebaseDataService.getDatabaseReference().child("members").setValue(members);
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
