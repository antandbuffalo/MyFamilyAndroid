package com.antandbuffalo.myfamily;

import java.util.List;

public interface DataServiceListener {
    public void onDataChange(List members);

    default public void onUpdated(boolean status) {

    }
}
