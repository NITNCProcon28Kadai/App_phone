package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kaito on 2017/09/30.
 */

public class EditCourse extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_course);
    }
    @Override
    protected void onStart(){
        super.onStart();
        TextView allDist = (TextView)findViewById(R.id.distView);
        String line;
        BufferedReader allReader = null;
        double dist = 0;
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
    }

}
