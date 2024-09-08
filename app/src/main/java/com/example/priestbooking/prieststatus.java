package com.example.priestbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class prieststatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prieststatus);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(prieststatus.this,MainActivity.class);
        startActivity(intent);
    }
}