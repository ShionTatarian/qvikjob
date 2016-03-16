package fi.qvik.job.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.qvik.job.BaseEvent;
import fi.qvik.job.R;
import fi.qvik.job.activity.BaseActivity;
import fi.qvik.job.data.JobModel;
import fi.qvik.job.fragment.BaseFragment;
import fi.qvik.job.web.WebService;
import fi.qvik.job.web.jobs.FetchJobs;
import io.realm.RealmResults;

/**
 * Created by Tommy on 16/03/16.
 */
public class JobListFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private JobAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private OnRefreshListener onRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            fetchJobs();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fetchJobs();
        return layout;
    }

    @Override
    protected void getLayoutReferences(ViewGroup v) {
        Context ctx = v.getContext();
        recyclerView = (RecyclerView) v.findViewById(R.id.job_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.job_list_recycler_swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(onRefreshListener);
    }


    @Override
    protected int getFragmentLayoutResoure() {
        return R.layout.job_list_fragment;
    }

    @Override
    protected void updateContent(@NonNull BaseActivity act, @Nullable Bundle data) {
        if (adapter == null) {
            adapter = new JobAdapter(act);
            recyclerView.setAdapter(adapter);
        }

        RealmResults<JobModel> jobs = act.getRealm().where(JobModel.class).findAll();
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
    public void onEvent(Message msg) {
        super.onEvent(msg);

        switch (BaseEvent.values()[msg.what]) {
            case JOB_FETCH_FAILED:
                refreshLayout.setRefreshing(false);
                break;
            case JOB_FETCH_SUCCESS:
                refreshLayout.setRefreshing(false);
                updateContent();
                break;
        }
    }

    private void fetchJobs() {
        WebService.getInstance().runRequest(new FetchJobs());
    }

}
