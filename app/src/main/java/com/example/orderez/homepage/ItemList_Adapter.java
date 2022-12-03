package com.example.orderez.homepage;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderez.R;

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
    }

    @Override
    public int getItemCount() {
        return itemContatiner.size();
    }

    public void addItem(ItemList_Manager item){
        itemContatiner.add(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, count,startDate, endDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNameTV);
            count = itemView.findViewById(R.id.countTV);
            startDate = itemView.findViewById(R.id.startDateTV);
            endDate = itemView.findViewById(R.id.endDateTV);
        }
        
        public void setItem(ItemList_Manager item){
            itemName.setText(item.title);
            count.setText(item.count);
            startDate.setText(item.startDate);
            endDate.setText(item.endDate);

        }
    }

}
