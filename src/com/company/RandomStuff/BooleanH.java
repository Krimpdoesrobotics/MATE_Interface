package com.company.RandomStuff;

public class BooleanH{
    private boolean a;
    public BooleanH(boolean a){
        this.a = a;
    }
    public boolean getBoolean(){
        return a;
    }
    public static BooleanH newBooleanH(boolean a){
        return new BooleanH(a);
    }
    public void setBoolean(boolean a){
        this.a = a;
    }
}