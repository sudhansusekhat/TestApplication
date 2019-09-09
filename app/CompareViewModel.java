package com.dbs.easyhomeloan.VIewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbs.easyhomeloan.Model.CompareModel;
import com.dbs.easyhomeloan.View.UI.APIController.Controller;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompareViewModel extends ViewModel {

    private static final String TAG = CompareViewModel.class.getSimpleName();
    private MutableLiveData<List<CompareModel>> comparedData;

    public LiveData<List<CompareModel>> getComparedData(String property1, String property2) {
        //if the list is null
        comparedData = new MutableLiveData<>();
        //we will load it asynchronously from server in this method
        callCompareData(property1, property2);

        //finally we will return the list
        return comparedData;
    }

    private void callCompareData(String p1, String p2) {

        JsonObject reqObj = new JsonObject();
        reqObj.addProperty("propertyid1", p1);
        reqObj.addProperty("propertyid2", p2);

        Call<List<CompareModel>> call = Controller.getClient().getComparedData("Comparision/", reqObj);

        call.enqueue(new Callback<List<CompareModel>>() {
            @Override
            public void onResponse(Call<List<CompareModel>> call, Response<List<CompareModel>> response) {
                comparedData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CompareModel>> call, Throwable t) {
                Log.e(TAG, "Error::: " + t.getMessage());
                comparedData.setValue(null);
            }
        });
    }
}
