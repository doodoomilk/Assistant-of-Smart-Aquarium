package com.example.cloudfinal;
/*
this class will be used to assign values to the Recyclerview.

 */

public class menu {

    private String functionName;
    //private String functionDescription;
    private int functionImage;

    public menu(String functionName, int functionImage) {
        this.functionName = functionName;
        //this.functionDescription = functionDescription;
        this.functionImage = functionImage;
    }

    public String getFunctionName() {
        return functionName;
    }

    /*
    public String getFunctionDescription() {
        return functionDescription;
    }
    */

    public int getFunctionImage() {
        return functionImage;
    }
}