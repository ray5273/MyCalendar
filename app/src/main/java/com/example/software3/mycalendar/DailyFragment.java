package com.example.software3.mycalendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DailyFragment extends Fragment {

    Button left,right;
    TextView textView;
    ListView listView;
    Calendar curday;
    public DailyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily,container,false);
        curday= Calendar.getInstance();

        textView = (TextView)view.findViewById(R.id.date_daily);
        listView = (ListView)view.findViewById(R.id.daily_lv);
        left = (Button)view.findViewById(R.id.btnLeft_daily);
        right = (Button)view.findViewById(R.id.btnright_daily);
        CalendarDay today = CalendarDay.today();
        String todate= today.getYear()+"-"+(today.getMonth()+1)+"-"+today.getDay();
        textView.setText(todate);

        Date d = new Date(curday.getTimeInMillis());
        String day;
        Log.e("next date:","year"+d.getYear()+" "+(d.getMonth()+1)+" "+d.getDate());
        if(d.getDate()<10)
            day="0"+d.getDate();
        else
            day=""+d.getDate();

        String new_date = (d.getYear()-100+2000)+"-"+(d.getMonth()+1)+"-"+day;
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

        listView = (ListView)view.findViewById(R.id.daily_lv);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curday.add ( Calendar.DATE, -1 );
                Date d = new Date(curday.getTimeInMillis());
                String day;
                Log.e("next date:","year"+d.getYear()+" "+(d.getMonth()+1)+" "+d.getDate());
                if(d.getDate()<10)
                    day="0"+d.getDate();
                else
                    day=""+d.getDate();

                String new_date = (d.getYear()-100+2000)+"-"+(d.getMonth()+1)+"-"+day;
                textView.setText(new_date);

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

                listView = (ListView)getView().findViewById(R.id.daily_lv);
                listView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curday.add ( Calendar.DATE, 1 );
                Date d = new Date(curday.getTimeInMillis());
                String day;
                Log.e("next date:","year"+d.getYear()+" "+(d.getMonth()+1)+" "+d.getDate());
                if(d.getDate()<10)
                    day="0"+d.getDate();
                else
                    day=""+d.getDate();

                String new_date = (d.getYear()-100+2000)+"-"+(d.getMonth()+1)+"-"+day;
                textView.setText(new_date);

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

                listView = (ListView)getView().findViewById(R.id.daily_lv);
                listView.setAdapter(adapter);

                adapter.notifyDataSetChanged();



            }
        });


    }
}
