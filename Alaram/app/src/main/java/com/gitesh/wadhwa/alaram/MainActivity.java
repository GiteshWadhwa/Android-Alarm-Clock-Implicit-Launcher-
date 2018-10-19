package com.gitesh.wadhwa.alaram;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.app.PendingIntent.*;

public class MainActivity extends AppCompatActivity {

    //to make our alarm manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;

    //tells the alaram manager to send a delayed intent
    /*A PendingIntent is a token that you give to a foreign application
    (e.g. NotificationManager , AlarmManager , Home Screen AppWidgetManager , or other 3rd party applications),
     which allows the foreign application to use your application's permissions to execute a predefined piece of code.*/
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.context = this;
        //initialize our alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialize out timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timepicker);

        //initialize our text update box
        update_text = (TextView) findViewById(R.id.update_text);

        //create an instance of calender
        final Calendar calendar = Calendar.getInstance();
        //create an intent to the Alarm Receiver class
        //tells which receiver to send to signal
        final Intent my_intent=new Intent(this.context,Alarm_Receiver.class);
        /********************************************************************************/
        //initialize  start buttons
        Button alarm_on = (Button) findViewById(R.id.alarm_on);
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //setting calender instance with the hour and minute that we picked
                //on the time picker
                //when the alaram manager broadcast to receiver.....
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getCurrentMinute());
                //get the string values of the hour and minute
                int hour = alarm_timepicker.getCurrentHour();
                int minute = alarm_timepicker.getCurrentMinute();
                //convert the int value to string
                String hour_String = String.valueOf(hour);
                String minute_String = String.valueOf(minute);
                //convert 24-hour time to 12-hour time
                if (hour > 12) {
                    hour_String = String.valueOf(hour - 12);
                }
                if (minute < 10) {
                    //10:7 --> 10:07
                    minute_String = "0" + String.valueOf(minute);
                }
                //method that changes the update text Textbox
                set_alarm_text("Alarm set: " + hour_String + ":" + minute_String);


                //put in extra string into my_intents
                //tells the clock that you passed the "alarm on" button
                my_intent.putExtra("extra","yes");//send it to receivers...

              /*************************************************************************************/
                //create a pending intent that delays the intents
                //until thew specified calander times
                //A PendingIntent itself is a token referencing the original data (Intent action, data, categories, etc.) maintained by the system.
                pending_intent= getBroadcast(MainActivity.this,0,my_intent, FLAG_UPDATE_CURRENT);
                //set the alaram manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                        pending_intent);
            /***********************************************************************************/
            }
        });


/*******************************************************************************************/
       //initialize the stop button
      Button alarm_off = (Button) findViewById(R.id.alarm_off);
      //create an onClick listener to stop the alarm or undo an alarm set
       alarm_off.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v)
         {
             set_alarm_text("ALARM_OFF");

             //cancel the alarm..
             alarm_manager.cancel(pending_intent);

             //put extra string into my intent
             //tells the clock that you pressed the "alarm off" button
                 my_intent.putExtra("extra","off");//send it to receivers....
             //stop the ringtone...
             sendBroadcast(my_intent);
         //this immediately sends signals to alaram receiver which immediately
             //sends a signal to ringtone playing service...

         }
     });
    }
/*********************************************************************************************/

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

}
