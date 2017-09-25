package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * Created by kaito on 2017/09/03.
 */

public class SettingConfirm extends Activity {
    private Setting set;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_confirm);

        Intent i = this.getIntent();
        set = (Setting)i.getSerializableExtra("Speed");

        RelativeLayout relative = new RelativeLayout(this);

        ImageButton modeBtn = (ImageButton)findViewById(R.id.mode);
        ImageButton methodBtn = (ImageButton)findViewById(R.id.method);
        ImageButton speedBtn = (ImageButton)findViewById(R.id.speed);

        //レイアウトのコンバート
        relative.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        relative.setBackgroundResource(R.drawable.background);

        setContentView(relative);

        //設定に応じた画像の割り当て
        if(set.mode.equals("vs")){
            modeBtn.setImageResource(R.drawable.mode_vs);
        }else{
            modeBtn.setImageResource(R.drawable.pace_keeper);
        }

        if(set.method.equals("interval")){
            methodBtn.setImageResource(R.drawable.exercize_interval);
        }else{
            methodBtn.setImageResource(R.drawable.exercize_pace);
        }

        switch (set.pace){
            case "regular":
                speedBtn.setImageResource(R.drawable.normal);
                break;
            case "fast":
                speedBtn.setImageResource(R.drawable.fast);
                break;
            case "slow":
                speedBtn.setImageResource(R.drawable.slow);
                break;
        }

        //画像の適用
        relative.addView(modeBtn);
        relative.addView(methodBtn);
        relative.addView(speedBtn);


    }




}
