package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    String usertype ="";
    EditText edtmobile;
    Button btnsend;
    public ArrayList<String> adminList=new ArrayList<String>();
    public ArrayList<String> userList=new ArrayList<String>();
    ArrayList<String> priestList=new ArrayList<String>();
    ArrayList<String> shoplist=new ArrayList<String>();
    int exists=0;
    int status=0;
    ProgressBar progressBar;
    String mobile;
    FirebaseAuth mAuth;
    String adminflag="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircularImageView btnuser = (CircularImageView) findViewById(R.id.userimgBtn);
        CircularImageView btnpriest = (CircularImageView) findViewById(R.id.priestimgBtn);
        CircularImageView btnshop = (CircularImageView) findViewById(R.id.stallimgBtn);
        TextView textView = (TextView)findViewById(R.id.type);
        TextView txtlogin = (TextView)findViewById(R.id.txtlogin);
//        TextView txtshared = (TextView)findViewById(R.id.sharedtxt);
//        TextView txtsharedmobile=(TextView)findViewById(R.id.sharedmob);

        edtmobile= (EditText)findViewById(R.id.mid);
        btnsend = (Button)findViewById(R.id.btnsend);

        mAuth = FirebaseAuth.getInstance();

        LinearLayout loginform = (LinearLayout)findViewById(R.id.loginform);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(20,20,20,20);
        params2.setMargins(20,20,20,20);
        params3.setMargins(20,20,20,20);
        ImageView.ScaleType obj1=btnuser.getScaleType();

        progressBar=findViewById(R.id.progress_bar_sending_otp);

        SharedPreferences sharedPreferences=getSharedPreferences("type",MODE_PRIVATE);
        String sharedtype=sharedPreferences.getString("type","0");
        String sharemobile=sharedPreferences.getString("mobile","0");

