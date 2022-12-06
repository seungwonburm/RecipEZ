package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.orderez.Adapters.RandomRecipeAdapter;
import com.example.orderez.Listeners.RandomRecipeResponseListener;
import com.example.orderez.Listeners.RecipeClickListener;
import com.example.orderez.Models.RandomRecipeAPIResponse;
import com.example.orderez.Models.Root;
import com.example.orderez.Adapters.RandomRecipeAdapter;
import com.example.orderez.Listeners.RandomRecipeResponseListener;
import com.example.orderez.Listeners.RecipeClickListener;
import com.example.orderez.Models.Root;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog; //Dialog message while api works on fetching information
    RequestManager manager; //Our request manager to request get information from api, the class will be explained in the next file
    RandomRecipeAdapter randomRecipeAdapter; //Adapter to get random recipe
    RecyclerView recyclerView; //RecyclerView to show all recipe in card format


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("====OnCreate===");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this); //on create we want to show a progress dialog as we fetch our api information
        dialog.setTitle("Presenting data to you by Yuchan, Jeet, Andrew, Lauren, please wait :)"); //this is the message we wanna show while user waits for info
        System.out.println("========setTitle DONE=============");
        manager = new RequestManager(this); //we make our manager make a new request to our request manager in this context
        manager.getRandomRecipes(randomRecipeResponseListener); //we then ask manager to get random recipe and pass in a response listener to know if we did get information
        System.out.println("========RandomResponseListener=============");
        dialog.show(); //this shows the dialog
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() { //this is our responser listener that determines if we were successful in fetching information or not
        @Override
        public void didFetch(ArrayList<Root> response, String message) { //function in the case that we fetch the information
            System.out.println("==========Fetched===========");
            dialog.dismiss(); //we dismiss the loading dialog
            System.out.println("==========Dialog dismissed===========");
            recyclerView = findViewById(R.id.recycler_random); //we set our recycler view to be recycler_random that we generated
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1)); //This sets our layout in main activity
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response, recipeClickListener); //calling randomrecipeadapter to get random recipes and storing them in our variable
            recyclerView.setAdapter(randomRecipeAdapter); //settings the adapter of recyclerview
        }

        @Override
        public void didError(String message) { //case if we find an error in fetching
            System.out.println("==========Did not fetch===========");
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT); //we show fail message in the case we fail to fetch

        } //the listener lets us know if we fetched or failed and based on that sets the recycler view or shows a fail message
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(MainActivity.this, RecipeDetailsActivity.class).putExtra("id", id));

        }
    };
}