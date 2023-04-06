package com.example.client.interfaces;

import com.example.client.constructors.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginAPI {
    @POST("authLogin")
    Call<Login> loginUser(@Body Login login);
}
