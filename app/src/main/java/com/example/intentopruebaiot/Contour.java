package com.example.intentopruebaiot;

import android.graphics.Paint;
import android.text.TextPaint;
import android.widget.TextView;

public class Contour {

    public static void applyContour(TextView textView, float strokeWidth) {
        TextPaint paint = textView.getPaint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(0xFFFFFFFF);

        textView.invalidate();
    }
}
