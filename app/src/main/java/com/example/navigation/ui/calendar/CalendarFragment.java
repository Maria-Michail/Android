package com.example.navigation.ui.calendar;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.navigation.R;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CalendarFragment extends Fragment {


    private CalendarViewModel calendarViewModel;
    CalendarView calendarView;
    TextView calendar_title;
    String date;
    TextView nameDay;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar_title = root.findViewById(R.id.calendar_title);
        nameDay = root.findViewById(R.id.nameDay);

        calendarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                calendar_title.setText(s);
            }
        });

        calendarView = (CalendarView) root.findViewById(R.id.calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                date = simpleDateFormat.format(calendar.getTime());
                calendar_title.setText(date);

                //put name day
                calendarViewModel.getNameDay(month, dayOfMonth);
            }
        });

        final Button button = root.findViewById(R.id.calendar_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calendarViewModel.goToDay(calendar_title.getText().toString());
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

        calendarViewModel.getNameDayBack().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String nameDayGet) {
                nameDay.setText(nameDayGet);
            }
        });

        return root;
    }
}