package fi.qvik.job.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;

import fi.qvik.job.R;
import fi.qvik.job.util.BaseValues;

/**
 * Created by Tommy on 16/03/16.
 */
public class TestDataView extends View {

    private int[] array;
    private Path path = null;
    private Paint paint = new Paint();

    public TestDataView(Context context) {
        super(context);
        init();
    }

    public TestDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public TestDataView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.margin_mini));
    }

    public void setData(int[] array) {
        path = null;
        this.array = array;

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (array != null && array.length > 0 && canvas != null && canvas.getHeight() > 0 && canvas.getWidth() > 0) {

            if (path == null) {
                path = new Path();
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                int size = width / array.length;
                int heightUnit = height / BaseValues.TEST_MAX;

                for (int i = 0; i < array.length - 1; i++) {
                    int current = array[i];
                    int next = array[i + 1];

                    path.moveTo(size * i, current * heightUnit);
                    path.lineTo(size * (i + 1), next * heightUnit);
                }
                path.close();
            }

            canvas.drawPath(path, paint);
            }

    }
}
