package com.example.orderez;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homepage_Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homepage_Setting extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Homepage_Setting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Homepage_Setting newInstance(String param1, String param2) {
        Homepage_Setting fragment = new Homepage_Setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ArrayList<settingList> settingNames;
    ListView settingListView;
    private static SettingAdapter settingAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_setting, container, false);

        settingNames = new ArrayList<>();
        settingNames.add(new settingList("User Profile"));
        settingNames.add(new settingList("Account setting"));
        settingNames.add(new settingList("what else?"));

        settingListView = (ListView) contentView.findViewById(R.id.settingMenu);
        settingAdapter = new SettingAdapter(getContext(),settingNames);

        settingListView.setAdapter(settingAdapter);
//        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
//
//                String selectedItem = (String) view.findViewById(R.id.settingCategoryText).getTag().toString();
//                Toast.makeText(getContext(), "Clicked:  " + selectedItem, Toast.LENGTH_SHORT).show();
//            }
//        });

        return contentView;
    }
}

class settingList {
    private String category;

    public settingList(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }
}