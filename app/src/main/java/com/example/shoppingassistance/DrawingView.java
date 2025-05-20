package com.example.shoppingassistance;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private Paint pathPaint, trianglePaint;
    private List<float[]> pathPoints = new ArrayList<>();
    private float[] currentPosition = null;
    private float currentAngle = 0f;
    private int animationIndex = 0;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        pathPaint = new Paint();
        pathPaint.setColor(Color.RED);
        pathPaint.setStrokeWidth(20f);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setAntiAlias(true);

        trianglePaint = new Paint();
        trianglePaint.setColor(Color.rgb(255, 165, 0));
        trianglePaint.setStyle(Paint.Style.FILL);
        trianglePaint.setAntiAlias(true);
    }

    public void setPath(List<float[]> pathPoints) {
        if (pathPoints == null || pathPoints.size() < 2) return;

        this.pathPoints = pathPoints;
        currentPosition = pathPoints.get(0);
        currentAngle = 0f;
        invalidate();
        startSmoothAnimation();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Vẽ đường đi
        for (int i = 0; i < pathPoints.size() - 1; i++) {
            float[] p1 = pathPoints.get(i);
            float[] p2 = pathPoints.get(i + 1);
            canvas.drawLine(p1[0], p1[1], p2[0], p2[1], pathPaint);
        }

        // Vẽ tam giác xoay theo hướng di chuyển
        if (currentPosition != null) {
            drawRotatedTriangle(canvas, currentPosition[0], currentPosition[1], currentAngle);
        }
    }

    private void drawRotatedTriangle(Canvas canvas, float cx, float cy, float angle) {
        float size = 40f;
        Path triangle = new Path();
        triangle.moveTo(0, -size);        // Đỉnh hướng lên
        triangle.lineTo(-size, size);     // đáy trái
        triangle.lineTo(size, size);      // đáy phải
        triangle.close();

        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate((float) Math.toDegrees(angle) + 90); // cộng thêm 90 độ để đỉnh hướng theo vector
        canvas.drawPath(triangle, trianglePaint);
        canvas.restore();
    }


    private void startSmoothAnimation() {
        if (pathPoints.size() < 2) return;

        animateSegment(pathPoints.get(0), pathPoints.get(1), 0);
    }

    private void animateSegment(float[] start, float[] end, int currentIndex) {
        float distance = calculateDistance(start, end);
        float speed = 0.5f; // pixels per millisecond (tùy bạn chỉnh, ví dụ 0.5 → 500 pixels mất 1s)
        long duration = (long) (distance / speed); // Tính thời gian dựa trên khoảng cách

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            float t = (float) animation.getAnimatedValue();
            float x = start[0] + t * (end[0] - start[0]);
            float y = start[1] + t * (end[1] - start[1]);
            currentPosition = new float[]{x, y};

            currentAngle = (float) Math.atan2(end[1] - start[1], end[0] - start[0]);
            invalidate();
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (currentIndex + 2 < pathPoints.size()) {
                    float[] nextStart = pathPoints.get(currentIndex + 1);
                    float[] nextEnd = pathPoints.get(currentIndex + 2);
                    animateSegment(nextStart, nextEnd, currentIndex + 1);
                }
            }
        });

        animator.start();
    }


    private float calculateDistance(float[] start, float[] end) {
        float dx = end[0] - start[0];
        float dy = end[1] - start[1];
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

}

