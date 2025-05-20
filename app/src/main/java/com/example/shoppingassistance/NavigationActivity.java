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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        drawingView = findViewById(R.id.drawingView);


        String location = getIntent().getStringExtra("location");
        assert location != null;
        List<float[]> pathPoints = getPathToLocation(location);

        drawingView.setPath(pathPoints);

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