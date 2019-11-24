package com.my.criminalintent.activityes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.my.criminalintent.R;
import com.my.criminalintent.data.Crime;
import com.my.criminalintent.data.CrimeLab;
import com.my.criminalintent.fragments.CrimeFragment;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "crime_id";

    private ViewPager mViewPager;

    private List<Crime> mCrimeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        setContentView(R.layout.activity_crime_pager);
        mViewPager = findViewById(R.id.crime_view_pager);

        mCrimeList = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager(); //нужен для взаимодействия FragmentStatePagerAdapter с активностью
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimeList.get(position);
                return CrimeFragment.newInstance(crime.getUUID());
            }

            @Override
            public int getCount() {
                return mCrimeList.size();
            }
        });

        for(int i = 0; i < mCrimeList.size(); i++) {
            if(mCrimeList.get(i).getUUID().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
}
