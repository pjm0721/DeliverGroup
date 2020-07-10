package com.test.eat2;

public class Home_Grid_Item {
    String teamName;
    int resId;

    public Home_Grid_Item(String teamName, int resId) {
        this.teamName = teamName;
        this.resId = resId;
    }

    public String getTeamName(){
        return teamName;
    }

    public void setTeamName(String teamName){
        this.teamName = teamName;
    }

    public int getResId(){
        return resId;
    }

    public void setResId(int resId){
        this.resId = resId;
    }
}
