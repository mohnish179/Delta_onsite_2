package com.example.onsite_rect;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import static android.content.ContentValues.TAG;

public class CustomView extends View {

    int flag=0;
    int flag2=0;

    Rect rect;
    int[] pointsx=new int[4];
    int[] pointsy=new int[4];
    int corner;


    Paint red;
    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init()
    {
        red=new Paint(Paint.ANTI_ALIAS_FLAG);
        red.setColor(Color.RED);
        rect=new Rect();




    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointX = (int)event.getX();
        int pointY = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if((pointX>pointsx[0]-60 && pointX<pointsx[0]+60)&&(pointY>pointsy[0]-60 && pointY<pointsy[0]+60))
                {
                    corner=1;
                    Log.d(TAG, "onTouchEvent: its recognising");

                }
               else if((pointX>pointsx[1]-60 && pointX<pointsx[1]+60)&&(pointY>pointsy[1]-60 && pointY<pointsy[1]+60))
                {
                    corner=2;
                    Log.d(TAG, "onTouchEvent: corner 2");
                }
               else if((pointX>pointsx[1]-60 && pointX<pointsx[1]+60)&&(pointY>pointsy[0]-60 && pointY<pointsy[0]+60))
                {
                    corner=3;
                    Log.d(TAG, "onTouchEvent: corner 3");
                }
               else if((pointX>pointsx[0]-60 && pointX<pointsx[0]+60)&&(pointY>pointsy[1]-60 && pointY<pointsy[1]+60))
                {
                    corner=4;
                }
               else if((pointX>pointsx[0]+100 && pointX<pointsx[1]-100)&&(pointY>pointsy[0]+100 && pointY<pointsy[1]-100))
                {
                    corner=5;

                    Log.d(TAG, "onTouchEvent: 5");
                }





                break;
            case MotionEvent.ACTION_MOVE:

                if(corner==1)
                {


                      pointsx[0] = pointX;
                      pointsy[0] = pointY;



                    Log.d(TAG, "onTouchEvent: cool");
                }
                else if(corner==2)
                {
                    pointsx[1]=pointX;
                    pointsy[1]=pointY;
                    Log.d(TAG, "onTouchEvent: corner 2 move");
                }
                else  if(corner==3)
                {
                    pointsx[1]=pointX;
                    pointsy[0]=pointY;
                }
                else if(corner==4)
                {
                    pointsx[0]=pointX;
                    pointsy[1]=pointY;
                }
                else if(corner==5) {


                    int deltaX = rect.width();
                    int deltaY =  rect.height();

                    if(pointX>deltaX/2 && pointY>deltaY/2 && (pointX+deltaX/2)<1085 && (pointY+deltaY/2)<2015)
                    {
                        rect.left = pointX - deltaX / 2;
                        rect.top = pointY - deltaY / 2;
                        rect.right = rect.left + deltaX;
                        rect.bottom = rect.top + deltaY;

                        pointsx[0] = rect.left;
                        pointsx[1] = rect.right;
                        pointsy[0] = rect.top;
                        pointsy[1] = rect.bottom;


                    }
                    else if(pointX<deltaX/2)
                    {
                        if(pointY>deltaY/2 && (pointY+deltaY/2)<2015)
                        {
                            rect.top = pointY - deltaY / 2;
                            rect.bottom = rect.top + deltaY;

                            pointsx[0] = rect.left;
                            pointsx[1] = rect.right;
                            pointsy[0] = rect.top;
                            pointsy[1] = rect.bottom;
                        }

                    }
                    else if((pointX+deltaX/2)>1085)
                    {
                        if(pointY>deltaY/2 && (pointY+deltaY/2)<2015)
                        {
                            rect.top = pointY - deltaY / 2;
                            rect.bottom = rect.top + deltaY;

                            pointsx[0] = rect.left;
                            pointsx[1] = rect.right;
                            pointsy[0] = rect.top;
                            pointsy[1] = rect.bottom;
                        }
                    }
                    else if(pointY<deltaY/2)
                    {
                        if(pointX>deltaX/2 && (pointX+deltaX/2)<1085)
                        {
                            rect.left = pointX - deltaX / 2;
                            rect.right = rect.left + deltaX;
                            pointsx[0] = rect.left;
                            pointsx[1] = rect.right;
                            pointsy[0] = rect.top;
                            pointsy[1] = rect.bottom;

                        }
                    }
                    else if((pointY+deltaY/2)>2015)
                    {
                        rect.left = pointX - deltaX / 2;
                        rect.right = rect.left + deltaX;
                        pointsx[0] = rect.left;
                        pointsx[1] = rect.right;
                        pointsy[0] = rect.top;
                        pointsy[1] = rect.bottom;
                    }

                }



                break;

            case MotionEvent.ACTION_UP:
                corner=6;




                break;



            default:
                return false;
        }


       postInvalidate();

        return true;
    }

    public void gg()
    {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(flag==0)
        {
            rect.set(400,600,900,1100);
            canvas.drawRect(400,600,900,1100,red);
            pointsx[0]=400;
            pointsy[0]=600;
            pointsx[1]=900;
            pointsy[1]=1100;

            flag=1;
        }
        else
        {

            if(corner==1||corner==2||corner==3||corner==4)
            {
                rect.set(pointsx[0],pointsy[0],pointsx[1],pointsy[1]);
                canvas.drawRect(rect,red);
            }
            else if(corner==5)
            {
                canvas.drawRect(rect,red);

              
            }
            else if(corner==6)
            {rect.set(pointsx[0],pointsy[0],pointsx[1],pointsy[1]);
                canvas.drawRect(rect,red);
            }
            else
        {
            canvas.drawRect(400,600,900,1100,red);
        }



        }





    }
}
