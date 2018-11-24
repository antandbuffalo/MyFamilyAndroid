package com.antandbuffalo.myfamily;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

public class Member implements Serializable {
    String dob, name, nickName;
    Long familyId, mid, parentId;
    Boolean isJoinedOurFamily, living;

    Member(HashMap<String, Object> currentMember) {
        this.dob = (String)currentMember.get("dob");
        Log.d("a", currentMember.get("mid").getClass() + "");
        Log.d("a", currentMember.get("familyId").getClass() + "");
        Log.d("a", currentMember.get("parentId").getClass() + "");
        Log.d("a", currentMember.get("isJoinedOurFamily").getClass() + "");
        Log.d("a", currentMember.get("living").getClass() + "");

        this.familyId = (Long) currentMember.get("familyId");
        this.isJoinedOurFamily = (Boolean) currentMember.get("isJoinedOurFamily");
        this.living = (Boolean)currentMember.get("living");
        this.mid = (Long) currentMember.get("mid");
        this.name = (String)currentMember.get("name");
        this.nickName = (String)currentMember.get("nickName");
        this.parentId = (Long) currentMember.get("parentId");
    }
}
