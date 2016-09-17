package yellow5a5.sample;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import yellow5a5.actswitchanimtool.ActSwitchAnimTool;
import yellow5a5.sample.ShareDemo.ShareContainer;

public class SecondActivity extends AppCompatActivity {

    private TextView mDemoTv;
    private FloatingActionButton mDemoFloatingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mDemoTv = (TextView) findViewById(R.id.demo_tv);
        mDemoFloatingBtn = (FloatingActionButton) findViewById(R.id.demo_float_btn);
        new ActSwitchAnimTool(this)
                .receiveIntent(getIntent())
                .setAnimType(ActSwitchAnimTool.MODE_SHRINK)
                .target(mDemoFloatingBtn)
                .build();

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new ActSwitchAnimTool(this)
                    .setAnimType(ActSwitchAnimTool.MODE_SPREAD)
                    .target(mDemoFloatingBtn)
                    .setmColorStart(Color.parseColor("#FF5777"))
                    .setmColorEnd(Color.parseColor("#FF5777"))
                    .setCustomEndCallBack(new ActSwitchAnimTool.SwitchAnimCallback() {
                        @Override
                        public void onAnimationStart() {
                        }

                        @Override
                        public void onAnimationEnd() {
                            finish();
                        }

                        @Override
                        public void onAnimationUpdate(int progress) {

                        }
                    })
                    .build();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
