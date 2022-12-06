package com.example.orderez.Models;

public class Ingredient {
    //Converting Json Object from our API to a Java Class. This is one of JSON Objects in our api with this format.
    //we used an online converison tool to convert JSON objects to POJO java class to utilize and parse through them in our app. This is in models folder as it is a model.
    public int id;
    public String name;
    public String localizedName;
    public String image;
}
