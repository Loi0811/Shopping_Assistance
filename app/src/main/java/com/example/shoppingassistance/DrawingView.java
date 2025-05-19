package com.example.shoppingassistance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private Paint pathPaint;
    private Paint trianglePaint;

    private List<float[]> pathPoints = new ArrayList<>();
    private float[] currentPosition = null;
    private float currentAngle = 0;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Vẽ đường path (đỏ)
        pathPaint = new Paint();
        pathPaint.setColor(Color.RED);
        pathPaint.setStrokeWidth(20f);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setAntiAlias(true);

        // Vẽ tam giác (đại diện người dùng)
        trianglePaint = new Paint();
        trianglePaint.setColor(Color.rgb(255, 165, 0)); // Cam
        trianglePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        trianglePaint.setAntiAlias(true);
    }

    public void setPath(List<float[]> points) {
        this.pathPoints = points;
        invalidate();
    }

    public void setCurrentPosition(float[] pos) {
        this.currentPosition = pos;
        invalidate();
    }

    public void setAngle(float angle) {
        this.currentAngle = angle;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Vẽ đường đi (nếu có)
        if (pathPoints != null && pathPoints.size() > 1) {
            for (int i = 0; i < pathPoints.size() - 1; i++) {
                float[] p1 = pathPoints.get(i);
                float[] p2 = pathPoints.get(i + 1);
                canvas.drawLine(p1[0], p1[1], p2[0], p2[1], pathPaint);
            }
        }

        // Vẽ biểu tượng người dùng (tam giác)
        if (currentPosition != null) {
            drawRotatedTriangle(canvas, currentPosition[0], currentPosition[1], currentAngle);
        }
    }

    private void drawRotatedTriangle(Canvas canvas, float cx, float cy, float angle) {
        float size = 80f;
        float height = (float)(Math.sqrt(3) / 2 * size);

        Path triangle = new Path();
        triangle.moveTo(0, -height / 2);       // Đỉnh
        triangle.lineTo(-size / 2, height / 2); // Góc trái
        triangle.lineTo(size / 2, height / 2);  // Góc phải
        triangle.close();


        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate((float) Math.toDegrees(angle));
        canvas.drawPath(triangle, trianglePaint);
        canvas.restore();
    }

    public void moveBy(float dx, float dy) {
        if (currentPosition == null) return;

        float newX = currentPosition[0] + dx;
        float newY = currentPosition[1] + dy;

        currentPosition[0] = newX;
        currentPosition[1] = newY;

        if (dx != 0 || dy != 0) {
            currentAngle = (float) Math.atan2(dy, dx) + (float) Math.toRadians(90); // cộng 90 độ dưới dạng radian
        }

        invalidate();
    }

}
