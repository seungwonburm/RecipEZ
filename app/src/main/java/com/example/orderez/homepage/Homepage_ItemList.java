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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homepage_ItemList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homepage_ItemList extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Homepage_ItemList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Homepage_ItemList newInstance(String param1, String param2) {
        Homepage_ItemList fragment = new Homepage_ItemList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ArrayList<itemList> itemNames;
    ListView itemList_ListView;
    FloatingActionButton fab;
    private static ItemListAdapter itemListAdapter;


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
        View contenView = inflater.inflate(R.layout.fragment_item_list, container, false);




        itemNames = new ArrayList<>();
        itemNames.add(new itemList("Banana"));
        itemNames.add(new itemList("Banana2"));
        itemNames.add(new itemList("Banana3"));
        itemNames.add(new itemList("Banana4"));
        itemNames.add(new itemList("Banana5"));
        itemNames.add(new itemList("Banana6"));

        Bundle bundle = getArguments();
        if (bundle != null){
            itemNames.add(new itemList(bundle.getString("userID")));
        }


        itemList_ListView = (ListView) contenView.findViewById(R.id.listMenu);
        itemListAdapter = new ItemListAdapter(getContext(),itemNames);

        itemList_ListView.setAdapter(itemListAdapter);

        fab = (FloatingActionButton) contenView.findViewById(R.id.floatingActionButtonSetting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddnewItems.class));
            }
        });

        return contenView;


    }


    public static class ItemListAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

        private Context context;
        private List list;
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
        }

        class ViewHolder {
            public TextView list_name;
        }

        public ItemListAdapter(@NonNull Context context, ArrayList list) {
            super(context,0, list);
            this.context = context;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null){
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                convertView = layoutInflater.inflate(R.layout.itemlist_listview_item, parent, false);
            }
            viewHolder = new ViewHolder();
            viewHolder.list_name = (TextView) convertView.findViewById(R.id.itemlistCategoryText);
            final itemList setList = (itemList) list.get(position);

            viewHolder.list_name.setText(setList.getCategory());
            return convertView;
        }

    }
}



class itemList {
    private String category;

    public itemList(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }
}