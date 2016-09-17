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

public class FirstActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                new ActSwitchAnimTool(FirstActivity.this).setAnimType(0)
                        .target(fab)
                        .setShrinkBack(true)
                        .setmColorStart(Color.parseColor("#FF5777"))
                        .setmColorEnd(Color.parseColor("#FF5777"))
                        .startActivity(intent, false)
                        .build();
            }
        });
    }

    @Override
    protected void onResume() {
        new ActSwitchAnimTool(FirstActivity.this)
                .setAnimType(1)
                .target(fab)
                .setmColorStart(Color.parseColor("#FF5777"))
                .setmColorEnd(Color.parseColor("#FF5777"))
                .build();
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
