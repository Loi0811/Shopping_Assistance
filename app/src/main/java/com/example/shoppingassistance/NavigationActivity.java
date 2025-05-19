package com.example.shoppingassistance;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {

    private DrawingView drawingView;
    private float[] currentPosition = {1400f, 1300f};
    private float currentAngle = 0f;// Vị trí ban đầu
    private float moveStep = 20f;
    private boolean isUpPressed = false, isDownPressed = false, isLeftPressed = false, isRightPressed = false;
    private Handler handler = new Handler();

    // Runnable cho từng hướng
    private final Runnable moveUpRunnable = new Runnable() {
        @Override public void run() {
            if (isUpPressed) {
                drawingView.moveBy(0, -20); // lên
                handler.postDelayed(this, 100);
            }
        }
    };

    private final Runnable moveDownRunnable = new Runnable() {
        @Override public void run() {
            if (isDownPressed) {
                drawingView.moveBy(0, 20); // xuống
                handler.postDelayed(this, 100);
            }
        }
    };

    private final Runnable moveLeftRunnable = new Runnable() {
        @Override public void run() {
            if (isLeftPressed) {
                drawingView.moveBy(-20, 0); // trái
                handler.postDelayed(this, 100);
            }
        }
    };

    private final Runnable moveRightRunnable = new Runnable() {
        @Override public void run() {
            if (isRightPressed) {
                drawingView.moveBy(20, 0); // phải
                handler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        drawingView = findViewById(R.id.drawingView);
        drawingView.setCurrentPosition(currentPosition);
        drawingView.setAngle(currentAngle);

        Button btnUp = findViewById(R.id.btn_up);
        Button btnDown = findViewById(R.id.btn_down);
        Button btnLeft = findViewById(R.id.btn_left);
        Button btnRight = findViewById(R.id.btn_right);

        btnUp.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isUpPressed = true;
                    handler.post(moveUpRunnable);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isUpPressed = false;
                    handler.removeCallbacks(moveUpRunnable);
                    v.performClick(); // ⚠️ KHÔNG QUÊN
                    return true;
            }
            return false;
        });

        btnDown.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isDownPressed = true;
                    handler.post(moveDownRunnable);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isDownPressed = false;
                    handler.removeCallbacks(moveDownRunnable);
                    v.performClick();
                    return true;
            }
            return false;
        });

        btnLeft.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isLeftPressed = true;
                    handler.post(moveLeftRunnable);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isLeftPressed = false;
                    handler.removeCallbacks(moveLeftRunnable);
                    v.performClick();
                    return true;
            }
            return false;
        });

        btnRight.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isRightPressed = true;
                    handler.post(moveRightRunnable);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isRightPressed = false;
                    handler.removeCallbacks(moveRightRunnable);
                    v.performClick();
                    return true;
            }
            return false;
        });



        String location = getIntent().getStringExtra("location");
        assert location != null;
        List<float[]> pathPoints = getPathToLocation(location);

        drawingView.setPath(pathPoints);

    }

    private View.OnTouchListener getMoveTouchListener(int dx, int dy) {
        return new View.OnTouchListener() {
            private boolean isMoving = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isMoving = true;
                        startMoving(dx, dy);
                        return true;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isMoving = false;
                        return true;
                }
                return false;
            }

            private void startMoving(int dx, int dy) {
                new Thread(() -> {
                    while (isMoving) {
                        runOnUiThread(() -> move(dx, dy));
                        try {
                            Thread.sleep(50); // tốc độ di chuyển (20 lần/s)
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };
    }

    private void move(int dx, int dy) {
        float step = 10f;

        currentPosition[0] += dx * step;
        currentPosition[1] += dy * step;

        if (dx != 0 || dy != 0) {
            currentAngle = (float) Math.atan2(dy, dx);
        }

        drawingView.setCurrentPosition(currentPosition);
        drawingView.setAngle(currentAngle);
    }

    // Hàm tính đường đi từ góc phải dưới (tam giác) đến các khu vực
    public List<float[]> getPathToLocation(String location) {
        List<float[]> path = new ArrayList<>();

        switch (location.toUpperCase()) {
            case "MI AN LIEN":
                path.add(new float[]{1400f, 1300f});
                path.add(new float[]{1400f, 700f});
                path.add(new float[]{225f, 700f});
                path.add(new float[]{225f, 400f});
                break;

            case "NUOC NGOT":
                path.add(new float[]{1400f, 1300f});
                path.add(new float[]{1400f, 700f});
                path.add(new float[]{670f, 700f});
                path.add(new float[]{670f, 400f});
                break;

            case "SUA":
                path.add(new float[]{1400f, 1300f});
                path.add(new float[]{1400f, 700f});
                path.add(new float[]{1115f, 700f});
                path.add(new float[]{1115f, 400f});
                break;

            case "GIA VI":
                path.add(new float[]{1400f, 1300f});
                path.add(new float[]{1400f, 700f});
                path.add(new float[]{225f, 700f});
                path.add(new float[]{225f, 1100f});
                break;

            case "KEO":
                path.add(new float[]{1400f, 1300f});
                path.add(new float[]{1400f, 700f});
                path.add(new float[]{670f, 700f});
                path.add(new float[]{670f, 1100f});
                break;

            case "TRAI CAY":
                path.add(new float[]{1400f, 1300f});
                path.add(new float[]{1400f, 700f});
                path.add(new float[]{1115f, 700f});
                path.add(new float[]{1115f, 1100f});
                break;

            default:
                // Không tìm thấy khu
                break;
        }

        return path;
    }

}