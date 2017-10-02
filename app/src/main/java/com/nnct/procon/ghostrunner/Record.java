package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kaito on 2017/09/30.
 */

public class Record extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
    }
    @Override
    protected void onStart(){
        super.onStart();
        TextView allDist = (TextView)findViewById(R.id.distView),aveDist = (TextView)findViewById(R.id.aveDist);
        String line,date = null;
        BufferedReader allReader = null,aveReader = null;
        double dist = 0,count = 0;
        //総距離
        try {
            allReader = new BufferedReader(
                    new InputStreamReader(openFileInput("allDistance.dat")));
            while((line = allReader.readLine()) != null){
                dist += Double.parseDouble(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(allReader != null){
                    allReader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        allDist.setText(Double.toString(dist) + "km");
        //平均距離
        try{
            aveReader = new BufferedReader(
                    new InputStreamReader(openFileInput("dateLog.dat")));
            while((line = aveReader.readLine()) != null){
                if(date != null){
                    if(date != line){count++;}
                }
                date = line;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(aveReader != null){
                    aveReader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        aveDist.setText(Double.toString(dist/count) + "km");
    }

    void medal_onClick(){
        Intent intent = new Intent(this,com.nnct.procon.ghostrunner.MedalList.class);
        startActivity(intent);
    }

}
