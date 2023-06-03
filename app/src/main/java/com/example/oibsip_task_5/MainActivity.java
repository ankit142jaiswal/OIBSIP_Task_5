package com.example.oibsip_task_5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.text.UStringsKt;

public class MainActivity extends AppCompatActivity {
    
    TextView timerText;
    Button stopStartButton;
    
    Timer timer ;
    TimerTask timerTask;
    Double time = 0.0;
    
    boolean timerStarted = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        timerText = (TextView) findViewById(R.id.timerText);
        stopStartButton = (Button) findViewById(R.id.startstopButton);
        
        timer =new Timer();
    }
    public void resetTime(View view){
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want to reset the timer?");
        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(timerTask != null){
                    timerTask.cancel();
                    extracted("START",R.color.green);
                    time = 0.0;
                    timerStarted=false;
                    timerText.setText(formatTime(0,0,0));
                }
            }
        });
        resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        resetAlert.show();
          
    }
    public void startstopTime(View view){
        
        if (timerStarted == false){
            timerStarted = true;
            extracted("STOP",R.color.red);
            
            startTimer();
            
        }
        else{
            timerStarted=false;
            extracted("START",R.color.green);

            timerTask.cancel();
        }
        
    }

    private void extracted(String start ,int color) {
        stopStartButton.setText(start);
        stopStartButton.setTextColor(ContextCompat.getColor(this,color));
    }

    private void startTimer() {
        timerTask  = new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(gettimerText());
                        
                    }
                });
               
            }
            
        };
        timer.scheduleAtFixedRate(timerTask,0,1);
    }
    
    private String gettimerText(){
        int rounded =(int)Math.round(time);
        int seconds=((rounded %86400)%3600)%60;
        int minutes=((rounded %86400)%3600)/60;
        int hours=((rounded %86400)/3600);
        return formatTime(seconds, minutes, hours);


    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }
}