//        txtshared.setText(sharedtype);
//        txtsharedmobile.setText(sharemobile);

        if(mAuth.getCurrentUser()!=null){
            if(sharedtype.equals("admin")){
                Intent intent = new Intent(MainActivity.this,Admin_MainActivity.class);
                startActivity(intent);
            }
            if(sharedtype.equals("user")){
                Intent intent =new Intent(MainActivity.this,User_MainActivity.class);
                startActivity(intent);
            }
            if(sharedtype.equals("priest")){
                Intent intent =new Intent(MainActivity.this,Priest_MainActivity.class);
                startActivity(intent);
            }
            if(sharedtype.equals("shopkeeper")){
                Intent intent =new Intent(MainActivity.this,Shop_MainActivity.class);
                startActivity(intent);
            }
        }

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!edtmobile.getText().toString().trim().isEmpty()){
                    if((edtmobile.getText().toString().trim()).length()==10){

                        progressBar.setVisibility(View.VISIBLE);
                        btnsend.setVisibility(View.INVISIBLE);

                        if(usertype.equals("user")) {
                            adminflag = "0";
                            exists = 0;
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                            DatabaseReference adminref = firebaseDatabase.getReference().child("Admin");
                            adminref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        String temp = dataSnapshot.getKey();
                                        adminList.add(temp);
                                        Log.d("",temp);
                                    }
                                    for (String t : adminList) {
                                        Log.d("",t);
                                        if (edtmobile.getText().toString().equals(t)) {
                                            Intent intent = new Intent(MainActivity.this,Admin_MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("type","admin");
                                            editor.putString("mobile",mobile);
                                            editor.apply();
                                            startActivity(intent);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

                            if (adminflag.equals("0")) {


                            DatabaseReference databaseReference = firebaseDatabase.getReference().child("userAuth").child("mobiles");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        String gstr = dataSnapshot.getKey();
                                        userList.add(gstr);
                                        Log.d("", gstr);
                                        Log.d("", "Present user is" + edtmobile.getText().toString());
                                    }
                                    Log.d("", "Fetched");

                                    for (String temp : userList) {
                                        //Log.d("",temp);
                                        //Log.d("",edtmobile.getText().toString());
                                        if (edtmobile.getText().toString().equals(temp)) {
                                            exists = 1;
                                            Log.d("", String.valueOf(exists));
                                            break;
                                        }
                                    }
                                    if (exists == 0) {
                                        Toast.makeText(getApplicationContext(), "User Not Registered Please register", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        btnsend.setVisibility(View.VISIBLE);
                                    } else {
                                        phonemethod();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

                        }

                        }

                        if(usertype.equals("priest")){
                            exists=0;
                            status=0;
                            mobile=edtmobile.getText().toString();

                            for(String temp:priestList){
                                Log.d("",temp);
                                if(mobile.equals(temp)){
                                    exists=1;
                                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                                    DatabaseReference priest_ref=firebaseDatabase.getReference("PSignupReq").child(mobile);
                                    priest_ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            priestSignupHelper priestSignupHelper = snapshot.getValue(com.example.priestbooking.priestSignupHelper.class);
                                            status=Integer.parseInt(priestSignupHelper.getPstatus());

                                            Log.d("","Exists value is"+exists);
                                            Log.d("","Status value is"+status);

                                            if(status==0){
                                                Intent intent=new Intent(MainActivity.this,prieststatus.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                phonemethod();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });

                                }
                            }
                            if(exists==0){
                                Toast.makeText(getApplicationContext(),"Priest Not Registered",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btnsend.setVisibility(View.VISIBLE);
                            }
                        }

                        if(usertype.equals("shop")){
                            exists=0;
                            status=0;
                            mobile=edtmobile.getText().toString();

                            for(String temp:shoplist){
                                if(mobile.equals(temp)){
                                    exists=1;
                                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                                    DatabaseReference shop_ref=firebaseDatabase.getReference("SkSignupReq").child(mobile);
                                    shop_ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            ShopSignupHelper shopSignupHelper = snapshot.getValue(com.example.priestbooking.ShopSignupHelper.class);
                                            status=Integer.parseInt(shopSignupHelper.getSkstatus());

                                            Log.d("","Exists value is"+exists);
                                            Log.d("","Status value is"+status);

                                            if(status==0){
                                                Intent intent=new Intent(MainActivity.this,ShopStatus.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                phonemethod();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });

                                }
                            }
                            if(exists==0){
                                Toast.makeText(getApplicationContext(),"Priest Not Registered",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btnsend.setVisibility(View.VISIBLE);
                            }

                        }

                    }
                    else {
                        Toast.makeText(MainActivity.this,"Please enter Correct Number",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Enter Mobile Number",Toast.LENGTH_SHORT).show();
                }

            }

        });

        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("User Selected");
                textView.setVisibility(View.GONE);
                params1.weight=0.8f;
                params2.weight=1f;
                params3.weight=1f;
                btnuser.setAdjustViewBounds(true);
                btnuser.setScaleType(obj1);
                txtlogin.setText("Hey User !,");
                txtlogin.setVisibility(View.VISIBLE);
                loginform.setVisibility(View.VISIBLE);


                btnuser.setLayoutParams(params1);
                btnpriest.setLayoutParams(params2);
                btnshop.setLayoutParams(params3);
                usertype="user";



            }
        });
        btnpriest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.GONE);
                params1.weight=1f;
                params2.weight=0.8f;
                params3.weight=1f;

                btnuser.setLayoutParams(params1);
                btnpriest.setLayoutParams(params2);
                btnshop.setLayoutParams(params3);
                txtlogin.setText("Hey Priest !,");
                txtlogin.setVisibility(View.VISIBLE);

                btnpriest.setAdjustViewBounds(true);
                loginform.setVisibility(View.VISIBLE);

                btnpriest.setScaleType(obj1);
                usertype="priest";

                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference().child("PSignupReq");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String gstr=dataSnapshot.getKey();
                            priestList.add(gstr);
                        }
                        Log.d("","priest list Fetched");
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });
        btnshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.GONE);
                textView.setText("Shop Selected");
                params1.weight=1f;
                params2.weight=1f;
                params3.weight=0.8f;

                btnuser.setLayoutParams(params1);
                btnpriest.setLayoutParams(params2);
                btnshop.setLayoutParams(params3);
                txtlogin.setText("Hey Shop Keeper !,");
                txtlogin.setVisibility(View.VISIBLE);

                loginform.setVisibility(View.VISIBLE);
                btnpriest.setAdjustViewBounds(true);

                btnshop.setScaleType(obj1);
                usertype="shop";

                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference().child("SkSignupReq");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String gstr=dataSnapshot.getKey();
                            shoplist.add(gstr);
                        }
                        Log.d("","Shop Keeper list Fetched");
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

    }


    public void signup(View v){
        if(usertype=="user"){
            Intent intent=new Intent(MainActivity.this,UserSignup.class);
            startActivity(intent);
        }
        else if(usertype=="priest"){
            Intent intent=new Intent(MainActivity.this,PriestSignup.class);
            startActivity(intent);
        }
        else {
            Intent intent=new Intent(MainActivity.this,ShopSignup.class);
            startActivity(intent);
        }
    }

    public void phonemethod(){
        Log.d("","Here");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + edtmobile.getText().toString(),
                60,
                TimeUnit.SECONDS,
                MainActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progressBar.setVisibility(View.GONE);
                        btnsend.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progressBar.setVisibility(View.GONE);
                        btnsend.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        progressBar.setVisibility(View.GONE);
                        btnsend.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(MainActivity.this, VerifyPhoneNo.class);
                        intent.putExtra("mobile", edtmobile.getText().toString());
                        intent.putExtra("backendotp",backendotp);
                        intent.putExtra("usertype",usertype);
                        intent.putExtra("adminflag",adminflag);
                        startActivity(intent);
                    }
                }
        );
    }

}
