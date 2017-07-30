package com.naxa.nepal.sudurpaschimanchal.model.rest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("getmenu")
    Call<Data> getMenu(@Field("last_sync_date_time") String data);


}
