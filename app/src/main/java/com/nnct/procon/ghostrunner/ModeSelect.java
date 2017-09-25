package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by kaito on 2017/09/03.
 */

public class ModeSelect extends Activity {
    Setting set = new Setting();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_select);
    }

    void vsGhost_onClick(View view){
        Intent vs = new Intent(this,com.nnct.procon.ghostrunner.SetCourse.class);
        set.mode = "vs";
        vs.putExtra("Mode",set);
        startActivity(vs);
    }

    void paceMaker_onClick(View view){
        Intent pace = new Intent(this,com.nnct.procon.ghostrunner.SetCourse.class);
        set.mode = "pace";
        pace.putExtra("Mode",set);
        startActivity(pace);
    }
}
