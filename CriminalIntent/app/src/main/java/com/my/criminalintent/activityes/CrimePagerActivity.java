package com.my.criminalintent.activityes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private Button mButtonFirst;
    private Button mButtonLast;

    private List<Crime> mCrimeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        setContentView(R.layout.activity_crime_pager2);
        mViewPager = findViewById(R.id.crime_view_pager);
        mButtonFirst = findViewById(R.id.button_first);
        mButtonLast = findViewById(R.id.button_last);

        mCrimeList = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager(); //нужен для взаимодействия FragmentStatePagerAdapter с активностью
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimeList.get(position); // используется для подгрузки и плавного пролистывания итемов.
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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateButtons(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    public void clickGoLast(View view) {
        mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
    }

    public void clickGoFirst(View view) {
        mViewPager.setCurrentItem(0);
    }

    public void updateButtons(int i) {
        if(i == 0) {
            mButtonFirst.setEnabled(false);
        } else if(i == mViewPager.getAdapter().getCount() - 1) {
            mButtonLast.setEnabled(false);
        } else {
            mButtonLast.setEnabled(true);
            mButtonFirst.setEnabled(true);
        }
    }

}
