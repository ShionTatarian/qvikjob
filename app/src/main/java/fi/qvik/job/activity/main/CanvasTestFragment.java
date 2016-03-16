package fi.qvik.job.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import fi.qvik.job.R;
import fi.qvik.job.activity.BaseActivity;
import fi.qvik.job.view.TestDataView;

/**
 * Created by Tommy on 16/03/16.
 */
public class CanvasTestFragment extends fi.qvik.job.fragment.BaseFragment {

    public static final String TEST_DATA_KEY = "testData";

    TestDataView testView;

    @Override
    protected void getLayoutReferences(ViewGroup v) {
        testView = (TestDataView) v.findViewById(R.id.canvas_test_view);
    }

    @Override
    protected int getFragmentLayoutResoure() {
        return R.layout.canvas_test_fragment;
    }

    @Override
    protected void updateContent(@NonNull BaseActivity act, @Nullable Bundle data) {
        if (data != null) {
            testView.setData(data.getIntArray(TEST_DATA_KEY));
        }


    }
}
