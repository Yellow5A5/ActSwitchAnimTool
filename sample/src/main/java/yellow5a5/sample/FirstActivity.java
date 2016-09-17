package yellow5a5.sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import yellow5a5.actswitchanimtool.ActSwitchAnimTool;
import yellow5a5.sample.ShareDemo.ShareContainer;

public class FirstActivity extends AppCompatActivity {

    private FloatingActionButton mActSwitchDemoBtn;
    private FloatingActionButton mShareViewDemoBtn;

    private ActSwitchAnimTool mFirstDemoActSwitchAnimTool;

    private ShareContainer mShareContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActSwitchDemoBtn = (FloatingActionButton) findViewById(R.id.fab);

        initTool();

        //DEMO FIRST.
        mActSwitchDemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mFirstDemoActSwitchAnimTool.setAnimType(0).build();
            }
        });

        //DEMO SECOND.
        mShareViewDemoBtn = (FloatingActionButton) findViewById(R.id.share_float_btn);
        final ActSwitchAnimTool shareDemoTool = new ActSwitchAnimTool(FirstActivity.this)
                .target(mShareViewDemoBtn)
                .setmColorStart(Color.parseColor("#33D1FF"))
                .setmColorEnd(Color.parseColor("#33D1FF"));
        mShareContainer = new ShareContainer(FirstActivity.this);
        mShareContainer.setIShareCallback(new ShareContainer.IShareCallback() {
            @Override
            public void onCancel() {
                mShareContainer.hideShareBtn();
                shareDemoTool.setAnimType(ActSwitchAnimTool.MODE_SHRINK)
                        .removeContainerView(mShareContainer)
                        .build();
            }
        });
        mShareViewDemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDemoTool.setAnimType(ActSwitchAnimTool.MODE_SPREAD)
                        .addContainerView(mShareContainer, new ActSwitchAnimTool.SwitchAnimCallback() {
                            @Override
                            public void onAnimationStart() {

                            }

                            @Override
                            public void onAnimationEnd() {
                                mShareContainer.showShareBtn();
                            }

                            @Override
                            public void onAnimationUpdate(int progress) {

                            }
                        }).
                        build();
            }
        });
    }

    private void initTool() {
        Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
        mFirstDemoActSwitchAnimTool = new ActSwitchAnimTool(FirstActivity.this).setAnimType(0)
                .target(mActSwitchDemoBtn)
                .setShrinkBack(true)
                .setmColorStart(Color.parseColor("#FF5777"))
                .setmColorEnd(Color.parseColor("#FF5777"))
                .startActivity(intent, false);
    }

    @Override
    protected void onResume() {
        if (mFirstDemoActSwitchAnimTool == null)
            return;
        if (mFirstDemoActSwitchAnimTool.isWaitingResume()) {
            mFirstDemoActSwitchAnimTool.setAnimType(1)
                    .build();
        }

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
