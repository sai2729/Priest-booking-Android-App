package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;


public class priestbookingPageAdapter extends FragmentStatePagerAdapter {
    int counttab;

    public priestbookingPageAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.counttab=behavior;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                priest_notifications_fragment priest_notifications = new priest_notifications_fragment();
                return priest_notifications;

            case 1:
                priest_active_booking_fragment priest_active= new priest_active_booking_fragment();
                return priest_active;
            case 2:
                priest_previous_booking_fragment priest_previous = new priest_previous_booking_fragment();
                return priest_previous;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
