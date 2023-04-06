package com.example.client.interfaces;

import com.example.client.constructors.Delete;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.HTTP;

public interface DeleteAPI {
    @HTTP(method = "DELETE", path = "deleteAccount", hasBody = true)
    Call<Delete> deleteAccount(@Body Delete delete);
}

