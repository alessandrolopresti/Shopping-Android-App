package com.mobile.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class TraditionalSearchFragment extends Fragment {
    private static final String TAG = "TraditionalSearchFragment" ;
    private EditText mCartItem;
    private Button mCartBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.traditional_search_fragment, container, false);

        mCartItem = view.findViewById(R.id.cartItem);
        mCartBtn = view.findViewById(R.id.cartBtn);
        mCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mCartItem.getText().toString();
                if (text != null)   {
                    SearchItem si = new SearchItem(text);

                    DatabaseReference pushRef = FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    pushRef.child("Cart").push().setValue(si).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), text + " has been successfully added to Your Cart", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), text + " could not be written", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Error: you did not digit any item", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }




}

