package com.dbs.easyhomeloan.VIewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbs.easyhomeloan.Helper;
import com.dbs.easyhomeloan.Model.PropertyModel;
import com.dbs.easyhomeloan.View.UI.APIController.Controller;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyViewModel extends ViewModel {

    private MutableLiveData<PropertyModel> propertyList;


    public LiveData<PropertyModel> getRecommendatonProperty(String userId) {
        try {
            //if the list is null
//        if (propertyList == null) {
            propertyList = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadRecommendatonProperty(userId);
//        }

            //finally we will return the list
        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertyList;
    }

    private void loadRecommendatonProperty(String userId) {

        String url = "";
        if(!Helper.isStringNotEmpty(userId)){
            url = "fetchRecommendations?userid=1";
        }else{
            url = "fetchRecommendations?userid="+userId;
        }


        Call<PropertyModel> call = Controller.getClient().getPropertyRecommed(url);


        call.enqueue(new Callback<PropertyModel>() {
            @Override
            public void onResponse(Call<PropertyModel> call, Response<PropertyModel> response) {

                //finally we are setting the list to our MutableLiveData
                propertyList.setValue(response.body());

            }

            @Override
            public void onFailure(Call<PropertyModel> call, Throwable t) {
                propertyList.setValue(null);
            }
        });
    }

    public void loadRecWithFilters(JsonObject jsonObject) {
        String url = "filter/dofilter";
        Call<PropertyModel> call = Controller.getClient().getFilteredRecom(url, jsonObject);
        call.enqueue(new Callback<PropertyModel>() {
            @Override
            public void onResponse(Call<PropertyModel> call, Response<PropertyModel> response) {
                if (response != null && response.isSuccessful()) {
                    propertyList.setValue(response.body());
                } else {
                    propertyList.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PropertyModel> call, Throwable t) {
                t.printStackTrace();
                propertyList.setValue(null);
            }
        });
    }
}
