package com.example.sqlitedatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabaseManager sqLiteDatabaseManager;
    EditText userName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.username);
        address = (EditText) findViewById(R.id.address);
        sqLiteDatabaseManager = new SQLiteDatabaseManager(this);
        Toast.makeText(MainActivity.this,"Simple example clicked",Toast.LENGTH_LONG).show();


    }

    public void addUser(View view) {
        String userValue = userName.getText().toString();
        String userAddress = address.getText().toString();
        long id = sqLiteDatabaseManager.insertData(userValue, userAddress);
        if (id > 0) {
            Log.d("added", "addUser: "+id);
            Toast.makeText(view.getContext(), "Successful", Toast.LENGTH_LONG).show();
            userName.setText("");
        } else {
            Toast.makeText(view.getContext(), "Failed", Toast.LENGTH_LONG).show();
        }

    }

    public void viewDetails(View view) {
        String users = sqLiteDatabaseManager.getData();
        Toast.makeText(this,users, Toast.LENGTH_LONG).show();

    }

    public void getAddress(View view) {
        String address = sqLiteDatabaseManager.getAddress(userName.getText().toString());
        Toast.makeText(this,address, Toast.LENGTH_LONG).show();

    }

    public void getUserId(View view) {
        String userValue = userName.getText().toString();
        String userAddress = address.getText().toString();
        String userId = sqLiteDatabaseManager.getUserId(userValue,userAddress);
        Toast.makeText(this,"UserID access: "+userId,Toast.LENGTH_LONG).show();

    }

    public void updateName(View view) {
        String currentName = userName.getText().toString();
        String newName = address.getText().toString();
        int count = sqLiteDatabaseManager.updateName(currentName,newName);
        Toast.makeText(this,"Updated"+count,Toast.LENGTH_LONG).show();

    }

    public void updateAddress(View view) {
        String currentName = userName.getText().toString();
        String newAddress = address.getText().toString();
        int count = sqLiteDatabaseManager.updateAddress(currentName,newAddress);
        Toast.makeText(this,"Updated"+count,Toast.LENGTH_LONG).show();
    }

    public void delete(View view) {
        int count = sqLiteDatabaseManager.delete(userName.getText().toString());
        Toast.makeText(this,"User Deleted:" + count,Toast.LENGTH_LONG).show();
    }
}