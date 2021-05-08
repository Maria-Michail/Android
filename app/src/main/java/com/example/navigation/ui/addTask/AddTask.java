package com.example.navigation.ui.addTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.navigation.R;
import com.example.navigation.ui.home.Task;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTask extends Fragment {

    EditText title;
    CheckBox deadline;
    EditText date;
    EditText time;
    private AddTaskViewModel addTaskViewModel;
    SimpleDateFormat simpleTimeFormat;
    SimpleDateFormat simpleDateFormat;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addTaskViewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);
        View root = inflater.inflate(R.layout.activity_add_task, container, false);
        title = root.findViewById(R.id.add_title);
        deadline = root.findViewById(R.id.checkBox);
        date = root.findViewById(R.id.add_date);
        time = root.findViewById(R.id.add_time);

        date.setInputType(InputType.TYPE_NULL);
        time.setInputType(InputType.TYPE_NULL);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(time);
            }
        });

        final Button button = root.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    insertTask();
                } catch (ParseException | NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Wrong date or time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void insertTask() throws ParseException {
        Date parseDate = simpleDateFormat.parse(date.getText().toString());
        Date parseTime = simpleTimeFormat.parse(time.getText().toString());
        if(null == title.getText().toString() || title.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Wrong Title", Toast.LENGTH_SHORT).show();
        }
        else if(title.getText().toString().length()>20){
            Toast.makeText(getContext(), "Title too long", Toast.LENGTH_SHORT).show();
        }
        else{
            addTaskViewModel.insert(new Task(title.getText().toString(), deadline.isChecked(), date.getText().toString(), time.getText().toString()));
            Toast.makeText(getContext(), "Task added", Toast.LENGTH_SHORT).show();
            title.setText("");
        }
    }

    private void showTimeDialog(EditText time) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                simpleTimeFormat = new SimpleDateFormat("HH:mm");
                time.setText(simpleTimeFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private void showDateDialog(EditText date) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}