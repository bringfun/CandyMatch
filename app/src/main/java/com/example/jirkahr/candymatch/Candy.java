package com.example.jirkahr.candymatch;

public class Candy {
    public int imageId;
    public int isMovingFlag;
    public double isShiftedFlag;

    Candy(int iId, int isMov, double isShift) {
        this.imageId = iId;
        this.isMovingFlag = isMov;
        this.isShiftedFlag = isShift;
    }
}
