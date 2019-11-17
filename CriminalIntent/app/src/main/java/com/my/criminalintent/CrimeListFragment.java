package com.my.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private final int ITEM_TYPE1 = 1;
    private final int ITEM_TYPE2 = 2;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mDateTextView;
        private TextView mTitleTextView;
        private Button mPoliceButton;
        private Crime mCrime;
        public CrimeHolder(View view, int viewGroup) {
            super(view);
            switch (viewGroup) {
                case ITEM_TYPE1:
                    mPoliceButton = itemView.findViewById(R.id.police_button);
                    mDateTextView = itemView.findViewById(R.id.crime_date);
                    mTitleTextView = itemView.findViewById(R.id.crime_title);
                case ITEM_TYPE2:
                    mDateTextView = itemView.findViewById(R.id.crime_date);
                    mTitleTextView = itemView.findViewById(R.id.crime_title);
            }
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mDateTextView.setText(mCrime.getDate().toString());
            mTitleTextView.setText(mCrime.getTitle());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mCrime.getTitle() + "clicked", Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {


        private List<Crime> mCrimes;

        public CrimeAdapter(List crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position) {
           return mCrimes.get(position).isRequarePolice() ? ITEM_TYPE1 : ITEM_TYPE2;

        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            switch (viewType) {
                case ITEM_TYPE1:
                   v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_police_crime, parent, false);
                   break;
                case ITEM_TYPE2:
                   v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crime, parent, false);
                   break;
                default:
                   v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_police_crime, parent, false);
            }
            return new CrimeHolder(v, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    public void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mCrimeAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mCrimeAdapter);
    }
}
