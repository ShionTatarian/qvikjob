package fi.qvik.job.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fi.qvik.job.R;
import fi.qvik.job.activity.data.JobModel;
import fi.qvik.job.activity.main.JobAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final OkHttpClient client = new OkHttpClient();

    private RecyclerView recyclerView;
    private JobAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JobAdapter();
        recyclerView.setAdapter(adapter);


        fetchJobs();

        updateContent();
    }

    private void updateContent() {
        RealmResults<JobModel> jobs = realm.where(JobModel.class).findAll();
        // note as we put these Realm objects into adapter we can not close the realm
        // so activity should have a realm open for its lifecycle

        adapter.clear();
        for (JobModel job : jobs) {
            adapter.add(job);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void fetchJobs() {
        Request request = new Request.Builder()
                .url(getString(R.string.api_jobs_json))
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.w(TAG, "Error loading JSON");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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
                } catch (JSONException e) {
                    Log.e(TAG, "Error loading JSON", e);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateContent();
                    }
                });
            }
        });

    }

}
