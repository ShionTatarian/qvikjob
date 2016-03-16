package fi.qvik.job.activity.main;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import fi.qvik.job.R;
import fi.qvik.job.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private MainFragmentAdapter adapter;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        eventBus.register(this);

        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        adapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabs = (TabLayout) findViewById(R.id.main_tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        updateContent();
    }


    @Override
    public void onEvent(Message msg) {
        super.onEvent(msg);

    }

    private void updateContent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
