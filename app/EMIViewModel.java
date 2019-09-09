package com.dbs.easyhomeloan.VIewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbs.easyhomeloan.Model.EMIModel;
import com.dbs.easyhomeloan.Model.LoginModel;
import com.dbs.easyhomeloan.View.UI.APIController.Controller;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EMIViewModel extends ViewModel {

    private static final String TAG = EMIViewModel.class.getSimpleName();
    private MutableLiveData<EMIModel> emiData;

    public LiveData<EMIModel> getEMIData(Float principle, Float roi, int tenure) {
        //if the list is null
        emiData = new MutableLiveData<>();
        //we will load it asynchronously from server in this method
        calculateEmi(principle, roi, tenure);

        //finally we will return the list
        return emiData;
    }

    private void calculateEmi(Float principle, Float roi, int tenure) {

        JsonObject reqObj = new JsonObject();
        reqObj.addProperty("principal",principle);
        reqObj.addProperty("ROI", roi);
        reqObj.addProperty("NOM", tenure);

        Call<EMIModel> call = Controller.getClient().getEmicalvalues("EmiCalculation/", reqObj);

        call.enqueue(new Callback<EMIModel>() {
            @Override
            public void onResponse(Call<EMIModel> call, Response<EMIModel> response) {
                emiData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<EMIModel> call, Throwable t) {
                Log.e(TAG, "Error::: " + t.getMessage());
                emiData.setValue(null);
            }
        });
    }

}
