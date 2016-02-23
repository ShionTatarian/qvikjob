package fi.qvik.job.web.jobs;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fi.qvik.job.BaseEvent;
import fi.qvik.job.R;
import fi.qvik.job.data.JobModel;
import fi.qvik.job.web.BaseRequest;
import io.realm.Realm;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * Created by Tommy on 23/02/16.
 */
public class FetchJobs extends BaseRequest {

    private static final String TAG = "FetchJobs";

    @Override
    protected void setupRequest(Context ctx, Builder builder) {
        builder.url(ctx.getString(R.string.api_jobs_json));
    }

    @Override
    protected void handleError(int statusCode, @Nullable Response response) {
        Log.e(TAG, "[" + statusCode + "]Error fetching jobs");

        Message msg = Message.obtain();
        msg.what = BaseEvent.JOB_FETCH_SUCCESS.ordinal();
        msg.arg1 = statusCode;
        eventBus.post(msg);
    }

    @Override
    protected void handleSuccess(int statusCode, @NonNull Response response) {
        try {
            // Read response body and make it into JSONObject
            JSONObject json = new JSONObject(response.body().string());
            JSONArray jobs = json.optJSONArray("jobs");

            Realm threadRealm = Realm.getDefaultInstance();
            threadRealm.beginTransaction();

            for (int i = 0; i < jobs.length(); i++) {
                JSONObject job = jobs.optJSONObject(i);
                JobModel j = new JobModel();
                j.setTitle(job.optString("title"));
                j.setDescription(job.optString("description"));
                j.setLink(job.optString("link"));
                j.setImage(job.optString("image"));

                Log.d(TAG, "Job: " + j.getTitle() + " saved");
                threadRealm.copyToRealmOrUpdate(j);
            }

            threadRealm.commitTransaction();
            threadRealm.close();
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error loading JSON", e);
        }

        Message msg = Message.obtain();
        msg.what = BaseEvent.JOB_FETCH_SUCCESS.ordinal();
        msg.arg1 = statusCode;
        eventBus.post(msg);
    }
}
