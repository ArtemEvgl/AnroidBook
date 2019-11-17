package com.my.criminalintent;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

public class CrimeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
