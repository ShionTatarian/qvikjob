package fi.qvik.job.activity.main;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fi.qvik.job.R;
import fi.qvik.job.activity.data.JobModel;
import fi.qvik.job.activity.main.JobAdapter.JobViewHolder;

/**
 * Created by Tommy on 23/02/16.
 */
public class JobAdapter extends RecyclerView.Adapter<JobViewHolder> {

    private final List<JobModel> list = new ArrayList<>();

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JobViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_row, parent, false));
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        JobModel job = list.get(position);
        holder.title.setText(job.getTitle());
        holder.description.setText(job.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(JobModel job) {
        list.add(job);
    }

    public void clear() {
        list.clear();
    }

    protected static class JobViewHolder extends ViewHolder {

        private TextView title;
        private TextView description;

        public JobViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.job_row_title);
            description = (TextView) v.findViewById(R.id.job_row_description);

        }
    }

}
