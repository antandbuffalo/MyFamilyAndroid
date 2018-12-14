package com.antandbuffalo.myfamily;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDataService implements DataService, DataServiceListener {
    List<Member> members;
    HashMap<String, Member> membersMap;
    DataServiceListener sender;
    HashMap<String, DataServiceListener> listeners = new HashMap<String, DataServiceListener>();
    HashMap<String, DataServiceListener> updateListeners = new HashMap<String, DataServiceListener>();

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

    @Override
    public void addListener(String key, DataServiceListener listener) {
        listeners.put(key, listener);
    }

    @Override
    public void addUpdateListener(String key, DataServiceListener listener) {
        updateListeners.put(key, listener);
    }


    public void notifyListeners() {
        for (Map.Entry<String, DataServiceListener> entry : listeners.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if(entry.getValue() != null) {
                entry.getValue().onDataChange(members);
            }
        }
    };

    public void notifyUpdateListeners(boolean status) {
        for (Map.Entry<String, DataServiceListener> entry : updateListeners.entrySet()) {
            if(entry.getValue() != null) {
                entry.getValue().onUpdated(status);
            }
        }
    };

    public void setMembers(List<Member> newMembers) {
        this.members = newMembers;
    }

    public List<Member> getMembers() {
        members = Utility.convertedToMembersList(membersMap);
        return members;
    }

    @Override
    public void setMembersMap(HashMap<String, Member> newMembersMap) {
        membersMap = newMembersMap;
    }


    @Override
    public HashMap<String, Member> getMembersMap() {
        return membersMap;
    }

    @Override
    public void update(Member member) {
        //databaseReference.child("membersMap").child(member.uniqueId).setValue(member);
        HashMap<String, Object> genericMember = Utility.genericMemberFromMember(member);
        databaseReference.child("membersMap").child(member.uniqueId).updateChildren(genericMember, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Log.i("success", "onComplete: success");
                    notifyUpdateListeners(true);
                } else {
                    Log.w("error", "onComplete: fail", databaseError.toException());
                    notifyUpdateListeners(false);
                }
            }
        });
        //databaseReference.child("members").setValue(members);
    }

    public void listenForDataChange() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                HashMap<String, Object> fbMembers = (HashMap) dataSnapshot.child("membersMap").getValue();

                membersMap = Utility.convertedToMembersMap(fbMembers);

                Gson gson = new Gson();
                String membersMapJson = gson.toJson(membersMap);
                LocalStorage.setItem("membersMapString", membersMapJson);

                members = Utility.convertedToMembersList(membersMap);
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

    @Override
    public void getOfflineData() {
        Gson gson = new Gson();
        String membersMapJson = LocalStorage.getItem("membersMapString");

        Type type = new TypeToken<HashMap<String, Member>>(){}.getType();

        membersMap = gson.fromJson(membersMapJson, type);
        members = Utility.convertedToMembersList(membersMap);
        notifyListeners();
    }
}
