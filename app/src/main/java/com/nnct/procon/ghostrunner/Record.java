package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by kaito on 2017/09/30.
 */

public class Record extends Activity {
    BufferedReader reader;
    String line;
    int count = 0,win = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
    }
    @Override
    protected void onStart(){
        super.onStart();
        TextView allDist = (TextView)findViewById(R.id.distView),aveDist = (TextView)findViewById(R.id.aveDist),winPer = (TextView)findViewById(R.id.winpercent);
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
        allDist.setText(String.format("%.3fkm",dist));
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
        aveDist.setText(String.format("%.3fkm",dist / count));
        //勝率
        try{
            reader = new BufferedReader(new InputStreamReader(openFileInput("result.dat")));
            while((line = reader.readLine()) != null){
                if(line == "win"){
                    win++;
                }
                count++;
            }
            winPer.setText(String.format("%d%",win/count));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    void medal_onClick(){
        Intent intent = new Intent(this,com.nnct.procon.ghostrunner.MedalList.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this,com.nnct.procon.ghostrunner.FirstMenu.class);
            startActivity(intent);
            Record.this.finish();
            return true;
        }
        return false;
    }

    void medal_onClick(View v){
        Intent intent = new Intent(this,com.nnct.procon.ghostrunner.MedalList.class);
        startActivity(intent);
    }

}
