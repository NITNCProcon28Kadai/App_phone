package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/**
 * Created by kaito on 2017/09/04.
 */

public class SpeedSelect extends Activity {
    Setting set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_select);

        Intent i = this.getIntent();
        set = (Setting)i.getSerializableExtra("Method");
    }

    void Regular_onClick(View view){
        set.pace = "regular";
        Intent reg = new Intent(this,com.nnct.procon.ghostrunner.SettingConfirm.class);
        reg.putExtra("Speed",set);
        startActivity(reg);
    }

    void Fast_onClick(View view){
        set.pace = "fast";
        Intent fast = new Intent(this,com.nnct.procon.ghostrunner.SettingConfirm.class);
        fast.putExtra("Speed",set);
        startActivity(fast);
    }

    void Slow_onClick(View view){
        set.pace = "slow";
        Intent slow = new Intent(this,com.nnct.procon.ghostrunner.SettingConfirm.class);
        slow.putExtra("Speed",set);
        startActivity(slow);
    }
}
