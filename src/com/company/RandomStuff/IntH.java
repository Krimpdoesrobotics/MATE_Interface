package com.company.RandomStuff;

public class IntH{
    private int a;
    private IntH(int a){
        this.a = a;
    }
    public int getInt(){
        return a;
    }
    public static IntH newIntH(int a){
        return new IntH(a);
    }
    public void setInt(int a){
        this.a = a;
    }
}