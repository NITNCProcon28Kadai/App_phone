package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by kaito on 2017/09/04.
 */

public class MethodSelect extends Activity {
    Setting set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.method_select);

        Intent i = this.getIntent();
        set = (Setting)i.getSerializableExtra("Course");
    }

    void interval_onClick(View view){
        set.method="interval";
        Intent interval = new Intent(this,com.nnct.procon.ghostrunner.SpeedSelect.class);
        interval.putExtra("Method",set);
        startActivity(interval);
        MethodSelect.this.finish();
    }

    void pace_onClick(View view){
        set.method = "paceRun";
        Intent pace = new Intent(this,com.nnct.procon.ghostrunner.SpeedSelect.class);
        pace.putExtra("Method",set);
        startActivity(pace);
        MethodSelect.this.finish();
    }
}
