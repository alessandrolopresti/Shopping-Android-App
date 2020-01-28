package com.mobile.shoppingapp;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestSmartShop {



    private OkHttpClient client = new OkHttpClient();

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String getResponse(String url)  {

        RequestSmartShop example = new RequestSmartShop();
        String response="error";
        try {
            response = example.run("https://raw.github.com/square/okhttp/master/README.md");
        }
        catch (IOException e){

        }

        Log.d("RequestSmartShop",response);
        return response;
    }

}
