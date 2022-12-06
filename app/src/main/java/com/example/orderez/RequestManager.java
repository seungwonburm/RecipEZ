package com.example.orderez;

import android.content.Context;

import com.example.orderez.Listeners.RandomRecipeResponseListener;
import com.example.orderez.Listeners.RecipeDetailsListener;
import com.example.orderez.Models.RandomRecipeAPIResponse;
import com.example.orderez.Models.RecipeDetailsResponse;
import com.example.orderez.Models.Root;
import com.example.orderez.Listeners.RandomRecipeResponseListener;
import com.example.orderez.Listeners.RecipeDetailsListener;
import com.example.orderez.Models.RecipeDetailsResponse;
import com.example.orderez.Models.Root;
import com.example.orderez.homepage.Homepage_Items;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager { //This is our Request manager to request GET information from the API
    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com/").addConverterFactory(GsonConverterFactory.create()).build(); //We are using Retrofit because it assists us in turning HTTP API into a java interface for us. The code is based on their documentation
    String recipe = Homepage_Items.recipe;

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener) {
        System.out.println("========getRandomRecipes START=============");
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class); //Using retrofit we create java interface based on our api response we get
        System.out.println("==========RecipeAPICalled===========" + callRandomRecipes);
        Call<ArrayList<Root>> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10",recipe,2,true );
//        Call<Root> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10");
        System.out.println("=============Call========" + call.toString() + call.request());
        call.enqueue(new Callback<ArrayList<Root>>() {  //we enqueue our call with API response listener to know if we got back a response from it. this is different then fetch because this is directed towards api information
            @Override
            public void onResponse(Call<ArrayList<Root>> call, Response<ArrayList<Root>> response) { //on response
                System.out.println("==========OnResponse triggered===========");
                if (!response.isSuccessful()) { //if response was not successful
                    System.out.println("==========Response not success===========");
                    listener.didError(response.message()); //we show error message
                    return;
                }
                System.out.println("=======Response Success==============");
                System.out.println("=====================" + response.body());
                System.out.println("=====================" + response.message());
                listener.didFetch(response.body(), response.message()); //or else we fetch the information from listerner.
            }
            @Override
            public void onFailure(Call<ArrayList<Root>> call, Throwable t) {
                System.out.println("===========onFailure triggered==========" + t.getMessage() + "  " + t.getCause() + "  " + t.getStackTrace());
                System.out.println(t.toString() + "========");
                listener.didError(t.getMessage()); //on failure we show error message

            }
        });
    }

    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


//    https://api.spoonacular.com/recipes/findByIngredients?ingredients=apples,+flour,+sugar&number=2
    private interface CallRandomRecipes{ //interface to call random recipes
        @GET("recipes/findByIngredients") //submitting a GET request for random recipe. An extension of base URL
        Call<ArrayList<Root>> callRandomRecipe( //Our request according to documentation requerires api key and a number which represents how many result we wanna see.
           @Query("apiKey") String apiKey, //query called apiKey and we obtain it from our apiKey
           @Query("number") String number,//number of results we wanna see. I.e how many random recipes
           @Query("ingredients") String ingredient,
           @Query("ranking") Integer ranking,
           @Query("ignorePantry") Boolean ignorePantry
        );
    }

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }


}
