package com.my.criminalintent.activityes;

import androidx.fragment.app.Fragment;

import com.my.criminalintent.fragments.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity {



    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }


}
