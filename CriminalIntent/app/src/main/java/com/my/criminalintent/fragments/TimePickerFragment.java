package com.my.criminalintent.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.my.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "time";
    public static final String EXTRA_TIME = "extra_time";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialoge_time, null);

        mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Time of crime: ")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();
                        Date date = new GregorianCalendar(year, month, day, hour, minute, 0)
                                .getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();

    }

    private void sendResult(int resultOk, Date date) {
        if(getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultOk, intent);
    }
}
