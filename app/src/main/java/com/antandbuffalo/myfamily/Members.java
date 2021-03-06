package com.antandbuffalo.myfamily;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class Members extends AppCompatActivity {

    List<Member> listViewData = new ArrayList<Member>();
    DefaultAdapter defaultAdapter;
    List<Member> members;
    ListView listview;
    List<String> memberKeys;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        memberKeys = (List<String>) intent.getSerializableExtra("memberKeys");
        members = Utility.getMembersFromKeys(memberKeys, DataService.getInstance().getMembersMap());

        loadListView(members);

        search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listViewData = Utility.filteredList(members, editable.toString());
                defaultAdapter.members = listViewData;
                defaultAdapter.notifyDataSetChanged();
            }
        });
    }

    public void prepareData() {
        listViewData.clear();
        for (int i = 0; i < members.size(); ++i) {
            Member member = members.get(i);
            listViewData.add(member);
        }
        defaultAdapter.members = listViewData;
    }

    public void loadListView(List<Member> members) {
        listview = (ListView) findViewById(R.id.listview);
        defaultAdapter = new DefaultAdapter();

        prepareData();

        listview.setAdapter(defaultAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("index", "i - " + i);
                //MemberDetails memberDetails = new Inte
                Intent intent = new Intent(getApplicationContext(), MemberDetails.class);
                intent.putExtra("index", i);
                Member selectedMember = listViewData.get(i);
                Member updatedMember = DataService.getInstance().getMembersMap().get(selectedMember.uniqueId);
                intent.putExtra("member", updatedMember);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("1111111", "onRestart");

        members = Utility.getMembersFromKeys(memberKeys, DataService.getInstance().getMembersMap());
        listViewData = Utility.filteredList(members, search.getText().toString());
        defaultAdapter.members = listViewData;
        defaultAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("2222222", "resume");
    }
}
