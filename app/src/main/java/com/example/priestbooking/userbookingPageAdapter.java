package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;


public class userbookingPageAdapter extends FragmentStatePagerAdapter {
    int counttab;

    public userbookingPageAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.counttab=behavior;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                user_active_booking_fragment user_active= new user_active_booking_fragment();
                return user_active;
            case 1:
                user_previous_booking_fragment user_previous = new user_previous_booking_fragment();
                return user_previous;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
