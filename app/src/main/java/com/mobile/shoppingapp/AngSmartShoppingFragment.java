package com.mobile.shoppingapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.json.Json;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AngSmartShoppingFragment extends Fragment {
    private final String LOG_TAG="AngSmartShopping";
   // private final ProgressDialog dialog = new ProgressDialog(getActivity());
    static private int counter_for_http = 0;
    static private boolean error_http = false;



    public AngSmartShoppingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    //TO DO
    public SmartShopping  getListSmartShopping(ArrayList<String> items){
        // avoid creating several instances, should be singleon

        //RequestSmartShop.getResponse("");

        SmartShopping listSmart[] = new SmartShopping[1];

        Map<String, String> val = new LinkedHashMap<String, String>();

        String sampleProd = "very good"+"-"+"13.4"+"-"+"https://nst.sky.it/immagini/sport/calcio_italiano/2012/01/06/original/marianna.jpg";

        val.put("Lavazza",sampleProd);
        val.put("Guizza",sampleProd);
        val.put("Oreo",sampleProd);
        val.put("Kebab",sampleProd);

        String tescoUri = "https://argomenti.ilsole24ore.com/Immagini/Editrice/ILSOLE24ORE/ARGOMENTI/Online/Aziende/Immagini/TUV/tesco.jpg";
        listSmart[0] = new SmartShopping("Tesco", tescoUri,46, val);

        return listSmart[0];

    }


    public SmartShopping[] buildSmartShopFromHttp(JSONObject[] arg)  {
        String tescoImageUrl ="https://wl3-cdn.landsec.com/sites/default/files/images/shops/logos/tesco.png";
        String waitroseImageUrl ="https://wl3-cdn.landsec.com/sites/default/files/images/shops/logos/waitrose_0.png";
        String morrisonsImageUrl ="https://upload.wikimedia.org/wikipedia/en/thumb/8/82/MorrisonsLogo.svg/1200px-MorrisonsLogo.svg.png";

        SmartShopping ris[] = new SmartShopping[3];
        ris[0] = new SmartShopping();
        ris[0].setMarketName("Tesco");
        ris[0].setTotalCost(0.0);
        ris[0].setItems(new HashMap<String, String>());
        ris[0].setImageUrl(tescoImageUrl);

        ris[1] = new SmartShopping();
        ris[1].setMarketName("Waitorose");
        ris[1].setImageUrl(waitroseImageUrl);
        ris[1].setTotalCost(0.0);
        ris[1].setItems(new HashMap<String, String>());

        ris[2] = new SmartShopping();
        ris[2].setMarketName("Morrisons");
        ris[2].setImageUrl(morrisonsImageUrl);
        ris[2].setTotalCost(0.0);
        ris[2].setItems(new HashMap<String, String>());


        for (JSONObject jobj:arg) {
            try {

                JSONObject json_item = jobj.getJSONArray("tesco").getJSONObject(0);
                String name = json_item.getString("name");
                String description =json_item.getString("description");
                String image =json_item.getString("image");
                String price =json_item.getString("price");
                Log.d("prova",name+" "+description+" "+image+" "+price);
                String sampleProd = description+"END"+price+"END"+image;
                ris[0].getItems().put(name,sampleProd);
                ris[0].setTotalCost(Double.parseDouble(price)+ris[0].getTotalCost());

                JSONObject json_item2 = jobj.getJSONArray("waitrose").getJSONObject(0);
                String name2 = json_item2.getString("name");
                String description2 =json_item2.getString("description");
                String image2 =json_item2.getString("image");
                String price2 =json_item2.getString("price");
                Log.d("prova",name2+" "+description2+" "+image2+" "+price2);
                String vvv[] = price2.replace("£","").split("[.]");

                if (vvv[0].equals("")){
                    price2="0."+vvv[1];
                }
                else {
                    if (vvv[1].length() == 0)
                         price2 = vvv[0] ;
                    if (vvv[1].length() == 1)
                        price2 = vvv[0] + "." + vvv[1].charAt(0);
                    else
                        price2 = vvv[0] + "." + vvv[1].charAt(0)+vvv[1].charAt(1);;

                }


                String sampleProd2 = description2+"END"+price2+"END"+image2;
                ris[1].getItems().put(name2,sampleProd2);
                ris[1].setTotalCost(Double.parseDouble(price2)+ris[1].getTotalCost());


                JSONObject json_item3 = jobj.getJSONArray("morrisions").getJSONObject(0);
                String name3 = json_item3.getString("name");
                if (!name3.equals("not-found")) {
                    String description3 = json_item3.getString("description");
                    String image3 = json_item3.getString("image");
                    String price3 = json_item3.getString("price").replace("p", "").replace("£", "");
                    if (!price3.contains(".")) {
                        price3 = "0." + price3;
                    }
                    price3 = price3.replaceAll(" ", "");
                    Log.d("prova", name3 + " " + description3 + " " + image3 + " " + price3);
                    String sampleProd3 = description3 + "END" + price3 + "END" + image3;
                    ris[2].getItems().put(name3, sampleProd3);
                    ris[2].setTotalCost(Double.parseDouble(price3) + ris[2].getTotalCost());
                }
                else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }




        return ris;
    }

    public void displayAngSmartItemFragment(SmartShopping smartList) {
        AngSmartItemFragment simpleFragment = AngSmartItemFragment.newInstance();
        // Get the FragmentManager and start a transaction.

        //store the general items retrieved from the database for that user
        Bundle bundle = new Bundle();
        bundle.putString("smart_market",smartList.getMarketName());
        bundle.putString("smart_image",smartList.getImageUrl());
        bundle.putDouble("smart_cost",smartList.getTotalCost());
        Map<String, String> items =smartList.getItems();
        ArrayList<String> product_name = new ArrayList<String>();

        String product_info[] = new String[items.keySet().size()];
        int i =0;
        for (String prod: items.keySet()) {
            product_name.add(prod);
            product_info[i]=(items.get(prod));
            i++;
        }
        bundle.putStringArrayList("smart_prod_name",product_name);
        bundle.putStringArray("smart_prod_info",product_info);

        simpleFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        // Replace the Fragment.
        fragmentTransaction.replace(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit();
    }

    public static AngSmartShoppingFragment newInstance() {
        return new AngSmartShoppingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_ang_smart_shopping_fragment, container, false);
        Bundle bundle = this.getArguments();
        if (bundle == null || bundle.getStringArrayList(getString(R.string.ang_general_items))  == null || bundle.getStringArrayList(getString(R.string.ang_general_items)).isEmpty()) {
            TextView el = (TextView)(rootView.findViewById(R.id.ang_smart_text));
            el.setText(R.string.ang_choose_least_one);
            Log.d(LOG_TAG,"ERROR");
            return rootView;
        }


        ArrayList<String> items = bundle.getStringArrayList(getString(R.string.ang_general_items));
        Log.d(LOG_TAG,items.toString());



        OkHttpClient client = new OkHttpClient();
        final JSONObject resp[]= new JSONObject[items.size()];
        counter_for_http=0;
        error_http=false;


        for (String food:items) {
            if (error_http) break;

            Request request = new Request.Builder()
                    .url("https://enigmatic-spire-46067.herokuapp.com/products/"+food)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {


                    counter_for_http=0;
                    error_http=true;


                    Log.d("ERRORhere2","a");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        Log.d("ERRORhere1","a");

                        throw new IOException("Unexpected code " + response);
                    } else {
                        // do something wih the result
                        JSONObject res = null;
                        try {
                            res = new JSONObject(response.body().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.d(LOG_TAG,res);
                        resp[counter_for_http++] = res;
                        if (error_http) {
                            Toast.makeText(getActivity(),"Error pleas retry",Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (counter_for_http == items.size() && !error_http){
                            Log.d("here",items.size()+" "+counter_for_http);
                            counter_for_http=0;
                            SmartShopping listSmart[]  = buildSmartShopFromHttp(resp);

                            ListView listView = (ListView)rootView.findViewById(R.id.ang_smart_list_view);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                                    SmartShopping c = listSmart[pos];
                                    //Toast.makeText(getActivity(),c.toString(),Toast.LENGTH_SHORT).show();
                                    displayAngSmartItemFragment(c);

                                }
                            });

                            AdapterSmartShopping adapter = new AdapterSmartShopping(rootView.getContext(), R.layout.smart_item, listSmart);

                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                    listView.setAdapter(adapter);

                                }
                            });

                        }

                    }
                }
            });



        }


        return rootView;
    }

}
