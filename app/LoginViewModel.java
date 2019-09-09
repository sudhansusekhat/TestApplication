package com.dbs.easyhomeloan.VIewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbs.easyhomeloan.Model.LoginModel;
import com.dbs.easyhomeloan.View.UI.APIController.Controller;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();
    private MutableLiveData<LoginModel> loginData;

    public LiveData<LoginModel> getLoginData(String emailStr, String pswStr) {
        //if the list is null
        loginData = new MutableLiveData<>();
        //we will load it asynchronously from server in this method
        callLogin(emailStr, pswStr);

        //finally we will return the list
        return loginData;
    }

    private void callLogin(String email, String password) {

        JsonObject reqObj = new JsonObject();
        reqObj.addProperty("userid", email);
        reqObj.addProperty("password", password);

        Call<LoginModel> call = Controller.getClient().getLoggedData("Login/", reqObj);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                loginData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e(TAG, "Error::: " + t.getMessage());
                loginData.setValue(null);
            }
        });
    }
}
