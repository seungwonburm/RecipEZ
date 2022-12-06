package com.example.orderez.Adapters;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderez.R;
import com.example.orderez.Models.CalendarUtil;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{
    ArrayList<LocalDate> dayList;

    public CalendarAdapter (ArrayList<LocalDate> dayList){

        this.dayList = dayList;

    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         View view = inflater.inflate(R.layout.calendar_recycleview_item,parent,false);
         return new CalendarViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        // Put Date in a variable
        LocalDate day = dayList.get(position);
        if(day == null){
            holder.dayText.setText("");
        }else {
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));

            //Mark of "Today"
            if (day.equals(CalendarUtil.selectedDate)){
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }
        }


        //Text color(Sat Sun)
        if ((position + 1) % 7 == 0) {//SAT
            holder.dayText.setTextColor(Color.BLUE);
        }else if (position == 0 || position % 7 == 0){ //SUN
            holder.dayText.setTextColor(Color.RED);
        }

        //Click events (date)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Ayear = day.getYear();
                int Amonth = day.getMonthValue();
                int Aday = day.getDayOfMonth();

                String yearMonday = Ayear + "/" + Amonth + "/" + Aday;
                Toast.makeText(holder.itemView.getContext(), yearMonday, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return dayList.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder{

        TextView dayText;

        View parentView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);
            parentView = itemView.findViewById(R.id.parentView);

        }
    }
}
