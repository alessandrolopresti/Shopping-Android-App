package com.mobile.shoppingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class AngProductFragment extends Fragment {


    public AngProductFragment() {
        // Required empty public constructor
    }
    public static AngProductFragment newInstance() {
        return new AngProductFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_ang_product, container, false);
        Bundle bundle = this.getArguments();
        String name = bundle.getString("name");
        String url = bundle.getString("url");
        String description = bundle.getString("description");
        TextView prod = rootView.findViewById(R.id.ang_prod_name);
        TextView desc = rootView.findViewById(R.id.ang_desc_text);
        ImageView image_product = rootView.findViewById(R.id.ang_image_product);
        Picasso.get().load(url).into(image_product);
        prod.setText(name);
        desc.setText(description);


        return rootView;
    }

}
