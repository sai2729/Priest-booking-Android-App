package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

public class ShopSignup extends AppCompatActivity {


    EditText edtname,edtmobile,edtlocation;
    Button btnstart,btnend,btnsignup;
    ProgressBar progressBar;
    TextView txtinfo;
    FirebaseAuth mauth;

    String skname,skmobile,sklocation,skstart,skend,skstatus,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_signup);

        edtname=(EditText)findViewById(R.id.edtname);
        edtmobile=(EditText)findViewById(R.id.edtmobile);
        edtlocation=(EditText)findViewById(R.id.edtloc);
        btnstart=(Button) findViewById(R.id.btnstart);
        btnend=(Button)findViewById(R.id.btnend);
        btnsignup=(Button)findViewById(R.id.btnsignup);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        txtinfo=(TextView)findViewById(R.id.txtinfo);
        ArrayList<String> sklist=new ArrayList<String>();


        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        mauth=FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("SkSignupReq");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String gstr=dataSnapshot.getKey();
                    sklist.add(gstr);
                }
                Log.d("","Fetched");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ShopSignup.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                btnstart.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                                Log.d("",hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ShopSignup.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                btnend.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                                Log.d("",hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                if(edtname.getText().toString().length()==0){
                    edtname.setError("Username Required");
                    flag=1;
                }
                if(edtmobile.getText().toString().length()==0){
                    edtmobile.setError("Enter Mobile Number");
                    flag=1;
                }
                if(edtmobile.getText().toString().length()!=10){
                    edtmobile.setError("Mobile Number Invalid");
                    flag=1;
                }
                if(edtlocation.getText().toString().length()==0){
                    edtlocation.setError("Services are Required");
                    flag=1;
                }
                if(btnstart.getText().toString().equals("00:00 AM")){
                    btnstart.setError("Select Starting Time");
                    flag=1;
                }
                if(btnend.getText().toString().equals("00:00 PM")){
                    btnend.setError("Select Ending Time");
                    flag=1;
                }
                if(flag==0){

                    btnsignup.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    skname=edtname.getText().toString();
                    skmobile=edtmobile.getText().toString();
                    sklocation=edtlocation.getText().toString();
                    skstart=btnstart.getText().toString();
                    skend=btnend.getText().toString();
                    skstatus="0";

                    int already_there=0;

                    for(String temp:sklist){
                        if(skmobile.equals(temp)){
                            already_there=1;
                            break;
                        }
                    }

                    if(already_there==1){
                        btnsignup.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        txtinfo.setText("User Already Registered !! Please Login In");
                        txtinfo.setTextColor(Color.parseColor("#ff0e00"));
                        Toast.makeText(getApplicationContext(),"User Already Registered",Toast.LENGTH_SHORT).show();
                        Log.d("","User Already Registered");
                    }
                    else{

                        ShopSignupHelper shopSignupHelper=new ShopSignupHelper(skname,skmobile,sklocation,skstart,skend,skstatus);


                        DatabaseReference shop_signup_ref=firebaseDatabase.getReference().child("SkSignupReq");
                        DatabaseReference shopdata=shop_signup_ref.child(shopSignupHelper.skmobile);
                        Log.d("",shopSignupHelper.skmobile);

                        shopdata.setValue(shopSignupHelper);

                        Log.d("",""+skname+" "+skmobile+" "+sklocation+" "+skstart+" "+skend);


                        progressBar.setVisibility(View.GONE);
                        btnsignup.setVisibility(View.INVISIBLE);
                        Intent intent=new Intent(ShopSignup.this,ShopStatus.class);
                        startActivity(intent);
                    }

                }
            }
        });


    }

    public void go_to_login(View view) {
        Intent intent=new Intent(ShopSignup.this,MainActivity.class);
        startActivity(intent);
    }
}