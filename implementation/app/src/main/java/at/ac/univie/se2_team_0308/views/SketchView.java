package at.ac.univie.se2_team_0308.views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is used for drawing in SketchFragment.
 * It overrides onTouch event and uses it to draw a path
 */
public class SketchView extends View {
    public static final String TAG = "SketchView";

    public SketchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        initPaint();
        setBackgroundColor(Color.parseColor("#f0f0f0"));
        setDrawingCacheEnabled(true);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    private Paint paint;
    private Path path = new Path();

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /**
                 * Start a new line
                 */
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * Draw a line between the last 2 points
                 */
                path.lineTo(x, y);
                break;
            default:
                return false;
        }
        /**
         * This call is needed to redraw and update the view
         */
        postInvalidate();
        return true;
    }

}




