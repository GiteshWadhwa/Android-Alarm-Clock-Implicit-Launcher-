package com.gitesh.wadhwa.alaram;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
   int startID;
   boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);




        //fetch the extra string values
        String state=intent.getExtras().getString("extra");

        Log.e("Ringtone state extra is",state);

        //this converts the extra strings from the intent
        //to the start IDs,values 0 or 1
        assert state !=null;
        switch(state) {
           case "alarm on":
                startID = 1;
                break;
            case "alarm off":
                startID = 0;
                Log.e("Start ID is",state);
                break;
            default:
                startID = 0;
                break;
            }


            //if else statements
        //if there is no music playing,and the user pressed "alarm on"
        if(!this.isRunning  && startID==1){
            Log.e("there is no music ","and you want to start");

            //create an instance of media player....
            media_song=MediaPlayer.create(this,R.raw.yaari);
            //start the ringtone
            media_song.start();
            this.isRunning=true;
            this.startID=0;
        }
        //if there is music playing ,and the user pressed "alarm off"
       //music should stop playing
        else if(this.isRunning && startID==0)
        {
            Log.e("there is  music ","and you want to end");

            //stop the ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning=true;
            this.startID=0;
        }
        //these are if the user presses random buttons
        //just to bug-proof the app
        //if there is no music playing,and the user preses,snd the user pressed "alarm-off"
        //do nothing
        else if(!this.isRunning && startID==0)
        {
            Log.e("there is no music ","and you want to end");
       this.isRunning=false;
       this.startID=0;
        }
        //if there is music playing and the user pressed "alarm on"
        //do nothing
        else if(this.isRunning && startID==1)
        {
            Log.e("there is  music ","and you want to start");
          this.isRunning=true;
          this.startID=0;
        }
        //can't think of anything else,just to catch the odd event
        else
        {
            Log.e("else ","somehow you reach this");
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
   Log.e("on Destroy called","ta da");
   super.onDestroy();
   this.isRunning=false;
    }
}
