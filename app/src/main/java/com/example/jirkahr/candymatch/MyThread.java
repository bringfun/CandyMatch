package com.example.jirkahr.candymatch;

public class MyThread extends Thread {
    private int milis = 2;

    public void sleep(int ms){
        this.milis = ms;
        this.run();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.milis);
        }
        catch (Exception e) {
            e.getLocalizedMessage();
        }
    }
}
