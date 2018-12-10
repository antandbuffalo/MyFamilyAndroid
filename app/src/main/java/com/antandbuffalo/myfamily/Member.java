package com.antandbuffalo.myfamily;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

public class Member extends Object implements Serializable {
    String dob, name, nickName, uniqueId;
    Long familyId, mid, parentId;
    Boolean isJoinedOurFamily, living;

    Member(String uniqueId, HashMap<String, Object> currentMember) {
        this.uniqueId = uniqueId;
        this.dob = (String)currentMember.get("dob");
        this.familyId = (Long) currentMember.get("familyId");
        this.isJoinedOurFamily = (Boolean) currentMember.get("isJoinedOurFamily");
        this.living = (Boolean)currentMember.get("living");
        this.mid = (Long) currentMember.get("mid");
        this.name = (String)currentMember.get("name");
        this.nickName = (String)currentMember.get("nickName");
        this.parentId = (Long) currentMember.get("parentId");
    }
}
