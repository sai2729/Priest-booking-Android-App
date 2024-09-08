package com.example.priestbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;


public class priest_active_booking_fragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;


    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_priest_active_booking_fragment, container, false);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("type",MODE_PRIVATE);
        String sharedtype=sharedPreferences.getString("type","0");
        String sharemobile=sharedPreferences.getString("mobile","0");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);


        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("PSignupReq").child(sharemobile).child("Bookings").child("Active");



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<PriestActiveZone, priest_active_booking_fragment.PriestActiveZoneViewHolder> FBRA = new FirebaseRecyclerAdapter<PriestActiveZone, priest_active_booking_fragment.PriestActiveZoneViewHolder>(
                PriestActiveZone.class,
                R.layout.priest_notifications_card,
                priest_active_booking_fragment.PriestActiveZoneViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(priest_active_booking_fragment.PriestActiveZoneViewHolder priestActiveZoneViewHolder, PriestActiveZone priestActiveZone, int i) {
                priestActiveZoneViewHolder.setReqservice(priestActiveZone.getReqservice());
                priestActiveZoneViewHolder.setTimings(priestActiveZone.getTimings());
                priestActiveZoneViewHolder.setPrice(priestActiveZone.getPrice());
                priestActiveZoneViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference obj = getRef(i);
                        String strorderid = obj.getKey();
                        Intent intent = new Intent(getActivity(),SingleActiveActivity.class);
                        intent.putExtra("orderid",strorderid);
                        startActivity(intent);
                    }
                });
                DatabaseReference obj = getRef(i);
                Log.d("",obj.getKey());
            }


        };
        recyclerView.setAdapter(FBRA);
    }


    public static class PriestActiveZoneViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public PriestActiveZoneViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        } public void setReqservice(String service){
            TextView post_service = mView.findViewById(R.id.txtcardservice);
            post_service.setText(service);
        } public void setTimings(String timings){
            TextView post_timings = mView.findViewById(R.id.txtcardtimings);
            post_timings.setText(timings);
        }public void setPrice(String price){
            TextView post_price = mView.findViewById(R.id.txtcardprice);
            post_price.setText(price);
        }
    }


}