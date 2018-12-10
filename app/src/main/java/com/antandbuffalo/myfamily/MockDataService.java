package com.antandbuffalo.myfamily;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockDataService implements DataService, DataServiceListener {
    HashMap<String, DataServiceListener> listeners = new HashMap<String, DataServiceListener>();
    List<Member> members = new ArrayList<Member>();
    HashMap<String, Object> mockMembers = new HashMap<String, Object>();
    HashMap<String, Member> membersMap = new HashMap<String, Member>();

    public static MockDataService mockDataService;

    public static MockDataService getInstance() {
        if(mockDataService == null) {
            mockDataService = new MockDataService();
        }
        return mockDataService;
    }

    @Override
    public void init() {
        if(members.size() == 0) {
            HashMap<String, Object> member = new HashMap<String, Object>();
            member.put("dob", "1988-12-12");
            member.put("familyId", 1L);
            member.put("isJoinedOurFamily", false);
            member.put("living", true);
            member.put("mid", 2L);
            member.put("name", "Jeyabalaji");
            member.put("nickName", "JBL");
            member.put("parentId", 0L);
            mockMembers.put("member2", member);

            member.put("dob", "1994-02-09");
            member.put("familyId", 2L);
            member.put("isJoinedOurFamily", false);
            member.put("living", true);
            member.put("mid", 3L);
            member.put("name", "Jeyachandran");
            member.put("nickName", "Chandru");
            member.put("parentId", 0L);
            mockMembers.put("member3", member);
        }
    }

    @Override
    public void addListener(String key, DataServiceListener listener) {
        listeners.put(key, listener);
        members = Utility.convertedToMembersList(mockMembers);
        membersMap = Utility.convertedToMembersMap(mockMembers);
        //members = Utility.listFromMap(membersMap);
        onDataChange(members);
    }

    @Override
    public List<Member> getMembers() {
        return members;
    }

    @Override
    public HashMap<String, Member> getMembersMap() {

        return membersMap;
    }

    @Override
    public void update(List<Member> members) {
        this.members = members;
    }

    @Override
    public void onDataChange(List members) {
        for (Map.Entry<String, DataServiceListener> entry : listeners.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if(entry.getValue() != null) {
                entry.getValue().onDataChange(members);
            }
        }
    }
}
