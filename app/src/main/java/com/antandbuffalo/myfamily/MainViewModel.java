package com.antandbuffalo.myfamily;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    public ArrayList getMenu() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("View All Members");
        arrayList.add("View Family Tree");
        arrayList.add("Count");
        return arrayList;
    }
}
