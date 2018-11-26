package com.antandbuffalo.myfamily;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataService implements DataService {
    List<Member> members;
    DataService sender;
    private static FirebaseDataService firebaseDataService;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;

    public static FirebaseDataService getInstance() {
        if(firebaseDataService == null) {
            firebaseDataService = new FirebaseDataService();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
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
        sender = (DataService)delegate;
        listenForDataChange();
    }

    public void setMembers(List<Member> newMembers) {
        this.members = newMembers;
    }

    public List getMembers() {
        return members;
    }

    public void listenForDataChange() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List fbMembers = (ArrayList) dataSnapshot.child("members").getValue();


                members = Utility.convertedToMember(fbMembers);

                sender.onDataChange(members);
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
