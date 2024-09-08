package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SingleActiveActivity extends AppCompatActivity {

    String userservice;
    String username;
    String usermobile;
    String userlocation;
    String userservicetimings;
    String userserviceprice;
    String priestname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_active);

        TextView txtservice = (TextView)findViewById(R.id.txtservice);
        TextView txtusername = (TextView)findViewById(R.id.txtname);
        TextView txtusermobile = (TextView)findViewById(R.id.txtmob);
        TextView txtloc = (TextView)findViewById(R.id.txtloc);
        TextView txtservtiming = (TextView)findViewById(R.id.txtservtiming);
        TextView txtservprice = (TextView)findViewById(R.id.txtservprice);
        Button btnfinished=(Button)findViewById(R.id.btnfinished);


        TextView txtorderid = (TextView)findViewById(R.id.txtorderid);
        String orderid = getIntent().getStringExtra("orderid");
        txtorderid.setText(orderid);

        SharedPreferences sharedPreferences=SingleActiveActivity.this.getSharedPreferences("type",MODE_PRIVATE);
        String sharedtype=sharedPreferences.getString("type","0");
        String sharemobile=sharedPreferences.getString("mobile","0");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference orderref = firebaseDatabase.getReference().child("PSignupReq").child(sharemobile).child("Bookings").child("Active").child(orderid);

        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                username=snapshot.child("uname").getValue(String.class);
                usermobile=snapshot.child("umobile").getValue(String.class);
                userlocation=snapshot.child("ulocation").getValue(String.class);
                userservicetimings=snapshot.child("timings").getValue(String.class);
                userserviceprice=snapshot.child("price").getValue(String.class);
                userservice=snapshot.child("reqservice").getValue(String.class);

                txtusername.setText(username);
                txtusermobile.setText(usermobile);
                txtloc.setText(userlocation);
                txtservtiming.setText(userservicetimings);
                txtservprice.setText(userserviceprice);
                txtservice.setText(userservice);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        btnfinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference priestprevref = firebaseDatabase.getReference().child("PSignupReq").child(sharemobile).child("Bookings").child("Previous").child(orderid);

                priestprevref.child("uname").setValue(username);
                priestprevref.child("umobile").setValue(usermobile);
                priestprevref.child("ulocation").setValue(userlocation);
                priestprevref.child("timings").setValue(userservicetimings);
                priestprevref.child("price").setValue(userserviceprice);
                priestprevref.child("reqservice").setValue(userservice);

                DatabaseReference userprevref=firebaseDatabase.getReference().child("userAuth").child("mobiles").child(usermobile).child("Bookings").child("Previous").child(orderid);

                userprevref.child("uname").setValue(username);
                userprevref.child("umobile").setValue(usermobile);
                userprevref.child("ulocation").setValue(userlocation);
                userprevref.child("timings").setValue(userservicetimings);
                userprevref.child("price").setValue(userserviceprice);
                userprevref.child("reqservice").setValue(userservice);

                DatabaseReference useractiveref=firebaseDatabase.getReference().child("userAuth").child("mobiles").child(usermobile).child("Bookings").child("Active").child(orderid);
                DatabaseReference priestactiveref = firebaseDatabase.getReference().child("PSignupReq").child(sharemobile).child("Bookings").child("Active").child(orderid);

                useractiveref.removeValue();
                priestactiveref.removeValue();

                Intent intent = new Intent(SingleActiveActivity.this,Priest_MainActivity.class);
                startActivity(intent);

            }
        });

    }
}