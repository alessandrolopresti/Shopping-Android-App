package com.mobile.shoppingapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class YourShoppingFragment extends Fragment {
    private static final String TAG = "YourShoppingFragment" ;
    private RecyclerView mShoppingRecyclerView;
    private ShoppingAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        View view = inflater.inflate(R.layout.fragment_yourshopping, container, false);

        mShoppingRecyclerView = (RecyclerView) view
                .findViewById(R.id.search_recycler_view);
        mShoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        YourShoppingLab yourShoppingLab = YourShoppingLab.get(getActivity());
        List<String> yourShoppings = yourShoppingLab.getYourShoppings();


        mAdapter = new ShoppingAdapter(yourShoppings);
        mShoppingRecyclerView.setAdapter(mAdapter);
    }

    private class ShoppingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String mTitle;

        private TextView mTitleTextView;

        public ShoppingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_your_shopping, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.your_shopping_title);
        }

        public void bind(String title) {
            mTitle = title;
            mTitleTextView.setText(mTitle);
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

        //Probably useless but who knows?
        public void closeAngSmartShoppingFragment() {
            // Get the FragmentManager.
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // Check to see if the fragment is already showing.
            AngSmartShoppingFragment simpleFragment = (AngSmartShoppingFragment) fragmentManager
                    .findFragmentById(R.id.fragment_container);
            if (simpleFragment != null) {
                // Create and commit the transaction to remove the fragment.
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.remove(simpleFragment).commit();
            }

        }

        @Override
        public void onClick(View view) {

            Toast.makeText(getActivity(),
                    mTitle + " clicked!", Toast.LENGTH_SHORT)
                    .show();
            if (mTitle.equals("Your Profile"))   {
                startActivity(new Intent(getActivity(), AccountActivity.class));
            }
            if (mTitle.equals("Your Cart"))   {
                YourCartFragment newFragment = new YourCartFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId() , newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            if (mTitle.equals("Your Smart Shoppings"))    {
                ArrayList<String> it = new ArrayList<String>(10);
                it.add("pasta");
                it.add("coffee");
                it.add("fruit");



                Cart shoppingList = new Cart(it);

                //Cart shoppingList = getShoppingList()
                displayAngSmartShoppingFragment(shoppingList);

            }
            if (mTitle.equals("Your favourite items")) {

            }
            if (mTitle.equals("Your favourite markets"))   {

            }

        }
    }

    private class ShoppingAdapter extends RecyclerView.Adapter<ShoppingHolder> {

        private List<String> mYourShoppings;

        public ShoppingAdapter(List<String> yourShoppings) {
            mYourShoppings = yourShoppings;
        }

        @Override
        public ShoppingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ShoppingHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ShoppingHolder holder, int position) {
            String item  = mYourShoppings.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "mYourShoppings vale: " + mYourShoppings);
            return mYourShoppings.size();

        }
    }

}
