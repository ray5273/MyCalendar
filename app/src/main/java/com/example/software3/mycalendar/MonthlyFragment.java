package com.example.software3.mycalendar;

import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MonthlyFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private ListView listView;
    public String dates;
    public MonthlyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBHelper dbHelper = new DBHelper(getContext(),DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION);
        dates = dbHelper.readDate();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        calendarView = (MaterialCalendarView)getView().findViewById(R.id.calendarView_monthly);
        calendarView.state().edit().setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        calendarView.setDateSelected(CalendarDay.today(), true);

        Map<Integer,CalendarDay> map = new HashMap<Integer, CalendarDay>();


        if(!dates.isEmpty()) {
            String[] imsi = dates.split("///");
            int index = 0;
            for (String item : imsi) {
                String[] date_split = item.split("-");
                CalendarDay test = CalendarDay.from(Integer.parseInt(date_split[0]), Integer.parseInt(date_split[1]) - 1,
                        Integer.parseInt(date_split[2]));
                map.put(index++, test);
            }

            Collection<CalendarDay> collection = map.values();
            EventDecorator eventDecorator = new EventDecorator(Color.RED, collection);
            calendarView.addDecorator(eventDecorator);
        }



        super.onViewCreated(view, savedInstanceState);
    }
    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(8, color));
        }
    }

}
