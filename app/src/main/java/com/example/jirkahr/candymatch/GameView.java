package com.example.jirkahr.candymatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    Bitmap[] bmp;

    int posx = 1;
    int posy = 1;
    int indexx = 1;
    int indexy = 1;

    int origposx = 1;
    int origposy = 1;
    int origIndexx = 1;
    int origIndexy = 1;

    int numberOfIcons = 4;

    int candyWidth = 215;
    int candyHeight = 215;

    Candy[][] fieldResource = new Candy[][]{
            { new Candy(0, 0, 0), new Candy(1, 0, 0), new Candy(2, 0, 0), new Candy(3, 0, 0), new Candy(0, 0, 0), new Candy(1, 0, 0)},
            { new Candy(1, 0, 0), new Candy(2, 0, 0), new Candy(3, 0, 0), new Candy(0, 0, 0), new Candy(1, 0, 0), new Candy(2, 0, 0)},
            { new Candy(0, 0, 0), new Candy(1, 0, 0), new Candy(2, 0, 0), new Candy(3, 0, 0), new Candy(0, 0, 0), new Candy(1, 0, 0)},
            { new Candy(1, 0, 0), new Candy(2, 0, 0), new Candy(3, 0, 0), new Candy(0, 0, 0), new Candy(1, 0, 0), new Candy(2, 0, 0)},
            { new Candy(0, 0, 0), new Candy(1, 0, 0), new Candy(2, 0, 0), new Candy(3, 0, 0), new Candy(0, 0, 0), new Candy(1, 0, 0)},
            { new Candy(1, 0, 0), new Candy(2, 0, 0), new Candy(3, 0, 0), new Candy(0, 0, 0), new Candy(1, 0, 0), new Candy(2, 0, 0)}
    };

    int fieldWidth = fieldResource[0].length;
    int fieldHeight = fieldResource.length;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(Context context) {
        bmp = new Bitmap[numberOfIcons];
        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_wood);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_gold);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_crystal);
        bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_stone);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN: {
                origposx=(int)event.getX();
                origposy=(int)event.getY();
                origIndexx = origposx/candyWidth;
                origIndexy = origposy/candyHeight;

                fieldResource[origIndexx][origIndexy].isMovingFlag = 1;
            }
            break;

            case MotionEvent.ACTION_MOVE:
            {
                posx=(int)event.getX();
                posy=(int)event.getY();
                invalidate();
            }

            break;
            case MotionEvent.ACTION_UP:
                int tempimageId;
                posx=(int)event.getX();
                posy=(int)event.getY();
                indexx = posx/candyWidth;
                indexy = posy/candyHeight;

                fieldResource[origIndexx][origIndexy].isMovingFlag = 0;

                tempimageId = fieldResource[origIndexx][origIndexy].imageId;
                fieldResource[origIndexx][origIndexy].imageId = fieldResource[indexx][indexy].imageId;
                fieldResource[indexx][indexy].imageId = tempimageId;
                //shift(indexx,indexy);
                shiftStraight();
                invalidate();
                break;
        }
        return true;
    }

    void shiftStraight() {
        int mainImageId;
        int[] horiz = null;
        int foundx;
        int verti = 0;

        for (int y = 0; y < fieldHeight; y++) {
            //horiz =  new int[]{0};
            foundx = -1;
            for (int x = 0; x < fieldWidth; x++) {
                mainImageId = fieldResource[x][y].imageId;
                if(foundx == -1) {
                    if (x <=4 && fieldResource[x + 1][y].imageId == mainImageId) {
                        if (x <= 3 && fieldResource[x + 2][y].imageId == mainImageId) {
                            if (x <= 2 && fieldResource[x + 3][y].imageId == mainImageId) {
                                if (x <= 1 && fieldResource[x + 4][y].imageId == mainImageId) {
                                    if (x == 0 && fieldResource[x + 5][y].imageId == mainImageId) {
                                        horiz = new int[]{0, 1, 2, 3, 4, 5};
                                    } else {
                                        horiz = new int[]{0, 1, 2, 3, 4};
                                    }
                                } else {
                                    horiz = new int[]{0, 1, 2, 3};
                                }
                            } else {
                                horiz = new int[]{0, 1, 2};
                            }
                            foundx = x;
                        }
                    }
                }
                if(y<=4 && fieldResource[x][y+1].imageId==mainImageId) {
                    if(y<=3 && fieldResource[x][y+2].imageId==mainImageId) {
                        if(y<=2 && fieldResource[x][y+3].imageId==mainImageId) {
                            if(y<=1 && fieldResource[x][y+4].imageId==mainImageId) {
                                if(y==0 && fieldResource[x][y+5].imageId==mainImageId){
                                    verti = 6;
                                }
                                else {
                                    verti = 5;
                                }
                            }
                            else {
                                verti = 4;
                            }
                        }
                        else {
                            verti = 3;
                        }
                        //pro kazdou ikonku
                        shiftThemVertically(verti, x, y);
                    }
                }
            }
            //ted je pro kazdy raadek
            if(foundx > 0 && horiz!=null){
                shiftThemHorizontally(horiz, foundx, y);
            }


        }
    }
