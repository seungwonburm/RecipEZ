package com.example.orderez.homepage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderez.BackKeyHandler;
import com.example.orderez.DatabaseManager;
import com.example.orderez.MainActivity;
import com.example.orderez.R;
import com.example.orderez.homepage.settingCategories.Homepage_SettingCategories;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Homepage_Items extends AppCompatActivity {

    RecyclerView itemList;
    ItemList_Adapter itemList_adapter;
    FloatingActionButton addBtn;
    Button generate;
    public static String id;
    DatabaseManager theDb;
    Cursor cursor, cursor2;
    String var0, var1, var2, var3, var4, currentDate;
    Boolean noItem = true;
    long dateDifference=Integer.MAX_VALUE, currentDifference=0;
    public static String temp = "", recipe="";
    TextView expFirst, itemList_MyItemTV;
    Spinner sortItems;

    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);

    //Bottom Navigation bar
    BottomNavigationView bottomNavigationView_Month;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_items);


        itemList = findViewById(R.id.itemList);




        theDb= new DatabaseManager(this);

        generate = (Button) findViewById(R.id.generate);
        expFirst = (TextView) findViewById(R.id.expFirst);
        currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()); //Grabs today's date and stores into selected date Format
        itemList_MyItemTV = (TextView) findViewById(R.id.itemList_MyItemTV);


        Intent intent = getIntent();
        id = intent.getStringExtra("userId"); //UserId from Previous Activity
        cursor = theDb.searchItemId(id); // Cursor for Item Table
        cursor2 = theDb.searchId(id); // Cursor for User Table
        sortItems = (Spinner) findViewById(R.id.itemSorting);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,R.array.Sorting,android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        sortItems.setAdapter(sortAdapter);


        if (cursor2.getCount()<=0){ //If No User Account Found
            Toast.makeText(getApplicationContext(), "Account Not Initialized!!", Toast.LENGTH_LONG).show();
        } else if ((cursor2.moveToFirst() && cursor2 != null)){  //If User Account Found

            String firstName = cursor2.getString(cursor2.getColumnIndexOrThrow("first"));
            itemList_MyItemTV.setText(firstName + "'s Item List"); // Sets title using user's first name
        }else if (cursor2 == null){
            Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
        }

        sortItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (sortItems.getItemAtPosition(position).equals("Recently added")){ //Sorts items by recently added
                    itemList_adapter = new ItemList_Adapter(getApplicationContext());
                    id = intent.getStringExtra("userId"); //id from previous activity
                    cursor = theDb.searchItemId(id); //Search User's Items
                    if (cursor.getCount()<=0){ //If no items available
                        generate.setEnabled(false);
                        noItem = true;
                        Toast.makeText(getApplicationContext(), "No Data Yet!!", Toast.LENGTH_LONG).show();
                    }
                    else if (cursor.moveToFirst() && cursor != null) { //If items available
                        noItem = false;
                        generate.setEnabled(true);
                        temp = "";
                        recipe = "";
                        do{
                            var0 = cursor.getString(cursor.getColumnIndexOrThrow("name")); //Stores elements values from DB into String
                            var1 = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
                            var2 = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                            var3 = cursor.getString(cursor.getColumnIndexOrThrow("expire_date"));
                            var4 = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
                            temp += var0 + ", "; // Adds item names into a String, separated by comma and whitespace

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            LocalDate start = LocalDate.parse(currentDate,formatter); //Date Format for today's date
                            LocalDate end = LocalDate.parse(var3,formatter); // Date Format for item's expiration date
                            currentDifference=ChronoUnit.DAYS.between(start, end); //Date difference between two dates
                            if (dateDifference>currentDifference){
                                dateDifference = currentDifference; // Calculates and updates item expiring first
                            }

                            itemList_adapter.addItem(new ItemList_Manager(var0, var2,  var1, var3, var4)); // Item is added
                        } while (cursor.moveToNext());
                        recipe = temp.substring(0,temp.length()-2); //Removes last comma and whitespace
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        itemList.setLayoutManager(layoutManager); //Sets item adapter and layoutmanager
                        itemList.setAdapter(itemList_adapter);

                    } else if (cursor == null){
                        noItem = true;
                        generate.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                    itemList.setAdapter(itemList_adapter);
                }else if (sortItems.getItemAtPosition(position).equals("A-Z")){ //Sorts items by A-Z
                    itemList_adapter = new ItemList_Adapter(getApplicationContext());
                    ArrayList  newList = new ArrayList<>();
                    id = intent.getStringExtra("userId"); //id from previous activity
                    cursor = theDb.searchItemId(id);//Search User's Items
                    if (cursor.getCount()<=0){
                        generate.setEnabled(false);
                        noItem = true;
                        Toast.makeText(getApplicationContext(), "No Data Yet!!", Toast.LENGTH_LONG).show();
                    }
                    else if (cursor.moveToFirst() && cursor != null) { //If items available
                        noItem = false;
                        generate.setEnabled(true);
                        temp = "";
                        recipe = "";
                        do{
                            var0 = cursor.getString(cursor.getColumnIndexOrThrow("name")); //Stores elements values from DB into String
                            var1 = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
                            var2 = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                            var3 = cursor.getString(cursor.getColumnIndexOrThrow("expire_date"));
                            var4 = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
                            temp += var0 + ", "; // Adds item names into a String, separated by comma and whitespace

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");  //Date Format for today's date
                            LocalDate start = LocalDate.parse(currentDate,formatter);
                            LocalDate end = LocalDate.parse(var3,formatter);
                            currentDifference=ChronoUnit.DAYS.between(start, end);
                            if (dateDifference>currentDifference){
                                dateDifference = currentDifference;
                            }

                            itemList_adapter.addItem(new ItemList_Manager(var0, var2,  var1, var3, var4));

                        } while (cursor.moveToNext());
                        recipe = temp.substring(0,temp.length()-2);
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        itemList_adapter.sortItemsA_Z(itemList_adapter);
                        itemList.setLayoutManager(layoutManager);
                        itemList.setAdapter(itemList_adapter);

                    } else if (cursor == null){
                        noItem = true;
                        generate.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                    itemList.setAdapter(itemList_adapter);
                }else if (sortItems.getItemAtPosition(position).equals("Z-A")){
                    itemList_adapter = new ItemList_Adapter(getApplicationContext());
                    ArrayList  newList = new ArrayList<>();
                    id = intent.getStringExtra("userId"); //id from previous activity
                    cursor = theDb.searchItemId(id); //Search User's Items
                    if (cursor.getCount()<=0){
                        generate.setEnabled(false);
                        noItem = true;
                        Toast.makeText(getApplicationContext(), "No Data Yet!!", Toast.LENGTH_LONG).show();
                    }
                    else if (cursor.moveToFirst() && cursor != null) { //If items available
                        noItem = false;
                        generate.setEnabled(true);
                        temp = "";
                        recipe = "";
                        do{
                            var0 = cursor.getString(cursor.getColumnIndexOrThrow("name")); //Stores elements values from DB into String
                            var1 = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
                            var2 = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                            var3 = cursor.getString(cursor.getColumnIndexOrThrow("expire_date"));
                            var4 = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
                            temp += var0 + ", "; // Adds item names into a String, separated by comma and whitespace

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //Date Format for today's date
                            LocalDate start = LocalDate.parse(currentDate,formatter);
                            LocalDate end = LocalDate.parse(var3,formatter);
                            currentDifference=ChronoUnit.DAYS.between(start, end);
                            if (dateDifference>currentDifference){
                                dateDifference = currentDifference;
                            }

                            itemList_adapter.addItem(new ItemList_Manager(var0, var2,  var1, var3, var4));

                        } while (cursor.moveToNext());
                        recipe = temp.substring(0,temp.length()-2);
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        itemList_adapter.sortItemsZ_A(itemList_adapter);
                        itemList.setLayoutManager(layoutManager);
                        itemList.setAdapter(itemList_adapter);

                    } else if (cursor == null){
                        noItem = true;
                        generate.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                    itemList.setAdapter(itemList_adapter);

                }else if (sortItems.getItemAtPosition(position).equals("Expires first")){
                    itemList_adapter = new ItemList_Adapter(getApplicationContext());
                    ArrayList  newList = new ArrayList<>();
                    id = intent.getStringExtra("userId"); //id from previous activity
                    cursor = theDb.searchItemId(id); //Search User's Items
                    if (cursor.getCount()<=0){
                        generate.setEnabled(false);
                        noItem = true;
                        Toast.makeText(getApplicationContext(), "No Data Yet!!", Toast.LENGTH_LONG).show();
                    }
                    else if (cursor.moveToFirst() && cursor != null) { //If items available
                        noItem = false;
                        generate.setEnabled(true);
                        temp = "";
                        recipe = "";
                        do{
                            var0 = cursor.getString(cursor.getColumnIndexOrThrow("name")); //Stores elements values from DB into String
                            var1 = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
                            var2 = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                            var3 = cursor.getString(cursor.getColumnIndexOrThrow("expire_date"));
                            var4 = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
                            temp += var0 + ", "; // Adds item names into a String, separated by comma and whitespace

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //Date Format for today's date
                            LocalDate start = LocalDate.parse(currentDate,formatter);
                            LocalDate end = LocalDate.parse(var3,formatter);
                            currentDifference=ChronoUnit.DAYS.between(start, end);
                            if (dateDifference>currentDifference){
                                dateDifference = currentDifference;
                            }

                            itemList_adapter.addItem(new ItemList_Manager(var0, var2,  var1, var3, var4));

                        } while (cursor.moveToNext());
                        recipe = temp.substring(0,temp.length()-2);
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        itemList_adapter.sortItemsExpFIrst(itemList_adapter);
                        itemList.setLayoutManager(layoutManager);
                        itemList.setAdapter(itemList_adapter);

                    } else if (cursor == null){
                        noItem = true;
                        generate.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                    itemList.setAdapter(itemList_adapter);

                }else if (sortItems.getItemAtPosition(position).equals("Expires last")){
                    itemList_adapter = new ItemList_Adapter(getApplicationContext());
                    ArrayList  newList = new ArrayList<>();
                    id = intent.getStringExtra("userId"); //id from previous activity
                    cursor = theDb.searchItemId(id); //Search User's Items
                    if (cursor.getCount()<=0){
                        generate.setEnabled(false);
                        noItem = true;
                        Toast.makeText(getApplicationContext(), "No Data Yet!!", Toast.LENGTH_LONG).show();
                    }
                    else if (cursor.moveToFirst() && cursor != null) { //If items available
                        noItem = false;
                        generate.setEnabled(true);
                        temp = "";
                        recipe = "";
                        do{
                            var0 = cursor.getString(cursor.getColumnIndexOrThrow("name")); //Stores elements values from DB into String
                            var1 = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
                            var2 = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                            var3 = cursor.getString(cursor.getColumnIndexOrThrow("expire_date"));
                            var4 = cursor.getString(cursor.getColumnIndexOrThrow("memo"));
                            temp += var0 + ", "; // Adds item names into a String, separated by comma and whitespace

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            LocalDate start = LocalDate.parse(currentDate,formatter);
                            LocalDate end = LocalDate.parse(var3,formatter);
                            currentDifference=ChronoUnit.DAYS.between(start, end);
                            if (dateDifference>currentDifference){
                                dateDifference = currentDifference;
                            }

                            itemList_adapter.addItem(new ItemList_Manager(var0, var2,  var1, var3, var4));

                        } while (cursor.moveToNext());
                        recipe = temp.substring(0,temp.length()-2);
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        itemList_adapter.sortItemsExpLast(itemList_adapter);
                        itemList.setLayoutManager(layoutManager);
                        itemList.setAdapter(itemList_adapter);

                    } else if (cursor == null){
                        noItem = true;
                        generate.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "NO DATA!!", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                    itemList.setAdapter(itemList_adapter);
                }
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, Math.toIntExact(dateDifference));
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String expiryDate = sdf.format(c.getTime());

                String message = "Today's Date: " + currentDate +"\n";
                if (noItem == true)
                    message += "There is no expiring item.";
                else if (dateDifference<0)
                    message += "At least one item has expired on " + expiryDate;
                else if (dateDifference==0)
                    message += "At least one item is expiring today";
                else
                    message += "At least one item is expiring on " + expiryDate;
                expFirst.setText(message + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent generateIntent = new Intent(getApplicationContext(), MainActivity.class);
//                generateIntent.putExtra("recipe", recipe);
                startActivity(generateIntent);

            }
        });

        //add Button
        addBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAddPage = new Intent(getApplicationContext(), AddnewItems.class);
                gotoAddPage.putExtra("userId", id);
                startActivity(gotoAddPage);
                finish();
            }
        });
        //Menu Bar
        bottomNavigationView_Month= (BottomNavigationView) findViewById(R.id.item_menubar);
        bottomNavigationView_Month.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settingIcon_02:
                        Intent intent2 = new Intent(getApplicationContext(), Homepage_SettingCategories.class);
                        intent2.putExtra("userId", id);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.itemListIcon_02:
                        Intent intent1 = new Intent(getApplicationContext(), Homepage_Items.class);
                        intent1.putExtra("userId",id);
                        startActivity(intent1);
                        finish();
                        break;
                }
                return false;
            }
        });

    }// onCreate


    //Overrides BackKeyHandler's onBackPressed method
    //Finish the app if user clicks the back button twice in 2 seconds.
    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed();
    }



}
