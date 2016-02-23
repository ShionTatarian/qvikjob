package fi.qvik.job.web;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * Created by Tommy on 23/02/16.
 */
public class WebService {

    private static WebService instance;

    private final OkHttpClient client = new OkHttpClient();
    private final Context context;

    private WebService(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (instance != null) {
            throw new IllegalStateException("WebService already initiated!");
        }
        instance = new WebService(context);
    }

    public static WebService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("WebService is not initialized!");
        }

        return instance;
    }

    public void runRequest(BaseRequest request) {
        client.newCall(request.makeRequest(context)).enqueue(request);
    }

}
