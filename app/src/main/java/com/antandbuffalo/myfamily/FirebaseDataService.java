package com.antandbuffalo.myfamily;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDataService implements DataService, DataServiceListener {
    List<Member> members;
    HashMap<String, Member> membersMap;
    DataServiceListener sender;
    HashMap<String, DataServiceListener> listeners = new HashMap<String, DataServiceListener>();
    private static FirebaseDataService firebaseDataService;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    @Override
    public void init() {
        if(firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            listenForDataChange();
        }
    }

    public static FirebaseDataService getInstance() {
        if(firebaseDataService == null) {
            firebaseDataService = new FirebaseDataService();
//            firebaseDatabase = FirebaseDatabase.getInstance();
//            databaseReference = firebaseDatabase.getReference();
        }

        return firebaseDataService;
    }

    private FirebaseDataService() {

    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    @Override
    public void onDataChange(List members) {

    }

    public void setListener(Object delegate) {
        sender = (DataServiceListener) delegate;
        listenForDataChange();
    }
    public void addListener(String key, DataServiceListener listener) {
        listeners.put(key, listener);
    }

    public void notifyListeners() {
        for (Map.Entry<String, DataServiceListener> entry : listeners.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if(entry.getValue() != null) {
                entry.getValue().onDataChange(members);
            }
        }
    };

    public void setMembers(List<Member> newMembers) {
        this.members = newMembers;
    }

    public List<Member> getMembers() {
        return members;
    }

    @Override
    public HashMap<String, Member> getMembersMap() {
        return membersMap;
    }

    @Override
    public void update(List<Member> members) {
        databaseReference.child("members").setValue(members);
    }

    public void listenForDataChange() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                HashMap<String, Object> fbMembers = (HashMap) dataSnapshot.child("membersMap").getValue();

                members = Utility.convertedToMembersList(fbMembers);
                notifyListeners();

                //sender.onDataChange(members);
                //senderRef.onDataChange(members);
                //callback.run(members);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FB2", "Failed to read value.", error.toException());
            }
        });
    }
}
