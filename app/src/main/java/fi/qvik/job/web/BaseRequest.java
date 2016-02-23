package fi.qvik.job.web;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * Created by Tommy on 23/02/16.
 */
public abstract class BaseRequest implements Callback {

    public Request makeRequest(Context ctx) {
        Request.Builder builder = new Request.Builder();

        // common things like headers can be set here to all requests

        setupRequest(ctx, builder);

        return builder.build();
    }

    /**
     * Setup request specific things here.
     *
     * @param ctx
     * @param builder
     */
    protected abstract void setupRequest(Context ctx, Builder builder);

    @Override
    public final void onFailure(Call call, IOException e) {
        handleError(-1, null);
    }

    @Override
    public final void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            handleSuccess(response.code(), response);
        } else {
            int statusCode = response.code();
            // common error handling can be done here:
            switch (statusCode) {
                case 403:
                    // TODO: should log out
                    break;
            }

            handleError(statusCode, response);
        }
    }

    protected abstract void handleError(int statusCode, @Nullable Response response);

    protected abstract void handleSuccess(int statusCode, @NonNull Response response);

}
