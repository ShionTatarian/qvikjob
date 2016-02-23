package fi.qvik.job;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final OkHttpClient client = new OkHttpClient();

    private TextView helloText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        helloText = (TextView) findViewById(R.id.main_hello_text);

        fetchJobs();
    }

    private void fetchJobs() {
        Request request = new Request.Builder()
                .url(getString(R.string.api_jobs_json))
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                printText(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    // Read response body and make it into JSONObject
                    JSONObject json = new JSONObject(response.body().string());
                    printText(json.toString(2));
                } catch (JSONException e) {
                    Log.w(TAG, "Error loading JSON", e);
                }
            }
        });

    }

    private void printText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                helloText.setText(text);
            }
        });
    }

}
