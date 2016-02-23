package fi.qvik.job.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import fi.qvik.job.BaseEvent;
import fi.qvik.job.R;
import fi.qvik.job.activity.main.JobAdapter;
import fi.qvik.job.data.JobModel;
import fi.qvik.job.web.WebService;
import fi.qvik.job.web.jobs.FetchJobs;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private JobAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        eventBus.register(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JobAdapter(this);
        recyclerView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_recycler_swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(onRefreshListener);

        fetchJobs();

        updateContent();
    }

    private OnRefreshListener onRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            fetchJobs();
        }
    };

    @Override
    public void onEvent(Message msg) {
        super.onEvent(msg);

        switch (BaseEvent.values()[msg.what]) {
            case JOB_FETCH_FAILED:
                Toast.makeText(this, "Error fetching jobs", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
                break;
            case JOB_FETCH_SUCCESS:
                refreshLayout.setRefreshing(false);
                updateContent();
                break;
        }
    }

    private void updateContent() {
        RealmResults<JobModel> jobs = realm.where(JobModel.class).findAll();
        // note as we put these Realm objects into adapter we can not close the realm
        // so activity should have a realm open for its lifecycle
        // BaseActivity handles realm lifecycle

        adapter.clear();
        for (JobModel job : jobs) {
            adapter.add(job);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fetchJobs() {
        WebService.getInstance().runRequest(new FetchJobs());
    }

}
