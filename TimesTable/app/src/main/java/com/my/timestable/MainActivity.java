package com.my.timestable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListViewNumbers;
    private SeekBar mSeekBar;

    private ArrayList<Integer> numbers;

    private int max = 20;
    private int min = 1;
    private int count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewNumbers = findViewById(R.id.listViewNumbers);
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setMax(max);
        numbers = new ArrayList<>();
        final ArrayAdapter<Integer> arrayAdapterNumbers = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, numbers);
        mListViewNumbers.setAdapter(arrayAdapterNumbers);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numbers.clear();
                if(progress < min) {
                    mSeekBar.setProgress(min);
                }
                for(int i = min; i <= count; i++) {
                    numbers.add(mSeekBar.getProgress() * i);
                }
                arrayAdapterNumbers.notifyDataSetChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBar.setProgress(10);
    }
}
