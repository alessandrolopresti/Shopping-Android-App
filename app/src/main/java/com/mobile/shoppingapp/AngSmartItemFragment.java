package com.mobile.shoppingapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AngSmartItemFragment extends Fragment {
    private final String LOG_TAG = "AngSmartItemFragment";
    private final int REQUEST_GPS = 1;


    public AngSmartItemFragment() {
        // Required empty public constructor
    }

    public static AngSmartItemFragment newInstance() {
        return new AngSmartItemFragment();
    }

    public void displayProductFragment(String name, String url, String description) {
        AngProductFragment simpleFragment = AngProductFragment.newInstance();
        // Get the FragmentManager and start a transaction.

        //store the general items retrieved from the database for that user
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("url", url);
        bundle.putString("description", description);

        simpleFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        // Replace the Fragment.
        //getActivity().findViewById(R.id.fragment_container_http_list).setVisibility(View.INVISIBLE);
        fragmentTransaction.replace(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_GPS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(LOG_TAG,"activated");
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ang_smart_item, container, false);
        //final FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.fragment_container_http_list);
        //frameLayout.setVisibility(View.INVISIBLE);

        Bundle bundle = this.getArguments();
        if (bundle == null) {
            Log.d(LOG_TAG, "ERROR");
            return rootView;
        }
        Log.d(LOG_TAG, bundle.getString("smart_market"));
        ListView listView = (ListView) rootView.findViewById(R.id.ang_single_items);
        TextView market_name = rootView.findViewById(R.id.ang_text_market_name);
        TextView total_cost = rootView.findViewById(R.id.ang_text_view_cost);
        market_name.setText(bundle.getString("smart_market"));

        total_cost.setText(bundle.getDouble("smart_cost") + "");
        ArrayList<String> item_name = bundle.getStringArrayList("smart_prod_name");
        String item_info[] = bundle.getStringArray("smart_prod_info");

        ArrayList<HashMap<String, String>> listItems = new ArrayList<>();
        int i = 0;
        for (String entry : item_name) {
            HashMap<String, String> map = new HashMap<>();
            map.put("item", entry);
            map.put("price", item_info[i++].split("END")[1] );
            listItems.add(map);
        }
        Log.d(LOG_TAG, listItems.toString());

        listView.setAdapter(new SimpleAdapter(
                rootView.getContext(),
                listItems,
                R.layout.single_smart_item,
                new String[]{"item", "price"},
                new int[]{R.id.ang_prod_name, R.id.ang_prod_price}
        ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                String urlImage = item_info[pos].split("END")[2];
                String description=item_info[pos].split("END")[0];
                String price = item_info[pos].split("END")[1];


                displayProductFragment(listItems.get(pos).get("item")+": "+price, urlImage, description);

            }

        });

        Button findMarket = rootView.findViewById(R.id.item_button_find);


        findMarket.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                double longitude = 0.0;
                double latitude = 0.0;

                if (PermissionUtils.requestPermission(
                        getActivity(),
                        REQUEST_GPS,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();

                    //Toast.makeText(getActivity(),longitude+" "+latitude,Toast.LENGTH_SHORT).show();

                    String loc = bundle.getString("smart_market");
                    Uri addressUri = Uri.parse("geo:"+longitude+","+latitude+"?q=" + loc);
                    Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Log.d(LOG_TAG, "Can't handle this intent!");
                    }

                }
                else{
                    Toast.makeText(getActivity(),"You need to provide the gps permission",Toast.LENGTH_SHORT).show();

                }




            }
        });


        return rootView;

    }


}
