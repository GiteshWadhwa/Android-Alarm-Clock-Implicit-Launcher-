package com.gitesh.wadhwa.alaram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.e("We are in the receiver.","hii");

        //fetch extra strings from the intent..
     String get_your_string=intent.getExtras().getString("extra");

        Log.e("What is the key??",get_your_string);


        //create as intent to the ringtone service
        Intent service_intent=new Intent(context,RingtonePlayingService.class);

        //pass the extra string from Main Activity to the Ringtone Playing Services.
        service_intent.putExtra("extra",get_your_string);

        context.startService(service_intent);
    }
}
