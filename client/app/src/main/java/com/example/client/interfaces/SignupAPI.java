package com.example.client.interfaces;

import com.example.client.constructors.Signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignupAPI {
    @POST("authSignup")
    Call<Signup> signupUser(@Body Signup signup);
}
