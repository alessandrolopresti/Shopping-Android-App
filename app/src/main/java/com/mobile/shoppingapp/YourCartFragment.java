package com.mobile.shoppingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class YourCartFragment extends Fragment {
    private static final String TAG = "YourCartFragment";

    private View itemsView;
    private Button smart_shop_button;
    private RecyclerView itemsList;

    private DatabaseReference itemsRef, UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    FirebaseRecyclerAdapter<SearchItem, CartItemViewHolder> adapter;


    public YourCartFragment()   {
    }

    public void displayAngSmartShoppingFragment(Cart generalItems) {
        AngSmartShoppingFragment simpleFragment = AngSmartShoppingFragment.newInstance();
        // Get the FragmentManager and start a transaction.

        //store the general items retrieved from the database for that user
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(getString(R.string.ang_general_items),generalItems.getCart());
        simpleFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        // Replace the Fragment.
        fragmentTransaction.replace(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        itemsView = inflater.inflate(R.layout.fragment_your_cart, container, false);

        itemsList = (RecyclerView) itemsView.findViewById(R.id.your_cart_recycler_view);
        itemsList.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        //itemsRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(currentUserID);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        itemsRef = UsersRef.child(currentUserID).child("Cart");
        smart_shop_button = itemsView.findViewById(R.id.button_smart_shop);
        smart_shop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> it = new ArrayList<String>(10);
                it.add("pasta");
                it.add("coffee");
                it.add("fruit");

                Cart shoppingList = new Cart(it);

                //Cart shoppingList = getShoppingList()
                displayAngSmartShoppingFragment(shoppingList);
            }
        });


        return itemsView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<SearchItem>()
                        .setQuery(itemsRef, SearchItem.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<SearchItem, CartItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartItemViewHolder holder, int position, @NonNull SearchItem model) {
                String userIDs = getRef(position).getKey();
                Log.d(TAG, "userIDs vale: " + userIDs);
                Log.d(TAG, "" + itemsRef.child(userIDs).child("name"));
                itemsRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("name").getValue() != null) {
                            Log.d(TAG, "DataSnapshot vale: " + dataSnapshot.child("name").getValue().toString());
                            String name = dataSnapshot.child("name").getValue().toString();
                            Log.d(TAG, "name vale: " + name);
                            Log.d(TAG, "holder vale: " + holder);
                            holder.item.setText(name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Do you want to delete this data?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int selectedItems = position;
                                        adapter.getRef(selectedItems).removeValue();
                                        adapter.notifyItemRemoved(selectedItems);
                                        itemsList.invalidate();
                                        onStart();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confirm");
                        dialog.show();
                    }
                });

            }



            @NonNull
            @Override
            public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)   {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_your_shopping, parent, false);
                CartItemViewHolder viewHolder = new CartItemViewHolder(view);
                return viewHolder;
            }
        };
        itemsList.setAdapter(adapter);
        adapter.startListening();
    }


    public static class CartItemViewHolder extends RecyclerView.ViewHolder  {
        TextView item;

        public CartItemViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.your_shopping_title);

        }
    }


}

