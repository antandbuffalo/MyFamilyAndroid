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
import java.util.function.Consumer;

public class FirebaseDataService implements DataService {
    DatabaseReference myRef;
    MainActivity senderRef;
    ArrayList members;
    private static FirebaseDataService firebaseDataService;

    public static FirebaseDataService getInstance() {
        if(firebaseDataService == null) {
            firebaseDataService = new FirebaseDataService();
        }
        return firebaseDataService;
    }

    private FirebaseDataService() {

    }

    public void setListener(MainActivity sender) {
        senderRef = sender;
        if(myRef == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference();
        }
        listenForDataChange();
    }

    public ArrayList getMembers() {
        return members;
    }

    public HashMap<String, String> getMember(Integer index) {
        Log.d("all", members + "");
        //Log.d("in", members.get(index) + "");
        return (HashMap<String, String>)members.get(index);
    }

    public void listenForDataChange() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                members = (ArrayList) dataSnapshot.child("members").getValue();
                senderRef.onDataChange(members);
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
