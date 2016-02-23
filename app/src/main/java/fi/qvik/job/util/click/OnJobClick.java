package fi.qvik.job.util.click;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import fi.qvik.job.activity.BaseActivity;
import fi.qvik.job.activity.job.JobActivity;
import fi.qvik.job.util.BaseValues;
import fi.qvik.job.util.BaseValues.IntentKey;

/**
 * Created by Tommy on 23/02/16.
 */
public class OnJobClick implements OnClickListener {

    private final WeakReference<ImageView> weakImage;
    private final WeakReference<BaseActivity> weakActivity;
    private final String jobTitle;

    public OnJobClick(String jobTitle, BaseActivity act, ImageView imageView) {
        this.jobTitle = jobTitle;
        weakActivity = new WeakReference<>(act);
        weakImage = new WeakReference<>(imageView);
    }

    @Override
    public void onClick(View v) {
        BaseActivity act = weakActivity.get();
        ImageView image = weakImage.get();
        if (act == null || image == null) {
            return;
        }
        Intent intent = new Intent(act, JobActivity.class);
        intent.putExtra(IntentKey.JOB_TITLE, jobTitle);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, image, BaseValues.MAIN_IMAGE_TRANSITION_NAME);
        ActivityCompat.startActivity(act, intent, options.toBundle());
    }
}
