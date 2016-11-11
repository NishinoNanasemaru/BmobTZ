package com.tz.bmobtz.been;

import cn.bmob.v3.BmobObject;

/**
 * Created by 西野七濑 on 2016/11/12.
 */

public class FeedBack extends BmobObject{
    private String memberName,memberNum;

    public String getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
