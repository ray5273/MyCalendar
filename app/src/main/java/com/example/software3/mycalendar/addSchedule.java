package com.example.software3.mycalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class addSchedule extends AppCompatActivity{

    private DBHelper mDbOpenHelper;
    private EditText editText;
    private DatePicker datePicker;
    private Button button;
    //https://stackoverflow.com/questions/38048713/how-to-get-the-value-from-datepicker-on-android
    //get value
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(),DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION);
        Intent intent = getIntent();
        final int curpos = Integer.parseInt(intent.getStringExtra("pos"));

        datePicker = (DatePicker)findViewById(R.id.add_schedule_datepicker);
        editText = (EditText)findViewById(R.id.add_schedule_text);
        button = (Button)findViewById(R.id.add_schedule_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();

                if(content.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                String day;

                if(datePicker.getDayOfMonth() < 10 ) {
                    day = "0" + datePicker.getDayOfMonth();
                    Log.e("??","들어감");
                }
                else {
                    day = "" + datePicker.getDayOfMonth();
                    Log.e("??","안들어감");
                }

                String date =  datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+day;
                Log.e("db에 저장된 날짜=content",date+"="+content);
                dbHelper.insert(date,content);
                finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("pos",""+curpos);
                startActivity(intent);

            }
        });
    }
}
