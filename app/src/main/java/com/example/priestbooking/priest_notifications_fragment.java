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

public class priest_notifications_fragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;


    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_priest_notifications_fragment, container, false);


        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("type",MODE_PRIVATE);
        String sharedtype=sharedPreferences.getString("type","0");
        String sharemobile=sharedPreferences.getString("mobile","0");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);


        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("pendingbookings");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<PriestNotificationsZone, priest_notifications_fragment.PriestNotificationZoneViewHolder> FBRA = new FirebaseRecyclerAdapter<PriestNotificationsZone, priest_notifications_fragment.PriestNotificationZoneViewHolder>(
                PriestNotificationsZone.class,
                R.layout.priest_notifications_card,
                priest_notifications_fragment.PriestNotificationZoneViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(PriestNotificationZoneViewHolder priestNotificationZoneViewHolder, PriestNotificationsZone priestNotificationsZone, int i) {
                priestNotificationZoneViewHolder.setReqservice(priestNotificationsZone.getReqservice());
                priestNotificationZoneViewHolder.setTimings(priestNotificationsZone.getTimings());
                priestNotificationZoneViewHolder.setPrice(priestNotificationsZone.getPrice());
                priestNotificationZoneViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference obj = getRef(i);
                        String strorderid = obj.getKey();
                        Intent intent = new Intent(getActivity(),SingleNotificationActivity.class);
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


    public static class PriestNotificationZoneViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public PriestNotificationZoneViewHolder(View itemView) {
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