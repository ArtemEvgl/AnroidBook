package com.my.criminalintent.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private HashMap<UUID, Crime> mCrimes;

    private CrimeLab(Context context) {
        mCrimes = new LinkedHashMap<>();
        for(int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle(String.format("Crime # %d", i));
            crime.setSolved(i % 2 == 0);
            crime.setRequarePolice(i % 2 ==0);
            mCrimes.put(crime.getUUID(), crime);
        }
    }

    public static CrimeLab get(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public Crime getCrime(UUID id) {
        return mCrimes.get(id);
    }

    public List<Crime> getCrimes() {
        return new ArrayList<>(mCrimes.values());
    }

}
