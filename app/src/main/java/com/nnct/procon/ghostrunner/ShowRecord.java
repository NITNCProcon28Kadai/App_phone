package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by kaito on 2017/09/12.
 */

public class ShowRecord extends Activity {
    Setting set;
    String line;
    BufferedReader reader;
    BufferedWriter writer;
    long best = 0,mres,sres;
    TextView distView,spView,balView;
    double aveSp;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_record);
        Intent intent = this.getIntent();
        set = (Setting)intent.getSerializableExtra("file");
        distView = (TextView)findViewById(R.id.viewDist);
        distView.setText(Double.toString(set.dist) + "km");
        aveSp = set.dist / set.time;
        spView = (TextView)findViewById(R.id.aveSpeed);
        spView.setText(Double.toString(aveSp));
        if(set.mode != "vs"){
            findViewById(R.id.textView7).setVisibility(View.INVISIBLE);
        }

        try{
            reader = new BufferedReader(new InputStreamReader(
                    openFileInput(set.courseFile + ".dat")));
            while((line = reader.readLine()) != null ){
                if(count != 0) {
                    if (Long.parseLong(line) > best) {
                        best = Long.parseLong(line);
                    }
                }
                count++;
            }
            mres = (set.time - best) * 1000 / 60;
            sres = Math.abs((set.time - best) * 1000 % 60);
            balView = (TextView)findViewById(R.id.balanceView);
            if(set.mode == "vs"){
                balView.setText(String.format("%d:%d",mres,sres));
            }else{
                balView.setVisibility(View.INVISIBLE);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(reader != null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            writer = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("result.dat",MODE_APPEND)));
            if(set.time > best){
                writer.write("win");
                writer.newLine();
            }else{
                writer.write("lose");
                writer.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    void returnHome_onClick(View view){
        Intent intent = new Intent(this,com.nnct.procon.ghostrunner.FirstMenu.class);
        startActivity(intent);
    }
}
