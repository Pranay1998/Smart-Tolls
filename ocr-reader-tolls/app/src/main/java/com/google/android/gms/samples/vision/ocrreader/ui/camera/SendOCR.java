package com.google.android.gms.samples.vision.ocrreader.ui.camera;

/**
 * Created by Pranay on 25/06/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.samples.vision.ocrreader.OcrDetectorProcessor;
import com.google.android.gms.samples.vision.ocrreader.OcrGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by Pranay on 15/06/17.
 * can be finalized when web part is done..
 * get all the ads to be scheduled and download the required files
 */

public class SendOCR {

    private Activity appActivity;
    private String response;
    private RequestQueue queue;
    private String URL;
    private String licence;
    private String tollID;
    private String time;


    public SendOCR(Activity myActivity, String link, String licence) {
        this.appActivity = myActivity;


        this.licence = licence;
        Random rand = new Random();


        this.tollID = OcrGraphic.array[Math.round(rand.nextInt(1))];
        int hour = new DateTime().getHourOfDay();
        int min = new DateTime().getMinuteOfHour();
        int sec = new DateTime().getSecondOfMinute();

        this.time = hour + ":" + min + ":" + sec;


        this.URL = link;


        getInfo();


    }

    @Nullable
    private void getInfo() {



        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String result) {
                        Log.d("Resuly",result);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {


                Map<String, String> params = new HashMap<String, String>();
                params.put("licence_no", licence);
                params.put("toll_id",tollID);
                params.put("time",time);
                return params;


            }


        };

        queue = Volley.newRequestQueue(appActivity.getApplicationContext());
        queue.add(postRequest);




    }








}
