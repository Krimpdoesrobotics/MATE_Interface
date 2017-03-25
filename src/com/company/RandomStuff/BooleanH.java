package com.company.RandomStuff;

/**
 * Created by julia on 3/25/2017.
 */
public class BooleanH{
    private boolean a;
    public BooleanH(boolean a){
        this.a = a;
    }
    public boolean getBoolean(){
        return a;
    }
    public static BooleanH newBooleanH(boolean a){
        BooleanH stuff = new BooleanH(a);
        return stuff;
    }
}