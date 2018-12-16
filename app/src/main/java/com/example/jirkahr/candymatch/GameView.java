package com.example.jirkahr.candymatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;

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

    Context context = getContext();

    private TextView woodScore;
    private TextView goldScore;
    private TextView crystalScore;
    private TextView stoneScore;
    private TextView movesLeftView;

    private int scoredWood = 0;
    private int scoredGold = 0;
    private int scoredCrystal = 0;
    private int scoredStone = 0;
    private int movesLeft = 20;


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

                if(indexx != origIndexx || indexy != origIndexy) {
                    tempimageId = fieldResource[origIndexx][origIndexy].imageId;
                    fieldResource[origIndexx][origIndexy].imageId = fieldResource[indexx][indexy].imageId;
                    fieldResource[indexx][indexy].imageId = tempimageId;
                    movesLeft--;
                    movesLeftView.setText(Integer.toString(movesLeft));
                    shiftStraight();
                }
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
            horiz = null;
            foundx = -1;
            int scoredHoriz = 0;
            int mainImageIdHoriz = 0;
            for (int x = 0; x < fieldWidth; x++) {
                int scoredVerti = 0;
                mainImageId = fieldResource[x][y].imageId;
                if(foundx < 0) {
                    if (x <=4 && fieldResource[x + 1][y].imageId == mainImageId) {
                        if (x <= 3 && fieldResource[x + 2][y].imageId == mainImageId) {
                            if (x <= 2 && fieldResource[x + 3][y].imageId == mainImageId) {
                                if (x <= 1 && fieldResource[x + 4][y].imageId == mainImageId) {
                                    if (x == 0 && fieldResource[x + 5][y].imageId == mainImageId) {
                                        horiz = new int[]{0, 1, 2, 3, 4, 5};
                                        scoredHoriz = 600;

                                    } else {
                                        horiz = new int[]{0, 1, 2, 3, 4};
                                        scoredHoriz = 500;
                                    }
                                } else {
                                    horiz = new int[]{0, 1, 2, 3};
                                    scoredHoriz = 400;
                                }
                            } else {
                                horiz = new int[]{0, 1, 2};
                                scoredHoriz = 300;
                            }
                            foundx = x;
                            mainImageIdHoriz = mainImageId;
                        }
                    }
                }
                if(y<=4 && fieldResource[x][y+1].imageId==mainImageId) {
                    if(y<=3 && fieldResource[x][y+2].imageId==mainImageId) {
                        if(y<=2 && fieldResource[x][y+3].imageId==mainImageId) {
                            if(y<=1 && fieldResource[x][y+4].imageId==mainImageId) {
                                if(y==0 && fieldResource[x][y+5].imageId==mainImageId){
                                    verti = 6;
                                    scoredVerti = 600;
                                }
                                else {
                                    verti = 5;
                                    scoredVerti = 500;
                                }
                            }
                            else {
                                verti = 4;
                                scoredVerti = 400;
                            }
                        }
                        else {
                            verti = 3;
                            scoredVerti = 300;
                        }
                        //pro kazdou ikonku
                        shiftThemVertically(verti, x, y);
                        setTexts(mainImageId, scoredVerti);
                    }
                }
            }
            //ted je pro kazdy raadek
            if(foundx >= 0){
                shiftThemHorizontally(horiz, foundx, y);
                setTexts(mainImageIdHoriz, scoredHoriz);
            }
        }
    }

    void setTexts(int imageId, int scored) {
        switch (imageId){
            case 0:
                scoredWood += scored;
                woodScore.setText(Integer.toString(scoredWood));
                break;
            case 1:
                scoredGold += scored;
                goldScore.setText(Integer.toString(scoredGold));
                break;
            case 2:
                scoredCrystal += scored;
                crystalScore.setText(Integer.toString(scoredCrystal));
                break;
            case 3:
                scoredStone += scored;
                stoneScore.setText(Integer.toString(scoredStone));
                break;
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
    void shiftThemVertically(int temVert, int mainx, int mainy) {
        for(int y = mainy + temVert - 1; y >=0; y--) {
            fieldResource[mainx][y].isShiftedFlag += temVert;
            changeImageId(mainx, y);
        }
    }

    void shiftThemHorizontally(int[] temHoriz, int mainx, int mainy) {
        for(int y = mainy; y >= 0; y--){
            for(int t:temHoriz){
                fieldResource[mainx + t][y].isShiftedFlag += 1;
                changeImageId(mainx + t, y);
            }
        }
    }

    void changeImageId(int x, int y) {
        if(y<fieldResource[x][y].isShiftedFlag) {
            switch(x){
                case 0: fieldResource[x][y].imageId+=1+y;
                    break;
                case 1: fieldResource[x][y].imageId+=2+y;
                    break;
                case 2: fieldResource[x][y].imageId+=3+y;
                    break;
                case 3: fieldResource[x][y].imageId+=4+y;
                    break;
                case 4: fieldResource[x][y].imageId+=5+y;
                    break;
                case 5: fieldResource[x][y].imageId+=6+y;
                    break;
            }
            fieldResource[x][y].imageId %= numberOfIcons;
        }
        else {
            fieldResource[x][y].imageId = fieldResource[x][y-(int)fieldResource[x][y].isShiftedFlag].imageId;
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.WHITE);
        movesLeftView.setText(Integer.toString(movesLeft));
        int movingX = -1;
        int movingY = -1;
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
            if(scoredWood >= 1000 && scoredGold >= 1000 && scoredCrystal >= 1000 && scoredStone >= 1000) {
                Intent intent = new Intent(context, Cleared.class);
                intent.putExtra("TOTAL_SCORE", scoredWood+scoredGold+scoredCrystal+scoredStone);
                context.startActivity(intent);
            }
            else if(movesLeft <= 0) {
                Intent intent = new Intent(context, GameOver.class);
                intent.putExtra("TOTAL_SCORE", scoredWood+scoredGold+scoredCrystal+scoredStone);
                context.startActivity(intent);
            }

            shiftStraight();
        }

        Sleep.run();
        invalidate();
    }

    Thread Sleep = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5);
            }
            catch (Exception e) {
                e.getLocalizedMessage();
            }
        }
    });

    public void setWoodScore(TextView woodScore) {
        this.woodScore = woodScore;
    }

    public void setGoldScore(TextView goldScore) {
        this.goldScore = goldScore;
    }

    public void setCrystalScore(TextView crystalScore) {
        this.crystalScore = crystalScore;
    }

    public void setStoneScore(TextView stoneScore) {
        this.stoneScore = stoneScore;
    }

    public void setMovesLeftView(TextView movesLeftView) {
        this.movesLeftView = movesLeftView;
    }
}

