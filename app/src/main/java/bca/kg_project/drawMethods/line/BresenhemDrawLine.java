package bca.kg_project.drawMethods.line;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Евгений on 11.03.2017.
 */

public class BresenhemDrawLine {
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
        Boolean steep = Math.abs(y2 - y1) > Math.abs(x2 - x1); // Проверяем рост отрезка по оси икс и по оси игрек
        // Отражаем линию по диагонали, если угол наклона слишком большой
        if (steep)
        {
            swap(x1, y1); // Перетасовка координат вынесена в отдельную функцию для красоты
            swap(x2, y2);
        }
        // Если линия растёт не слева направо, то меняем начало и конец отрезка местами
        if (x1 > x2)
        {
            swap(x1, x2);
            swap(y1, y2);
        }
        float dx = x2 - x1;
        float dy = Math.abs(y2 - y1);
        float error = dx / 2; // Здесь используется оптимизация с умножением на dx, чтобы избавиться от лишних дробей
        float ystep = (y1 < y2) ? 1 : -1; // Выбираем направление роста координаты y
        float y = y1;
        for (float x = x1; x <= x2; x++) {
            canvas.drawPoint(steep ? y : x, steep ? x : y,mPaint); // Не забываем вернуть координаты на место
            error -= dy;
            if (error < 0) {
                y += ystep;
                error += dx;
            }
        }
    }

    public void swap(float x1 , float y1){
        float xOld = x1;
        float yOld = y1;
        this.x1 = yOld;
        this.y1 = xOld;
    }
}
