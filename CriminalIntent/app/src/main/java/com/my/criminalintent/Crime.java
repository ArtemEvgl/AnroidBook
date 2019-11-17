package com.my.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mUUID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public boolean isRequarePolice() {
        return mRequarePolice;
    }

    public void setRequarePolice(boolean requarePolice) {
        mRequarePolice = requarePolice;
    }

    private boolean mRequarePolice;

    public Crime() {
        mUUID = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getUUID() {
        return mUUID;
    }

    public Date getDate() {
        return mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
