package com.example.priestbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import static android.content.Context.MODE_PRIVATE;


public class user_active_booking_fragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;


    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;



    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_user_active_booking_fragment, container, false);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("type",MODE_PRIVATE);
        String sharedtype=sharedPreferences.getString("type","0");
        String sharemobile=sharedPreferences.getString("mobile","0");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);


        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("userAuth").child("mobiles").child(sharemobile).child("Bookings").child("Active");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<UserBookingZone, UserBookingZoneViewHolder> FBRA = new FirebaseRecyclerAdapter<UserBookingZone, UserBookingZoneViewHolder>(
                UserBookingZone.class,
                R.layout.bookings_card,
                user_active_booking_fragment.UserBookingZoneViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(UserBookingZoneViewHolder userBookingZoneViewHolder, UserBookingZone userBookingZone, int i) {
                userBookingZoneViewHolder.setService(userBookingZone.getService());
                userBookingZoneViewHolder.setTimings(userBookingZone.getTimings());
                userBookingZoneViewHolder.setStatus(userBookingZone.getStatus());

            }

        };
        recyclerView.setAdapter(FBRA);
    }


    public static class UserBookingZoneViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UserBookingZoneViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        } public void setService(String service){
            TextView post_service = mView.findViewById(R.id.txtcardservice);
            post_service.setText(service);
        } public void setTimings(String timings){
            TextView post_timings = mView.findViewById(R.id.txtcardtimings);
            post_timings.setText(timings);
        }public void setStatus(String status){
            TextView post_status = mView.findViewById(R.id.txtcardstatus);
            post_status.setText(status);
        }
    }


}