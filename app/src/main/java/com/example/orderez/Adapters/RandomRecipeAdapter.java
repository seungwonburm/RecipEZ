package com.example.orderez.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderez.Listeners.RecipeClickListener;
import com.example.orderez.Models.RandomRecipeAPIResponse;
import com.example.orderez.Models.Recipe;
import com.example.orderez.Models.Root;
import com.example.orderez.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder>{   //Adapter to present our information that we collect form api
    Context context;
    ArrayList<Root> list;
    RecipeClickListener listener;


    public RandomRecipeAdapter(Context context, ArrayList<Root> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));   //When creating the view, we inflate from context
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) { //This extracts the neccessary information from the api (title of recipe, likes, serving, and time.
        System.out.println("====RESPONSE LIST TO UI--->" + list);
        holder.textView_title.setText(list.get(position).title);
        holder.textView_title.setSelected(true);
        holder.textView_likes.setText(list.get(position).likes+" Likes");
        holder.textView_servings.setText(list.get(position).missedIngredientCount+" Missing Ingredient");
        holder.textView_time.setText(list.get(position).usedIngredientCount+ " Using Ingredient");
        Picasso.get().load(list.get(position).image).into(holder.imageView_food); //We use picasso to display image in our app

        holder.random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }


    @Override
    public int getItemCount() { //function to get the number of items in our list
        return list.size();
    }
}

class RandomRecipeViewHolder extends RecyclerView.ViewHolder {  //This is a recycler view to show all the recipes we fetch
    CardView random_list_container;  //CardView for our overall view of container
    TextView textView_title, textView_servings, textView_likes, textView_time; //All info extracted from api will be in TextView format
    ImageView imageView_food; //The image of food displayed using picasso will be type ImageView


    public RandomRecipeViewHolder(@NonNull View itemView) { //all code below is responsible for storing the values into our variabless below
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_servings = itemView.findViewById(R.id.textView_servings);
        textView_likes = itemView.findViewById(R.id.textView_likes);
        textView_time = itemView.findViewById(R.id.textView_time);
        imageView_food = itemView.findViewById(R.id.imageView_food);
    }
}

