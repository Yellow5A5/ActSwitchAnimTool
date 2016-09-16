package yellow5a5.sample;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import yellow5a5.actswitchanimtool.ActSwitchAnimTool;

public class SecondActivity extends AppCompatActivity {

    private TextView mTextView;
    private FloatingActionButton mFloatingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mTextView = (TextView) findViewById(R.id.text);
        mFloatingBtn = (FloatingActionButton) findViewById(R.id.float_btn);
        new ActSwitchAnimTool(this)
                .receiveIntent(getIntent())
                .setAnimType(1)
                .setmColorEnd(Color.parseColor("#FF5777"))
                .target(mFloatingBtn)
                .build();
    }
}
