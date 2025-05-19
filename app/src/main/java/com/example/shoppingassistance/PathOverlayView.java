package com.example.shoppingassistance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

public class PathOverlayView extends androidx.appcompat.widget.AppCompatImageView {
    private String targetLocation;
    private Paint pathPaint;

    public PathOverlayView(Context context) {
        super(context);
        init();
    }

    public PathOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        pathPaint = new Paint();
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStrokeWidth(15f);
        pathPaint.setStyle(Paint.Style.STROKE);
    }

    public void setTargetLocation(String location) {
        this.targetLocation = location;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (targetLocation == null) return;

        Path path = new Path();
        // Gán tọa độ (hardcoded tạm thời) theo từng khu
        switch (targetLocation.toUpperCase()) {
            case "SỮA":
                path.moveTo(950, 1900);  // ví dụ: vị trí xuất phát
                path.lineTo(950, 1200);  // vị trí khu sữa
                break;
            case "KHU KẸO":
                path.moveTo(950, 1900);
                path.lineTo(600, 1200);
                break;
            // Thêm các khu khác...
        }
        canvas.drawPath(path, pathPaint);
    }
}
