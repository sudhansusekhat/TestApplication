package com.dbs.easyhomeloan.View.UI.APIController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

    public static final String BASE_URL = "http://52.221.206.147:5000/";

    private static Retrofit retrofit = null;


    public static Api getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Api.class);
    }

}
