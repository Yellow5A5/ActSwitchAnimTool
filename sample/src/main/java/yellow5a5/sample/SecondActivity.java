package yellow5a5.sample;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
                .target(mFloatingBtn)
                .build();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new ActSwitchAnimTool(this)
                    .setAnimType(0)
                    .target(mFloatingBtn)
                    .setmColorStart(Color.parseColor("#FF5777"))
                    .setmColorEnd(Color.parseColor("#FF5777"))
                    .setCustomEndCallBack(new ActSwitchAnimTool.SwitchAnimCallback() {
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
