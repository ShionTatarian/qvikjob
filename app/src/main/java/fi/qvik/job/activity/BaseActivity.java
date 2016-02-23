package fi.qvik.job.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import fi.qvik.job.BaseEvent;
import fi.qvik.job.R;
import io.realm.Realm;

/**
 * Created by Tommy on 23/02/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final String TAG = "BaseActivity";

    protected Toolbar toolbar;
    protected Realm realm;
    protected final EventBus eventBus = EventBus.getDefault();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.qvik_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar abc = getSupportActionBar();
            if (abc != null) {
                abc.setDisplayHomeAsUpEnabled(true);
                abc.setDisplayShowTitleEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Simple way to handle up button click
                onUpClick();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Message msg) {
        Log.d(TAG, "onEvent[" + BaseEvent.values()[msg.what] + "]");
    }

    protected void onUpClick() {
        onBackPressed();
    }
}
