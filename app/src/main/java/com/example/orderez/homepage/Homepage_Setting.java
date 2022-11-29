package com.example.orderez.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderez.R;
//import com.example.orderez.homepage.settingCategories.Setting_Account;
//import com.example.orderez.homepage.settingCategories.Setting_User_Profile;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_setting, container, false);

        settingNames = new ArrayList<>();
        settingNames.add(new settingList("User Profile"));
        settingNames.add(new settingList("Account setting"));

        settingListView = (ListView) contentView.findViewById(R.id.settingMenu);
        settingAdapter = new SettingAdapter(getContext(),settingNames);

        settingListView.setAdapter(settingAdapter);

        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
//                    startActivity(new Intent(getActivity(), Setting_Account.class));
                }else if (i == 1){
//                    startActivity(new Intent(getActivity(), Setting_User_Profile.class));
                }
            }
        });

        return contentView;
    }

    public static class SettingAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

        private Context context;
        private List list;

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
        }

        class ViewHolder {
            public TextView category_name;
        }

        public SettingAdapter(@NonNull Context context, ArrayList list) {

            super(context,0,list);
            this.context = context;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            final ViewHolder viewHolder;
            if (convertView == null){
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                convertView = layoutInflater.inflate(R.layout.setting_listview_item, parent, false);
            }
            viewHolder = new ViewHolder();
            viewHolder.category_name = (TextView) convertView.findViewById(R.id.settingCategoryText);
            final settingList setList = (settingList) list.get(position);

            viewHolder.category_name.setText(setList.getCategory());


            return convertView;
        }

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