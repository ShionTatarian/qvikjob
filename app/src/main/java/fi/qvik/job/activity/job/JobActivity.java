package fi.qvik.job.activity.job;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fi.qvik.job.R;
import fi.qvik.job.activity.BaseActivity;
import fi.qvik.job.data.JobModel;
import fi.qvik.job.util.BaseValues;
import fi.qvik.job.util.BaseValues.IntentKey;

/**
 * Created by Tommy on 23/02/16.
 */
public class JobActivity extends BaseActivity {

    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView link;

    private JobModel job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_activity);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        String jobTitle = intent.getStringExtra(IntentKey.JOB_TITLE);
        job = realm.where(JobModel.class).equalTo("title", jobTitle).findFirst();


        setTitle(jobTitle);

        image = (ImageView) findViewById(R.id.job_activity_image);
        ViewCompat.setTransitionName(image, BaseValues.MAIN_IMAGE_TRANSITION_NAME);
        Picasso.with(image.getContext())
                .load(job.getImage())
                .into(image);

        title = (TextView) findViewById(R.id.job_activity_title);
        title.setText(job.getTitle());
        description = (TextView) findViewById(R.id.job_activity_description);
        description.setText(job.getDescription());
        link = (TextView) findViewById(R.id.job_activity_link);
        link.setText(job.getLink());

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.job_activity_collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
        // If expanded title color is transparent the title comes visible only after the image has collapsed

        updateContent();
    }

    private void updateContent() {

    }

    public void onLinkClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(job.getLink()));
        startActivity(intent);
    }

}
