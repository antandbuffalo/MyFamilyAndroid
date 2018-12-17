package com.antandbuffalo.myfamily;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements DataServiceListener {
    MainViewModel mainViewModel;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalStorage.init(getApplicationContext());

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.main_progress_spinner);

        setupFirebase();

        if(!isAuthenticated()) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public boolean isAuthenticated() {
        String username = LocalStorage.getItem("username");
        String password = LocalStorage.getItem("password");
        return  Utility.validate(username, password);
    }

    public void populateList() {
        final ListView listview = (ListView) findViewById(R.id.listview);

        ArrayList<String> list = mainViewModel.getMenu();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("index", "i - " + i);
                switch (i) {
                    case 0: {
                        Intent intent = new Intent(getApplicationContext(), Members.class);
                        Set keysSet =  DataService.getInstance().getMembersMap().keySet();
                        List<String> keysList = new ArrayList<String>(keysSet);
                        intent.putExtra("memberKeys", (Serializable) keysList);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(getApplicationContext(), FamilyTree.class);
                        intent.putExtra("parentId", 0);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupFirebase() {
        DataService dataService = DataService.getInstance();
        dataService.init();
        dataService.addListener("main", this);
        if(!Utility.isConnected(getApplicationContext())) {
            dataService.getOfflineData();
        }
    }

    @Override
    public void onDataChange(List members) {
        progressBar.setVisibility(View.INVISIBLE);
        populateList();
    }
}
