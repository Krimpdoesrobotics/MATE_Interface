package com.company.RandomStuff;

/**
 * Created by julia on 3/25/2017.
 */
public class IntH{
    private int a;
    public IntH(int a){
        this.a = a;
    }
    public int getInt(){
        return a;
    }
    public static IntH newIntH(int a){
        IntH stuff = new IntH(a);
        return stuff;
    }
    public void setInt(int a){
        this.a = a;
    }
}