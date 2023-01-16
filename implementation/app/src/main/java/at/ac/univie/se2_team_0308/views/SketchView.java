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

public class SketchView extends View {
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

        // check the action
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // start new line
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                // draw a line between the last two points
                path.lineTo(x, y);
                break;
            default:
                return false;
        }
        // to redraw
        postInvalidate();
        return true;
    }

}




