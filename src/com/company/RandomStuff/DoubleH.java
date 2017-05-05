package com.company.RandomStuff;

public class DoubleH{
    private double a;
    private DoubleH(double a){
        this.a = a;
    }
    public double getDouble(){
        return a;
    }
    public static DoubleH newDoubleH(double a){
        return new DoubleH(a);
    }
    public void setDouble(double a){
        this.a = a;
    }
}