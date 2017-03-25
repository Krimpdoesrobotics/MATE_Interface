package com.company.RandomStuff;

/**
 * Created by julia on 3/25/2017.
 */
public class DoubleH{
    private double a;
    public DoubleH(double a){
        this.a = a;
    }
    public double getDouble(){
        return a;
    }
    public static DoubleH newDoubleH(double a){
        DoubleH stuff = new DoubleH(a);
        return stuff;
    }
}