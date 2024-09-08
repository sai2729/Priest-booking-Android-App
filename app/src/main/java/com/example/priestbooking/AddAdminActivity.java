package com.example.priestbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAdminActivity extends AppCompatActivity {

    String aname;
    String amob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        EditText edtmob = (EditText)findViewById(R.id.edtaddmob);
        EditText edtname = (EditText)findViewById(R.id.edtaddname);
        Button btnadd = (Button)findViewById(R.id.btnadd);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference admin_ref = firebaseDatabase.getReference().child("Admin");

                aname=edtname.getText().toString();
                amob=edtmob.getText().toString();

                admin_ref.child(amob).setValue(aname);
            }
        });

    }
}