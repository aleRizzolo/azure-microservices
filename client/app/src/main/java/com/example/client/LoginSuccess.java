package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.constructors.Delete;
import com.example.client.constructors.Login;
import com.example.client.interfaces.DeleteAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginSuccess extends AppCompatActivity {
    private TextView messageSuccess, newFirstName, newLastName;
    private Button deleteAccountButton, modifyAccountButton;
    private DeleteAPI deletePlaceHolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        //get the email from the MainActivity
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("email");

        messageSuccess = findViewById(R.id.message);
        deleteAccountButton = findViewById(R.id.delete_button);
        newFirstName = findViewById(R.id.newFirstName);
        newLastName = findViewById(R.id.newLastName);

        messageSuccess.setText("Hello " +userEmail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        deletePlaceHolderAPI = retrofit.create(DeleteAPI.class);

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               deleteUserAccount(userEmail.toString());
            }
        });
    }

    private void deleteUserAccount(String userEmail) {
        Delete delete = new Delete(userEmail);

        Call<Delete> call = deletePlaceHolderAPI.deleteAccount(delete);

        call.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginSuccess.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginSuccess.this, MainActivity.class);
                    Toast.makeText(LoginSuccess.this, "Account deleted", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Delete> call, Throwable t) {
                Toast.makeText(LoginSuccess.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}