package com.example.software3.mycalendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.Map;

public class WeeklyFragment extends Fragment {
    private MaterialCalendarView calendarView;
    private ListView listView;
    public String dates;
    public WeeklyFragment() {
        // Required empty public constructor
    }

    //https://github.com/prolificinteractive/material-calendarview/issues/330





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
        View view = inflater.inflate(R.layout.fragment_weekly,container,false);
        String day;
        if(CalendarDay.today().getDay()<10)
            day="0"+CalendarDay.today().getDay();
        else
            day=""+CalendarDay.today().getDay();
        String new_date = CalendarDay.today().getYear()+"-"+(CalendarDay.today().getMonth()+1)+"-"+day;
        Log.e("날짜",new_date);
        DBHelper dbHelper = new DBHelper(getContext(),DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION);
        String getMessage = dbHelper.read(new_date);
        ArrayList<String> items = new ArrayList<String>();


        if(getMessage.isEmpty()){
            items.add("스케줄이 없습니다");
        }else {
            String[] imsi = getMessage.split("///");
            for (String item : imsi) {
                items.add(item);
            }
        }

        ArrayAdapter adapter
                = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_single_choice,items);

        listView = (ListView)view.findViewById(R.id.weekly_lv);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        calendarView = (MaterialCalendarView)getView().findViewById(R.id.calendarView_weekly);
        calendarView.state().edit().setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        calendarView.setDateSelected(CalendarDay.today(), true);
        super.onViewCreated(view, savedInstanceState);

        Map<Integer,CalendarDay> map = new HashMap<Integer, CalendarDay>();

        if(!dates.isEmpty()) {
            String[] imsi = dates.split("///");
            int index = 0;
            for (String item : imsi) {
                String[] date_split = item.split("-");
                Log.e("dates", date_split[0] + date_split[1] + date_split[2]);
                CalendarDay test = CalendarDay.from(Integer.parseInt(date_split[0]), Integer.parseInt(date_split[1]) - 1,
                        Integer.parseInt(date_split[2]));
                map.put(index++, test);
            }

            Collection<CalendarDay> collection = map.values();
            EventDecorator eventDecorator = new EventDecorator(Color.RED, collection);


            calendarView.addDecorator(eventDecorator);
        }
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String day;

                if(date.getDay()<10)
                    day="0"+date.getDay();
                else
                    day=""+date.getDay();
                Log.e("day",day);
                String new_date = date.getYear()+"-"+(date.getMonth()+1)+"-"+day;
                DBHelper dbHelper = new DBHelper(getContext(),DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION);
                String getMessage = dbHelper.read(new_date);
                ArrayList<String> items = new ArrayList<String>();


                if(getMessage.isEmpty()){
                    items.add("스케줄이 없습니다");
                }else {
                    String[] imsi = getMessage.split("///");
                    for (String item : imsi) {
                        items.add(item);
                    }
                }

                ArrayAdapter adapter
                        = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_single_choice,items);

                listView = (ListView)getView().findViewById(R.id.weekly_lv);
                listView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }
        });








    }

    @Override
    public void onResume() {
        super.onResume();

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
