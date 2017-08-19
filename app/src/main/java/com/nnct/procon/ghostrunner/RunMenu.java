package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by kaito on 2017/08/17.
 */

public class RunMenu extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_menu);
    }

    void interval_onClick(View v){
        Intent i = new Intent(this,com.nnct.procon.ghostrunner.IntervalCourseSelect.class);
        startActivity(i);
    }

    void pace_onClick(View v){
        Intent i = new Intent(this,com.nnct.procon.ghostrunner.PaceCourseSelect.class);
        startActivity(i);
    }
}
