package com.example.priestbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.MODE_PRIVATE;


public class shop_profile_fragment extends Fragment {


    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String uname;
    String umobile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shop_profile, container, false);

        TextView txtname = (TextView)rootView.findViewById(R.id.txtname);
        TextView txtmob = (TextView)rootView.findViewById(R.id.txtmob);
        TextView txtloc = (TextView)rootView.findViewById(R.id.txtloc);
        TextView txtstart = (TextView)rootView.findViewById(R.id.txtstart);
        TextView txtend = (TextView)rootView.findViewById(R.id.txtend);
        Button btnlogout = (Button)rootView.findViewById(R.id.btnlogout);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("type",MODE_PRIVATE);
        String sharedtype=sharedPreferences.getString("type","0");
        String sharemobile=sharedPreferences.getString("mobile","0");

        DatabaseReference myRef = database.getReference().child("SkSignupReq").child(sharemobile);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                ShopSignupHelper shopProfile = snapshot.getValue(ShopSignupHelper.class);
                txtname.setText(""+shopProfile.getSkname());
                txtloc.setText(""+shopProfile.getSklocation());
                txtstart.setText(""+shopProfile.getSkopentime());
                txtend.setText(""+shopProfile.getSkclosetime());

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        txtmob.setText(sharemobile);

        firebaseDatabase = FirebaseDatabase.getInstance();

        mAuth=FirebaseAuth.getInstance();
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });




        return rootView;
    }
}