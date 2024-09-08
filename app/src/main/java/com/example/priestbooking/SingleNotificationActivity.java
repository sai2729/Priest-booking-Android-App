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

public class SingleNotificationActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_single_notification);

        TextView txtservice = (TextView)findViewById(R.id.txtservice);
        TextView txtusername = (TextView)findViewById(R.id.txtname);
        TextView txtusermobile = (TextView)findViewById(R.id.txtmob);
        TextView txtloc = (TextView)findViewById(R.id.txtloc);
        TextView txtservtiming = (TextView)findViewById(R.id.txtservtiming);
        TextView txtservprice = (TextView)findViewById(R.id.txtservprice);
        Button btnaccept = (Button)findViewById(R.id.btnaccept);
        Button btnclose = (Button)findViewById(R.id.btnclose);

        TextView txtorderid = (TextView)findViewById(R.id.txtorderid);
        String orderid = getIntent().getStringExtra("orderid");
        txtorderid.setText(orderid);

        SharedPreferences sharedPreferences=SingleNotificationActivity.this.getSharedPreferences("type",MODE_PRIVATE);
        String sharedtype=sharedPreferences.getString("type","0");
        String sharemobile=sharedPreferences.getString("mobile","0");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference orderref = firebaseDatabase.getReference().child("pendingbookings").child(orderid);


        //Setting up text views

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

        //getting priest name

        DatabaseReference pnameref = firebaseDatabase.getReference().child("PSignupReq").child(sharemobile);
        pnameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                priestname=snapshot.child("pname").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //Moving priest data from pending bookings to priest active bookings

        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference priestref = firebaseDatabase.getReference().child("PSignupReq").child(sharemobile).child("Bookings").child("Active").child(orderid);
                priestref.child("uname").setValue(username);
                priestref.child("umobile").setValue(usermobile);
                priestref.child("ulocation").setValue(userlocation);
                priestref.child("timings").setValue(userservicetimings);
                priestref.child("price").setValue(userserviceprice);
                priestref.child("reqservice").setValue(userservice);

                DatabaseReference userref=firebaseDatabase.getReference().child("userAuth").child("mobiles").child(usermobile).child("Bookings").child("Active").child(orderid);
                userref.child("priestname").setValue(priestname);
                userref.child("priestmobile").setValue(sharemobile);

                DatabaseReference allordref = firebaseDatabase.getReference().child("allorders").child(orderid);
                allordref.child("uname").setValue(username);
                allordref.child("umobile").setValue(usermobile);
                allordref.child("ulocation").setValue(userlocation);
                allordref.child("timings").setValue(userservicetimings);
                allordref.child("price").setValue(userserviceprice);
                allordref.child("reqservice").setValue(userservice);

                orderref.removeValue();

                Intent intent =new Intent(SingleNotificationActivity.this,Priest_MainActivity.class);
                startActivity(intent);

            }
        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleNotificationActivity.this,Priest_MainActivity.class);
                startActivity(intent);
            }
        });

    }
}