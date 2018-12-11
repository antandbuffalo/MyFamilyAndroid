package com.antandbuffalo.myfamily;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utility {

    public static HashMap<String, Object> genericMemberFromMember(Member member) {
        HashMap<String, Object> genericMember = new HashMap<String, Object>();
        genericMember.put("uniqueId", member.uniqueId);
        genericMember.put("dob", member.dob);
        genericMember.put("familyId", member.familyId);
        genericMember.put("isJoinedOurFamily", member.isJoinedOurFamily);
        genericMember.put("living", member.living);
        genericMember.put("mid", member.mid);
        genericMember.put("name", member.name);
        genericMember.put("nickName", member.nickName);
        genericMember.put("parentId", member.parentId);

        return genericMember;
    }

    public static HashMap<String, Member> convertedToMembersMap(HashMap<String, Object> members) {
        HashMap<String, Member> membersMap = new HashMap<String, Member>();
        for (HashMap.Entry<String, Object> entry : members.entrySet()) {
            Member member = new Member(entry.getKey(), (HashMap<String, Object>)entry.getValue());
            membersMap.put(member.uniqueId, member);
        }
        return membersMap;
    }

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

    public static List<Member> convertedToMembersList(HashMap<String, Object> membersMap) {
        List<Member> converted = new ArrayList<Member>();
        for (HashMap.Entry<String, Object> entry : membersMap.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            Member member = new Member(entry.getKey(), (HashMap<String, Object>)entry.getValue());
            converted.add(member);
        }
        return converted;
    }

    public static List<Member> getMembersHavingParentId(List<Member> members, Long parentId) {
        List<Member> filtered = new ArrayList<Member>();
        for (int i=0; i < members.size(); i++) {
            Member temp = members.get(i);
            if(temp.parentId == parentId) {
                filtered.add(temp);
            }
        }
        return filtered;
    }

    public static List<TreeMember> mergedSameFamilyMembers(List<Member> members) {
        HashMap<String, TreeMember> merged = new HashMap<String, TreeMember>();

        for (int i=0; i < members.size(); i++) {
            Member member = members.get(i);
            String key = member.familyId + "";
            TreeMember treeMember = merged.get(key);
            if(treeMember == null) {
                treeMember = new TreeMember();
                treeMember.member1 = member;
                merged.put(key, treeMember);
            }
            else {
                if(treeMember.member1 == null) {
                    treeMember.member1 = member;
                }
                else {
                    treeMember.member2 = member;
                }
            }
        }

        List<TreeMember> mergedList = new ArrayList<TreeMember>();
        for (HashMap.Entry<String, TreeMember> entry : merged.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if(entry.getValue().member1 != null) {
                entry.getValue().title = entry.getValue().member1.name;
            }
            if(entry.getValue().member2 != null) {
                entry.getValue().title = entry.getValue().title + " - " + entry.getValue().member2.name;
            }

            mergedList.add(entry.getValue());
        }
        return mergedList;
    }
}
