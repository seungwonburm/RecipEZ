package com.example.orderez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.orderez.homepage.Homepage_Items;

public class Update_Delete extends AppCompatActivity {

    EditText itemName, itemAmount, itemexpirationDate, itemMemo;
    Spinner itemUnit;
    Button submit, delete;
    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        itemName = (EditText) findViewById(R.id.editItemName);
        itemAmount = (EditText) findViewById(R.id.editItemAmount);
        itemexpirationDate = (EditText) findViewById(R.id.editExpirationDate);
        itemMemo = (EditText) findViewById(R.id.editMemo);
        itemUnit = (Spinner) findViewById(R.id.editItemUnit);

        //이부분에 DB에서 꺼낸 값들을 넣어주시면 됩니다.
        itemName.setText("여기에 들어갈 String을 넣어주세요.");
        itemAmount.setText("여기에 들어갈 String을 넣어주세요.");
        itemexpirationDate.setText("여기에 들어갈 String을 넣어주세요.");
        itemMemo.setText("여기에 들어갈 String을 넣어주세요.");
        //유닛은 어떻게 설정하는지 찾아보겠음.


        submit = (Button) findViewById(R.id.editSubmitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update Process


                //Back to Item List
                Intent backtoItem = new Intent(getApplicationContext(), Homepage_Items.class);
                backtoItem.putExtra("userId", Homepage_Items.id);
                startActivity(backtoItem);
            }
        });

        delete = (Button) findViewById(R.id.deleteBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete Process


                //Back to Item List
                Intent backtoItem = new Intent(getApplicationContext(), Homepage_Items.class);
                backtoItem.putExtra("userId", Homepage_Items.id);
                startActivity(backtoItem);
            }
        });
    }

    //Overrides BackKeyHandler's onBackPressed method
    //Finish the app if user clicks the back button twice in 2 seconds.
    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed_BacktoItem("You will lose your progress if you click back button again.");
    }
}