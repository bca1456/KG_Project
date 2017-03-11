package bca.kg_project.drawMethods.line;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Евгений on 10.03.2017.
 */

public class ParametricalDrawLine {
    private float x1,y1,x2,y2;

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public void drawLine(Canvas canvas , Paint mPaint){
        float max = Math.max(Math.abs(x2 - x1),Math.abs(y2 - y1));
        float t = 1/(max); //шаг приращения

        System.out.println("шаг: " + t);


        if (x1 < x2 && y1 < y2) {
            float x = x1;
            float y = y1;
            while (x <= x2 && y <= y2) {
                canvas.drawPoint(x, y, mPaint);
                x = x + t * (x2 - x1);
                y = y + t * (y2 - y1);
            }
        } else if (x1 > x2 && y1 < y2){
            float x = x1;
            float y = y1;
            while (x >= x2 && y <= y2) {
                canvas.drawPoint(x, y, mPaint);
                x = x - t * (x1 - x2);
                y = y + t * (y2 - y1);
            }
        } else if (x1 < x2 && y1 > y2){
            float x = x2;
            float y = y2;
            while (x >= x1 && y <= y1) {
                canvas.drawPoint(x, y, mPaint);
                x = x - t * (x2 - x1);
                y = y + t * (y1 - y2);
            }
        } else if (x1 > x2 && y1 > y2){
            float x = x2;
            float y = y2;
            while (x <= x1 && y <= y1) {
                canvas.drawPoint(x, y, mPaint);
                x = x + t * (x1 - x2);
                y = y + t * (y1 - y2);
            }
        }
    }
}
