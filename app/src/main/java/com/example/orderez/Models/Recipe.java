package com.example.orderez.Models;

import java.util.ArrayList;

public class Recipe {
    //Converting Json Object from our API to a Java Class. This is one of JSON Objects in our api with this format.
    //we used an online converison tool to convert JSON objects to POJO java class to utilize and parse through them in our app. This is in models folder as it is a model.
    public boolean vegetarian;
    public boolean vegan;
    public boolean glutenFree;
    public boolean dairyFree;
    public boolean veryHealthy;
    public boolean cheap;
    public boolean veryPopular;
    public boolean sustainable;
    public boolean lowFodmap;
    public int weightWatcherSmartPoints;
    public String gaps;
    public int preparationMinutes;
    public int cookingMinutes;
    public int aggregateLikes;
    public int healthScore;
    public String creditsText;
    public String sourceName;
    public double pricePerServing;
    public ArrayList<ExtendedIngredient> extendedIngredients;
    public int id;
    public String title;
    public int readyInMinutes;
    public int servings;
    public String sourceUrl;
    public String image;
    public String imageType;
    public String summary;
    public ArrayList<String> cuisines;
    public ArrayList<String> dishTypes;
    public ArrayList<String> diets;
    public ArrayList<String> occasions;
    public String instructions;
    public ArrayList<AnalyzedInstruction> analyzedInstructions;
    public Object originalId;
    public String spoonacularSourceUrl;
}
