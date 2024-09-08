package com.example.priestbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ShopStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_status);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ShopStatus.this,MainActivity.class);
        startActivity(intent);
    }
}