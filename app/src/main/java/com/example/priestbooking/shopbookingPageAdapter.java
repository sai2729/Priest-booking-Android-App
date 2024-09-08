package com.example.priestbooking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;


public class shopbookingPageAdapter extends FragmentStatePagerAdapter {
    int counttab;

    public shopbookingPageAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.counttab=behavior;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                shopNotificationFragment shop_notifications = new shopNotificationFragment();
                return shop_notifications;

            case 1:
                shop_active_booking_fragment shop_active= new shop_active_booking_fragment();
                return shop_active;
            case 2:
                shop_previous_booking_fragment shop_previous = new shop_previous_booking_fragment();
                return shop_previous;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
