package com.example.timepickertemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button bottomSheetDialogTimePickerBtn, cancelBtn, applyBtn;
    private TextView time1TextView, time2TextView, time3TextView, time4TextView, time5TextView;
    private int time1Hour, time1Minute, time2Hour, time2Minute, hour, minute;
    private TimePicker timePicker1, timePicker2, timePicker;

    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setReferences();

        bottomSheetDialogTimePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottomSheetDialog();
            }
        });


        time1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                time1Hour = i;
                                time1Minute = i1;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, time1Hour, time1Minute);

                                time1TextView.setText(DateFormat.format("hh:mm aa", calendar)); // import android.text.format.DateFormat;
                            }
                        }, 12, 0, false);

                timePickerDialog.updateTime(time1Hour, time1Minute);
                timePickerDialog.show();
            }
        });

        time2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                time2Hour = i;
                                time2Minute = i1;

                                String time = time2Hour + ":" + time2Minute;

                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");

                                try {
                                    Date date = f24Hours.parse(time);

                                    time2TextView.setText(f12Hours.format(date));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                , 12, 0, false);

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(time2Hour, time2Minute);
                timePickerDialog.show();
            }
        });

        timePicker1.setIs24HourView(true);
        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                String hourAndMin = i + ":" + i1;
                String colorTime = "<font color='#EE00CC'><b>" + hourAndMin + "</b></font>";

                time3TextView.setText(Html.fromHtml("Time: " + colorTime));
            }
        });

        timePicker2.setIs24HourView(true);
        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                time4TextView.setText(i + ":" + i1);
            }
        });

        time5TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), "");
            }
        });
    }

    private void setReferences(){
        bottomSheetDialogTimePickerBtn = findViewById(R.id.bottom_sheet_dialog_time_picker_btn);
        time1TextView = findViewById(R.id.time1_text_view);
        time2TextView = findViewById(R.id.time2_text_view);
        time3TextView = findViewById(R.id.time3_text_view);
        time4TextView = findViewById(R.id.time4_text_view);
        time5TextView = findViewById(R.id.time5_text_view);
        timePicker1 = findViewById(R.id.time_picker1);
        timePicker2 = findViewById(R.id.time_picker2);
    }

    private void openBottomSheetDialog(){
        bottomSheetDialog = new BottomSheetDialog(this, R.style.UploadBottomSheetDialogTheme);
        createBottomSheetDialog();
        bottomSheetDialog.show();

        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialogTimePickerBtn.setText(hour + ":" + minute);
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void createBottomSheetDialog(){
        View view = getLayoutInflater().inflate(R.layout.time_picker_bottom_sheet_dialog, null, false);

        timePicker = view.findViewById(R.id.time_picker);
        cancelBtn = view.findViewById(R.id.cancel_btn);
        applyBtn = view.findViewById(R.id.apply_btn);

        bottomSheetDialog.setContentView(view);
    }
}