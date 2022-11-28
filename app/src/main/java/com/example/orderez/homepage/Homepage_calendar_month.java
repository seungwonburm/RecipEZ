package com.example.orderez.homepage;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.orderez.R;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homepage_calendar_month#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homepage_calendar_month extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Homepage_calendar_month() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Homepage_calendar_month2.
     */
    // TODO: Rename and change types and number of parameters
    public static Homepage_calendar_month newInstance(String param1, String param2) {
        Homepage_calendar_month fragment = new Homepage_calendar_month();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView monthYearText; //Year and Month Textview
    LocalDate selectedDate;
    RecyclerView recyclerView;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_homepage_calendar_month, container, false);

        //Default set
        monthYearText = getActivity().findViewById(R.id.monthYearText);
        ImageButton preBtn = getActivity().findViewById(R.id.pre_btn);
        ImageButton nextBtn = getActivity().findViewById(R.id.next_btn);
        recyclerView = getActivity().findViewById(R.id.recycler_month);

        //Show present time
//        selectedDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
//        selectedDate.format(formatter);
//        monthYearText.setText(selectedDate);

        ArrayList<String> dayList = dayInMonthArray(selectedDate);
        CalendarAdapter adapter = new CalendarAdapter(dayList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


        //Click Events (Arrow buttons)
        preBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                selectedDate = selectedDate.minusMonths(1);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                selectedDate = selectedDate.plusMonths(1);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> dayInMonthArray(LocalDate date){
        ArrayList<String> dayList = new ArrayList();
        YearMonth yearMonth = YearMonth.from(date);
        int lastDate = yearMonth.lengthOfMonth();
        LocalDate firstDay = selectedDate.withDayOfMonth(1);
        int dayofWeek = firstDay.getDayOfWeek().getValue();
        for (int i = 1; i < 42; i++){
            if (1 <= dayofWeek || i > lastDate + dayofWeek){
                dayList.add("");
            }else {
                dayList.add(String.valueOf(i - dayofWeek));
            }
        }
        return dayList;
    }

}