/*
    void shift(int mainx, int mainy) {
        int mainImageId = fieldResource[mainx][mainy].imageId;
        boolean l2=false,l1=false,r1=false,r2=false;
        
        if(mainx-1>=0 && fieldResource[mainx-1][mainy].imageId==mainImageId){
            l1=true;
        }
        if(mainx-2>=0 && fieldResource[mainx-2][mainy].imageId==mainImageId){
            l2=true;
        }
        if(mainx+1< fieldWidth && fieldResource[mainx+1][mainy].imageId==mainImageId){
            r1=true;
        }
        if(mainx+2< fieldWidth && fieldResource[mainx+2][mainy].imageId==mainImageId){
            r2=true;
        }

        if(l2 && l1){
            if(r1 && r2) {
                //five
                shiftThem(new int[]{-2,-1,0,1,2}, mainx, mainy);
            }
            else if(r1) {
                //left-four
                shiftThem(new int[]{-2,-1,0,1}, mainx, mainy);
            }
            else {
                //left-three
                shiftThem(new int[]{-2,-1,0}, mainx, mainy);
            }
        }
        else if(l1 && r1){
            if(r2) {
                //four-right
                shiftThem(new int[]{-1,0,1,2}, mainx, mainy);
            }
            else {
                //three-middle
                shiftThem(new int[]{-1,0,1}, mainx, mainy);
            }
        }
        else if(r1 && r2) {
            //three-right
            shiftThem(new int[]{0,1,2}, mainx, mainy);
        }

    }
*/
    void shiftThemVertically(int temVertically, int mainx, int mainy) {
        for(int y = mainy + temVertically - 1; y >=0; y--) {
            fieldResource[mainx][y].isShiftedFlag += 1+temVertically;

            if(y<fieldResource[mainx][y].isShiftedFlag) {
                switch(mainx){
                    case 0: fieldResource[mainx][y].imageId+=1+y;
                        break;
                    case 1: fieldResource[mainx][y].imageId+=2+y;
                        break;
                    case 2: fieldResource[mainx][y].imageId+=3+y;
                        break;
                    case 3: fieldResource[mainx][y].imageId+=4+y;
                        break;
                    case 4: fieldResource[mainx][y].imageId+=5+y;
                        break;
                    case 5: fieldResource[mainx][y].imageId+=6+y;
                        break;
                }
                fieldResource[mainx][y].imageId %= numberOfIcons;
            }
            else {
                fieldResource[mainx][y].imageId = fieldResource[mainx][y-(int)fieldResource[mainx][y].isShiftedFlag].imageId;
            }
        }
    }

    void shiftThemHorizontally(int[] temHoriz, int mainx, int mainy) {
        for(int y = mainy; y >= 0; y--){
            for(int t:temHoriz){
                fieldResource[mainx + t][y].isShiftedFlag += 1;
                /*
                if(fieldResource[mainx + t][y].isShiftedFlag >= 3){
                    fieldResource[mainx + t][y].isShiftedFlag = 3;
                }
                */
                if(y<fieldResource[mainx + t][y].isShiftedFlag) {
                    switch(t){
                        case 0: fieldResource[mainx + t][y].imageId+=1;
                                break;
                        case 1: fieldResource[mainx + t][y].imageId+=2;
                            break;
                        case 2: fieldResource[mainx + t][y].imageId+=3;
                            break;
                        case 3: fieldResource[mainx + t][y].imageId+=4;
                            break;
                        case 4: fieldResource[mainx + t][y].imageId+=5;
                            break;
                        case 5: fieldResource[mainx + t][y].imageId+=6;
                            break;
                    }
                    fieldResource[mainx + t][y].imageId %= numberOfIcons;
                }
                else {
                    fieldResource[mainx + t][y].imageId = fieldResource[mainx + t][y-(int)fieldResource[mainx + t][y].isShiftedFlag].imageId;
                }
            }
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.WHITE);
        int movingX = -1;
        int movingY = -1;
        boolean inv = false;
        boolean check = true;

        for (int y = 0; y < fieldHeight; y++) {
            for (int x = 0; x < fieldWidth; x++) {
                if (fieldResource[x][y].isMovingFlag == 0) {
                    canvas.drawBitmap(bmp[fieldResource[x][y].imageId], null,
                            new Rect(x * candyWidth, (int)((y-fieldResource[x][y].isShiftedFlag) * candyHeight), (x + 1) * candyWidth, (int)((y + 1 - fieldResource[x][y].isShiftedFlag) * candyHeight)), null);
                }
                else {
                    movingX = x;
                    movingY = y;
                }

                if(fieldResource[x][y].isShiftedFlag>0) {
                    fieldResource[x][y].isShiftedFlag -= 0.05;
                    check = false;
                }
                else {
                    fieldResource[x][y].isShiftedFlag = 0;
                }
            }
        }
        if (movingX >= 0 && movingY >= 0) {
            canvas.drawBitmap(bmp[fieldResource[movingX][movingY].imageId], null,
                    new Rect(posx - candyWidth, posy - candyHeight, posx + candyWidth, posy + candyHeight), null);
        }

        if(check) {
            /*
            for (int y = 0; y < fieldHeight; y++) {
                for (int x = 1; x < fieldWidth-1; x++) {
                    if(fieldResource[x][y].isShiftedFlag == 0) {
                        shift(x,y);
                        invalidate();
                    }
                }
            }
            */
            shiftStraight();
        }

        invalidate();
    }
}

