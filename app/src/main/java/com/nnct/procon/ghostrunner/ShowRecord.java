package com.nnct.procon.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;

/**
 * Created by kaito on 2017/09/12.
 */

public class ShowRecord extends Activity {
    Setting set;
    TextView distView,spView,balView;
    double aveSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_record);
        Intent intent = this.getIntent();
        set = (Setting)intent.getSerializableExtra("file");
        distView = (TextView)findViewById(R.id.viewDist);
        distView.setText(Double.toString(set.dist));
        aveSp = set.dist / set.time;
        spView = (TextView)findViewById(R.id.aveSpeed);
        spView.setText(Double.toString(aveSp));

    }

    void returnHome_onClick(View view){
        Intent intent = new Intent(this,com.nnct.procon.ghostrunner.FirstMenu.class);
        startActivity(intent);
    }
}
