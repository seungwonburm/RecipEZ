package com.example.orderez.homepage;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderez.R;
import com.example.orderez.Update_Delete;

import java.util.ArrayList;

public class ItemList_Adapter extends RecyclerView.Adapter<ItemList_Adapter.ViewHolder> {
    Context mContext;
    ArrayList<ItemList_Manager> itemContatiner = new ArrayList<>();

    public ItemList_Adapter(Context mContext){

        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.itemlist_listview_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemList_Adapter.ViewHolder holder, int position) {
        ItemList_Manager item = itemContatiner.get(position);
        holder.setItem(item);

        holder.itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(((Activity)view.getContext()).getApplicationContext(), Update_Delete.class);
                intent.putExtra("ItemName",holder.itemName.getText().toString());
                intent.putExtra("userId", Homepage_Items.id);
                ((Activity)view.getContext()).startActivity(intent);
                ((Activity)view.getContext()).finish();
            }
        });
        holder.expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(((Activity)view.getContext()).getApplicationContext(),Update_Delete.class);
                intent.putExtra("ItemName",holder.itemName.getText().toString());
                intent.putExtra("userId", Homepage_Items.id);
                ((Activity)view.getContext()).startActivity(intent);
                ((Activity)view.getContext()).finish();
            }
        });
        holder.count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(((Activity)view.getContext()).getApplicationContext(),Update_Delete.class);
                intent.putExtra("ItemName",holder.itemName.getText().toString());
                intent.putExtra("userId", Homepage_Items.id);
                ((Activity)view.getContext()).startActivity(intent);
                ((Activity)view.getContext()).finish();
            }
        });
        holder.itemUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(((Activity)view.getContext()).getApplicationContext(),Update_Delete.class);
                intent.putExtra("ItemName",holder.itemName.getText().toString());
                intent.putExtra("userId", Homepage_Items.id);
                ((Activity)view.getContext()).startActivity(intent);
                ((Activity)view.getContext()).finish();
            }
        });
        holder.itemmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(((Activity)view.getContext()).getApplicationContext(),Update_Delete.class);
                intent.putExtra("ItemName",holder.itemName.getText().toString());
                intent.putExtra("userId", Homepage_Items.id);
                ((Activity)view.getContext()).startActivity(intent);
                ((Activity)view.getContext()).finish();
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemContatiner.size();
    }

    public void addItem(ItemList_Manager item){
        itemContatiner.add(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, count,expDate, itemmemo, itemUnit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNameTV);
            count = itemView.findViewById(R.id.itemDate);
            expDate = itemView.findViewById(R.id.countNum);
            itemmemo = itemView.findViewById(R.id.itemMemo);
            itemUnit = itemView.findViewById(R.id.countUnit);

        }
        
        public void setItem(ItemList_Manager item){
            itemName.setText(item.title);
            expDate.setText(item.expDate);
            count.setText(item.count);
            itemUnit.setText(item.unit);
            itemmemo.setText(item.memo);


        }
    }

}
