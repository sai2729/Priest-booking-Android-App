package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class User_MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private long backPressedTime;
    private Toast backToast;

    String umobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main);


        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListner);



        Fragment userhomefragment = new user_home_fragment();

        Fragment userbookingsfragment = new user_booking_fragment();

        Fragment userprofilefragment = new user_profile_fragment();



        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,userhomefragment).commit();

    }


    @Override
    public void onBackPressed(){
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                    Fragment userhomefragment = new user_home_fragment();

                    Fragment userbookingsfragment = new user_booking_fragment();

                    Fragment userprofilefragment = new user_profile_fragment();


                    switch (menuItem.getItemId()){
                        case R.id.nav_Home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userhomefragment).commit();
                            break;
                        case R.id.nav_booking:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userbookingsfragment).commit();
                            break;
                        case R.id.nav_Profile:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userprofilefragment).commit();
                            break;

                    }

                    return true;
                }
            };

}