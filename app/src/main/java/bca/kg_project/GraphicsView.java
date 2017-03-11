package bca.kg_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;

import bca.kg_project.drawMethods.line.BresenhemDrawLine;
import bca.kg_project.drawMethods.line.ParametricalDrawLine;
import bca.kg_project.tools.DrawTools;


/**
 * Created by Евгений on 01.03.2017.
 */

public class GraphicsView extends View {

    private Canvas canvas;
    private Bitmap bitmap;
    private Paint mPaint;

    //pencil
    float oldX;
    float oldY;

    //param_line
    private ParametricalDrawLine parametricalDrawLine;
    private BresenhemDrawLine bresenhemDrawLine;
    int quantityOfTapsLine = 0;

    public GraphicsView(Context context) {
        super(context);
    }

    public GraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        bitmap = Bitmap.createBitmap(700,1024, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        /*setScaleX(10);
        setScaleY(10);*/

        //линия. параметрический алгоритм
        parametricalDrawLine = new ParametricalDrawLine();
        bresenhemDrawLine = new BresenhemDrawLine();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap,0,0,mPaint);
    }

    public void drawPoint(float x , float y){
        canvas.drawPoint(x,y,mPaint);
        invalidate();//перерисовка экрана
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (DrawTools.instrument == 1) {
                    drawPoint(event.getX(), event.getY());
                    oldX = event.getX();
                    oldY = event.getY();
                }else if (DrawTools.instrument == 2){
                    if (quantityOfTapsLine == 0){
                        System.out.println("1 опустил");
                        parametricalDrawLine.setX1(event.getX());
                        parametricalDrawLine.setY1(event.getY());
                    } else if (quantityOfTapsLine == 1){
                        System.out.println("2 опустил");
                        parametricalDrawLine.setX2(event.getX());
                        parametricalDrawLine.setY2(event.getY());
                    }
                } else if (DrawTools.instrument == 3){
                    if (quantityOfTapsLine == 0){
                        System.out.println("1 опустил");
                        bresenhemDrawLine.setX1(event.getX());
                        bresenhemDrawLine.setY1(event.getY());
                    } else if (quantityOfTapsLine == 1){
                        System.out.println("2 опустил");
                        bresenhemDrawLine.setX2(event.getX());
                        bresenhemDrawLine.setY2(event.getY());
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (DrawTools.instrument == 1) {
                    canvas.drawLine(oldX, oldY, event.getX(), event.getY(), mPaint);
                    oldX = event.getX();
                    oldY = event.getY();
                } else if (DrawTools.instrument == 2){
                    if (quantityOfTapsLine == 0){
                        System.out.println("1 поднял");
                        quantityOfTapsLine++;
                    } else if (quantityOfTapsLine == 1){
                        System.out.println("2 поднял");
                        parametricalDrawLine.drawLine(canvas,mPaint);
                        System.out.println("нарисовал");
                        quantityOfTapsLine = 0;
                    }
                } else if (DrawTools.instrument == 3){
                    if (quantityOfTapsLine == 0){
                        System.out.println("1 поднял");
                        quantityOfTapsLine++;
                    } else if (quantityOfTapsLine == 1){
                        System.out.println("2 поднял");
                        bresenhemDrawLine.drawLine(canvas,mPaint);
                        System.out.println("нарисовал");
                        quantityOfTapsLine = 0;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (DrawTools.instrument == 1) {
                    canvas.drawLine(oldX, oldY, event.getX(), event.getY(), mPaint);
                    oldX = event.getX();
                    oldY = event.getY();
                }
                break;
        }
        invalidate();
        return true;
    }

    /*public void drawLineParam(float x1 , float y1 , float x2 , float y2){
        float t = 1/(Math.max(Math.abs(x2 - x1),Math.abs(y2 - y1))); //шаг приращения
        float x = x1;
        float y = y1;
        while (x != x2 && y != y2){
            canvas.drawPoint(x,y,mPaint);
            x = x1 + t * (x2 - x1);
            y = y1 + t * (y2 - y1);
        }
    }*/

    public void clear() {
        bitmap.eraseColor(Color.WHITE);
    }

    public void saveImage() {

        Date date = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String filename = df.format(date);

        try {
            File extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            System.out.println("kek1");
            OutputStream outStream;
            File file = new File(extStorageDirectory, filename + ".jpg");
            System.out.println("kek2");
            if (!extStorageDirectory.exists()) {
                extStorageDirectory.mkdirs();
            }
            System.out.println("kek3");
            file.createNewFile();
            System.out.println("kek4");
            outStream = new FileOutputStream(file);
            System.out.println("kek5");
            bitmap.compress(Bitmap.CompressFormat.PNG, 86, outStream);
            System.out.println("kek6");
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
