package fi.qvik.job.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import fi.qvik.job.BaseEvent;
import fi.qvik.job.activity.BaseActivity;

/**
 * Created by Tommy on 16/03/16.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    protected final EventBus eventBus = EventBus.getDefault();
    protected ViewGroup layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eventBus.register(this);
        layout = (ViewGroup) inflater.inflate(getFragmentLayoutResoure(), container, false);
        getLayoutReferences(layout);

        updateContent();
        return layout;
    }

    @Override
    public void onDestroyView() {
        layout = null;
        super.onDestroyView();
    }

    protected abstract void getLayoutReferences(ViewGroup v);

    @LayoutRes
    protected abstract int getFragmentLayoutResoure();

    /**
     * Always call this to update your fragment data.
     */
    protected final void updateContent() {
        BaseActivity act = (BaseActivity) getActivity();
        if (act == null || layout == null) {
            return;
        }
        Bundle data = getArguments();

        updateContent(act, data);
    }

    /**
     * NEVER CALL THIS METHOD!
     * <p>
     * Implement your layout update here!
     * Activity and layout is always non null so your view references should be ok.
     *
     * @param act
     * @param data
     */
    protected abstract void updateContent(@NonNull BaseActivity act, @Nullable Bundle data);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Message msg) {
        Log.d(TAG, "onEvent[" + BaseEvent.values()[msg.what] + "]");
    }

}
