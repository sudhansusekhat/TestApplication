package com.dbs.easyhomeloan.View.UI.APIController;

import com.dbs.easyhomeloan.Model.CompareModel;
import com.dbs.easyhomeloan.Model.EMIModel;
import com.dbs.easyhomeloan.Model.LoginModel;
import com.dbs.easyhomeloan.Model.PropertyModel;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {


    @GET()
    Call<PropertyModel> getPropertyRecommed(@Url String url);

    @POST()
    Call<LoginModel> getLoggedData(@Url String url, @Body JsonObject reqBody);

    @POST()
    Call<PropertyModel> getFilteredRecom(@Url String url, @Body JsonObject reqObj);

    @POST()
    Call<EMIModel> getEmicalvalues(@Url String url, @Body JsonObject reqObj);

    @POST()
    Call<List<CompareModel>> getComparedData(@Url String url, @Body JsonObject reqObj);

}
