package com.example.client.interfaces;

import com.example.client.constructors.Update;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;

public interface UpdateAPI {
    @PATCH("modifyAccount")
    Call<Update> modifyUser(@Body Update update);
}
