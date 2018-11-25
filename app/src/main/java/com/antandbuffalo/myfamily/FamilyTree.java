package com.antandbuffalo.myfamily;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FamilyTree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_tree);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Long parentId = intent.getLongExtra("parentId", 0L);
        List<Member> sameParentMembers = Utility.getMembersHavingParentId(FirebaseDataService.getInstance().getMembers(), parentId);
        List<TreeMember> finalData = Utility.mergedSameFamilyMembers(sameParentMembers);
        Log.d("final", finalData + "");

        ListView listView = findViewById(R.id.treeListView);
        TreeAdapter treeAdapter = new TreeAdapter();
        treeAdapter.data = finalData;

        listView.setAdapter(treeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), FamilyTree.class);
                intent.putExtra("parentId", finalData.get(i).member1.familyId);
                startActivity(intent);
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

}
