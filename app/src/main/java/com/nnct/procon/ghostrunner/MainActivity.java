package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        if(e.getAction() == MotionEvent.ACTION_DOWN){}
        if(e.getAction() == MotionEvent.ACTION_UP){
            Intent intent = new Intent(this,com.nnct.procon.ghostrunner.FirstMenu.class);
            startActivity(intent);
        }
        return true;
    }
}
