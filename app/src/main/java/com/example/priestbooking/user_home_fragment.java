package com.example.priestbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;


public class user_home_fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_user_home, container, false);
        CircularImageView img1 =(CircularImageView)rootview.findViewById(R.id.id1);
        CircularImageView img2 =(CircularImageView)rootview.findViewById(R.id.id2);
        CircularImageView img3 =(CircularImageView)rootview.findViewById(R.id.id3);
        CircularImageView img4 =(CircularImageView)rootview.findViewById(R.id.id4);
        CircularImageView img5 =(CircularImageView)rootview.findViewById(R.id.id5);
        CircularImageView img6 =(CircularImageView)rootview.findViewById(R.id.id6);
        CircularImageView img7 =(CircularImageView)rootview.findViewById(R.id.id7);
        CircularImageView img8 =(CircularImageView)rootview.findViewById(R.id.id8);
        CircularImageView img9 =(CircularImageView)rootview.findViewById(R.id.id9);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),user_service_details.class);
                intent.putExtra("servicetype","Vratalu");
                startActivity(intent);

            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),user_service_details.class);
                intent.putExtra("servicetype","Vivaham");
                startActivity(intent);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),user_service_details.class);
                intent.putExtra("servicetype","Homam");
                startActivity(intent);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),user_service_details.class);
                intent.putExtra("servicetype","Vahanamu");
                startActivity(intent);
            }
        });

        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),user_service_details.class);
                intent.putExtra("servicetype","Gruhapravesham");
                startActivity(intent);
            }
        });

        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),user_service_details.class);
                intent.putExtra("servicetype","Shubakaryalu");
                startActivity(intent);
            }
        });

        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),user_service_details.class);
                intent.putExtra("servicetype","House");
                startActivity(intent);
            }
        });

        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),user_service_details.class);
                intent.putExtra("servicetype","Commercial");
                startActivity(intent);
            }
        });

        img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),user_service_details.class);
                intent.putExtra("servicetype","Temple");
                startActivity(intent);
            }
        });


        return rootview;
    }


}