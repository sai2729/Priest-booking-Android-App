package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserSignup extends AppCompatActivity {
    EditText edtname,edtmobile;
    TextView txtinfo;
    Button btnsignup;
    ProgressBar progressBar;
    String uname;
    String umobile;
    ArrayList<String> userList=new ArrayList<String>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        edtname=(EditText)findViewById(R.id.edtname);
        edtmobile=(EditText)findViewById(R.id.mid);
        txtinfo=(TextView)findViewById(R.id.txtinfo);
        btnsignup=(Button)findViewById(R.id.btnsignup);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar_sending_otp);
        ArrayList<String> userList=new ArrayList<String>();

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("userAuth").child("mobiles");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String gstr=dataSnapshot.getKey();
                    userList.add(gstr);
                }
                Log.d("","Fetched");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnsignup.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                uname=edtname.getText().toString();
                umobile=edtmobile.getText().toString();

                if(uname.length()==0){
                    edtname.setError("Please Enter your name");
                    btnsignup.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                if(umobile.length()!=10){
                    edtmobile.setError("Mobile Number is Invalid");
                    btnsignup.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                if(uname.length()!=0 && umobile.length()==10){
                    int flag=0;
                    Log.d("",String.valueOf(flag));
                    for(String temp:userList){
                        if(umobile.equals(temp)){
                            flag=1;
                            break;
                        }
                    }
                    if(flag==1){
                        btnsignup.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        txtinfo.setText("User Already Registered !! Please Login In");
                        txtinfo.setTextColor(Color.parseColor("#ff0e00"));
                        Toast.makeText(getApplicationContext(),"User Already Registered",Toast.LENGTH_SHORT).show();
                        Log.d("","User Already Registered");
                    }
                    else {
                        mAuth=FirebaseAuth.getInstance();
                        DatabaseReference myref=firebaseDatabase.getReference().child("userAuth").child("mobiles").child(umobile);
                        myref.child("Name").setValue(uname);
                        myref.child("Mobile").setValue(umobile);
                        btnsignup.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        txtinfo.setText("User Succesfully Registered !");
                        txtinfo.setTextColor(Color.parseColor("#00FF3E"));

                        Toast.makeText(getApplicationContext(),"User Successfully Registered",Toast.LENGTH_SHORT).show();
                        Log.d("","User Succesfully Registered");
                    }
                    /*
                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference=firebaseDatabase.getReference().child("userAuth").child("mobiles");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            int flag=0;
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                String gstr=dataSnapshot.getKey();
                                assert gstr != null;
                                if(gstr.equals(umobile)){
                                    flag=1;
                                    break;
                                }
                            }
                            if(flag==1 && count==0){
                                Log.d("","User Already Exists");
                                Intent intent=new Intent(UserSignup.this,MainActivity.class);
                                intent.putExtra("flag","1");
                                intent.putExtra("done","0");
                            }
                            else {
                                Log.d("","User Registered");
                                DatabaseReference myref=firebaseDatabase.getReference().child("userAuth").child("mobiles").child(umobile);
                                myref.child("Name").setValue(uname);
                                myref.child("Mobile").setValue(umobile);
                                count=count+1;
                                Intent intent=new Intent(UserSignup.this,MainActivity.class);
                                intent.putExtra("flag","1");
                                intent.putExtra("done","1");
                                startActivity(intent);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });*/
                }

            }
        });


    }

    public void go_to_login(View view) {
        Intent intent=new Intent(UserSignup.this,MainActivity.class);
        startActivity(intent);
    }

}
