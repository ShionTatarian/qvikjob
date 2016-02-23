package fi.qvik.job.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JobAdapter(this);
        recyclerView.setAdapter(adapter);


        fetchJobs();

        updateContent();
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
