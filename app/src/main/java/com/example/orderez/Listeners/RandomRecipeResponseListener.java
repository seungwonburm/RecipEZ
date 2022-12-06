package com.example.orderez.Listeners;

import com.example.orderez.Models.Root;

import java.util.ArrayList;

public interface RandomRecipeResponseListener {                       //A response listerer for our api, lets us know if we fetched or got error
    void didFetch(ArrayList<Root> response, String message);
    void didError(String message);
}
