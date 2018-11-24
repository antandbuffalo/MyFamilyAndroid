package com.antandbuffalo.myfamily;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utility {

    public static List<Member> filteredList(List<Member> members, String search) {

        List<Member> filtered = new ArrayList<Member>();
        String lowecaseSearch = search.toLowerCase();

        for(int i=0; i < members.size(); i++) {
            if(members.get(i).name.toLowerCase().contains(lowecaseSearch)) {
                filtered.add(members.get(i));
            }
        }
        return filtered;
    }

    public static int getMemberIndex(List<Member> members, Member member) {
        for(int i=0; i < members.size(); i++) {
            Log.d("i = " + i, members.get(i) + "");
            if(members.get(i).mid.longValue() == member.mid.longValue()) {
                return i;
            }
        }
        return -1;
    }

    public static List<Member> convertedToMember(List members) {
        List<Member> converted = new ArrayList<Member>();

        for(int i=0; i < members.size(); i++) {
            Log.d("Type", members.get(i).getClass() + "");
            converted.add(new Member((HashMap<String, Object>)members.get(i)));
        }

        return converted;
    }
}
