package com.antandbuffalo.myfamily;

import java.util.HashMap;
import java.util.List;

public interface DataService {

    public static DataService getInstance() {
        return FirebaseDataService.getInstance();
        //return MockDataService.getInstance();
    }
    public void init();
    public void addListener(String key, DataServiceListener listener);
    public List<Member> getMembers();
    public HashMap<String, Member> getMembersMap();

    public void addUpdateListener(String key, DataServiceListener listener);
    public void update(Member member);
}
