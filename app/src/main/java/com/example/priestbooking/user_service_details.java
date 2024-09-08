package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class user_service_details extends AppCompatActivity {

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String strordercount,strprice,strlocation,strtiming;
    String strpriestname="0";
    String strpriestmob="0";
    String service;
    String bookdate;
    String booktime;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_service_details);

        EditText edtloc = (EditText) findViewById(R.id.edtloc);
        TextView txtprice =(TextView)findViewById(R.id.txtprice);
        btnDatePicker=(Button)findViewById(R.id.btndate);
        btnTimePicker=(Button)findViewById(R.id.btntime);
        Button btnbook=(Button)findViewById(R.id.btnbook);

        SharedPreferences sharedPreferences=getSharedPreferences("type",MODE_PRIVATE);
        String sharedtype=sharedPreferences.getString("type","0");
        String sharemobile=sharedPreferences.getString("mobile","0");
        CircularImageView simg = (CircularImageView)findViewById(R.id.serviceimg);
        service =getIntent().getStringExtra("servicetype");

        if(service.equals("Vratalu")){
            simg.setBackgroundResource(R.drawable.vratalu);
        }
        if(service.equals("Vivaham")){
            simg.setBackgroundResource(R.drawable.vivaham);
        }
        if(service.equals("Homam")){
            simg.setBackgroundResource(R.drawable.homam);
        }
        if(service.equals("Vahanamu")){
            simg.setBackgroundResource(R.drawable.car);
        }
        if(service.equals("Gruhapravesham")){
            simg.setBackgroundResource(R.drawable.house);
        }
        if(service.equals("Shubakaryalu")){
            simg.setBackgroundResource(R.drawable.subhakaryalu);
        }
        if(service.equals("House")){
            simg.setBackgroundResource(R.drawable.vasthu_house);
        }
        if(service.equals("Commercial")){
            simg.setBackgroundResource(R.drawable.building);
        }
        if(service.equals("Temple")){
            simg.setBackgroundResource(R.drawable.temple);
        }




        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(user_service_details.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                bookdate=dayOfMonth+"-"+(month+1)+"-"+year;
                                btnDatePicker.setText(bookdate);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(user_service_details.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                booktime=String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM");
                                btnTimePicker.setText(booktime);
                            }

                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        //For getting Services prices

        DatabaseReference myref = database.getReference().child("services").child(service);
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                strprice=snapshot.child("price").getValue(String.class);
                txtprice.setText(strprice);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //For Getting order count;

        DatabaseReference ordercountref = database.getReference().child("ordercount");
        ordercountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                strordercount=snapshot.child("orderid").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //For getting user name

        DatabaseReference usernameref = database.getReference().child("userAuth").child("mobiles").child(sharemobile);
        usernameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                username=snapshot.child("Name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strlocation=edtloc.getText().toString();
                strtiming=bookdate+","+booktime;

                //Setting order details in userrbookings
                int num=Integer.parseInt(strordercount)+1;
                String orderid = "ordid"+num;

                DatabaseReference userorderref=database.getReference().child("userAuth").child("mobiles").child(sharemobile).child("Bookings").child("Active").child(orderid);
                userorderref.child("service").setValue(service);
                userorderref.child("price").setValue(strprice);
                userorderref.child("priestname").setValue(strpriestname);
                userorderref.child("priestmobile").setValue(strpriestmob);
                userorderref.child("status").setValue("Confirmation Pending");
                userorderref.child("location").setValue(strlocation);
                userorderref.child("timings").setValue(strtiming);

                DatabaseReference pendingorderref=database.getReference().child("pendingbookings").child(orderid);
                pendingorderref.child("uname").setValue(username);
                pendingorderref.child("umobile").setValue(sharemobile);
                pendingorderref.child("ulocation").setValue(strlocation);
                pendingorderref.child("reqservice").setValue(service);
                pendingorderref.child("timings").setValue(strtiming);
                pendingorderref.child("price").setValue(strprice);



                DatabaseReference increasingorderref=database.getReference().child("ordercount");
                increasingorderref.child("orderid").setValue(""+num);

                Toast.makeText(getApplicationContext(),"Successfully booked",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(user_service_details.this,User_MainActivity.class);
                startActivity(intent);

            }
        });

        TextView txtservice=(TextView)findViewById(R.id.txtservice);
        txtservice.setText(service);

    }

}