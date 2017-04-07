package com.udacity.stockhawk.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import timber.log.Timber;

public class QuoteJobService extends JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        // you are using a not necessary getApplicationContext() everywhere  , instead
        // you should use this try to change and tell me if it runs correctly I dont know where I have to change it was created by udacity not me I mean this java class
        // let me check my code show me your emulator
        // show me again your emulator

        // it's taking too much time to retrive from cursor, don't know why can we try to change
        //  emulator ? emulator ???? to what bro

        // there isn't any error simply the loader are not able to load I dont know why because mine
        //  it works, have you changed something inside main activity? no

        // from error you posted on slack seems that your emulator have a lack of RAM so I asket you to change emulator okay can you help me to change ram of for big size
        //  let me check, so strange it has 2 Gb of RAM and there isn't any error on the logs

        // Sorry I'm not able to help you, your code is right but I don't know why symbols are not showing on startup what I have to do maybe we should ask some help from fellow students on slack
        // try maybe someone are able to help you okay thank you I will post on udacity forum and stackoverflow maybe.

        Timber.d("Intent handled");
        Intent nowIntent = new Intent(getApplicationContext(), QuoteIntentService.class);
        getApplicationContext().startService(nowIntent);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


}
