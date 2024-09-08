package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNo extends AppCompatActivity {
    EditText inputnumber1, inputnumber2, inputnumber3, inputnumber4, inputnumber5, inputnumber6;
    String mobile;
    String getotpbackend,usertype;
    String adminflag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);

        final Button verifybuttonclick = findViewById(R.id.buttongetotp);

        SharedPreferences sharedPreferences=getSharedPreferences("type", Context.MODE_PRIVATE);


        inputnumber1 = findViewById(R.id.inputotp1);
        inputnumber2 = findViewById(R.id.inputotp2);
        inputnumber3 = findViewById(R.id.inputotp3);
        inputnumber4 = findViewById(R.id.inputotp4);
        inputnumber5 = findViewById(R.id.inputotp5);
        inputnumber6 = findViewById(R.id.inputotp6);

        mobile=getIntent().getStringExtra("mobile");
        ImageView imageView = findViewById(R.id.img);
        usertype =getIntent().getStringExtra("usertype");
        adminflag=getIntent().getStringExtra("adminflag");

        if(usertype.equals("user")){
            imageView.setBackgroundResource(R.drawable.user);
        }
        else if(usertype.equals("priest")){
            imageView.setBackgroundResource(R.drawable.priest);
        }
        else {
            imageView.setBackgroundResource(R.drawable.shop);
        }


        TextView textView = findViewById(R.id.shownumber);
        textView.setText(String.format("+91-%s", getIntent().getStringExtra("mobile")));

        getotpbackend = getIntent().getStringExtra("backendotp");
        final ProgressBar progressBarVerifyotp = findViewById(R.id.progress_bar_verifying_otp);

        verifybuttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!inputnumber1.getText().toString().trim().isEmpty() && !inputnumber2.getText().toString().trim().isEmpty() && !inputnumber3.getText().toString().trim().isEmpty() && !inputnumber4.getText().toString().trim().isEmpty() && !inputnumber5.getText().toString().trim().isEmpty() && !inputnumber6.getText().toString().trim().isEmpty()) {

                    String entercodeotp = inputnumber1.getText().toString() +
                            inputnumber2.getText().toString() +
                            inputnumber3.getText().toString() +
                            inputnumber4.getText().toString() +
                            inputnumber5.getText().toString() +
                            inputnumber6.getText().toString();

                    if (getotpbackend != null) {
                        progressBarVerifyotp.setVisibility(View.VISIBLE);
                        verifybuttonclick.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getotpbackend, entercodeotp
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBarVerifyotp.setVisibility(View.INVISIBLE);
                                        verifybuttonclick.setVisibility(View.VISIBLE);

                                        if (!task.isSuccessful()) {
                                            Log.d("","Adminflag is"+adminflag);
                                            if(adminflag.equals("1")){
                                                Intent intent = new Intent(getApplicationContext(), Admin_MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("type","admin");
                                                editor.putString("mobile",mobile);
                                                editor.apply();
                                                startActivity(intent);
                                            }

                                            if(usertype.equals("user")){

                                                Intent intent = new Intent(getApplicationContext(), User_MainActivity.class);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("type","user");
                                                editor.putString("mobile",mobile);
                                                editor.apply();

                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.putExtra("umobile",mobile);
                                                startActivity(intent);
                                            }
                                            else if(usertype.equals("priest")){
                                                Intent intent = new Intent(getApplicationContext(), Priest_MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("type","priest");
                                                editor.putString("mobile",mobile);
                                                editor.apply();

                                                intent.putExtra("pmobile",mobile);
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent(getApplicationContext(), Shop_MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("type","shopkeeper");
                                                editor.putString("mobile",mobile);
                                                editor.apply();

                                                intent.putExtra("smobile",mobile);
                                                startActivity(intent);
                                            }
                                        } else {
                                            Toast.makeText(VerifyPhoneNo.this, "Enter the Correct OTP", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                    } else {
                        Toast.makeText(VerifyPhoneNo.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

//                    Toast.makeText(VerifyPhoneNo.this,"otp verify",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VerifyPhoneNo.this, "Please enter all digits", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberotpremove();

        TextView resendlabel = findViewById(R.id.textResendOtp);

        resendlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyPhoneNo.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyPhoneNo.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend=newbackendotp;
                                Toast.makeText(VerifyPhoneNo.this,"OTP RESENT successfully",Toast.LENGTH_SHORT).show();

                            }
                        }
                );


            }
        });


    }



    private void numberotpremove() {
        inputnumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber3.requestFocus();
                } else {
                    inputnumber1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber4.requestFocus();
                } else {
                    inputnumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber5.requestFocus();
                } else {
                    inputnumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber6.requestFocus();
                } else {
                    inputnumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnumber6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    inputnumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



}