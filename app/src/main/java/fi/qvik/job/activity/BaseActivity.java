package fi.qvik.job.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import fi.qvik.job.R;
import io.realm.Realm;

/**
 * Created by Tommy on 23/02/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected Realm realm;

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

    protected void onUpClick() {
        onBackPressed();
    }
}
