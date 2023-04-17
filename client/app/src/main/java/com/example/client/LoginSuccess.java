package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.constructors.Delete;
import com.example.client.constructors.Login;
import com.example.client.constructors.Update;
import com.example.client.interfaces.DeleteAPI;
import com.example.client.interfaces.UpdateAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginSuccess extends AppCompatActivity {
    private TextView messageSuccess, newFirstName, newLastName;
    private Button deleteAccountButton, modifyAccountButton;
    private DeleteAPI deletePlaceHolderAPI;
    private UpdateAPI updatePlaceholderAPI;

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
        modifyAccountButton = findViewById(R.id.submit_modifications);

        //call the interfaces that locks submit button if a filed is empty
        newFirstName.addTextChangedListener(updateUserTextWatcher);
        newLastName.addTextChangedListener(updateUserTextWatcher);

        messageSuccess.setText("Hello " +userEmail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        updatePlaceholderAPI = retrofit.create(UpdateAPI.class);
        deletePlaceHolderAPI = retrofit.create(DeleteAPI.class);

        //on click with lambda function
        modifyAccountButton.setOnClickListener(view -> modifyAccount(newFirstName.getText().toString(), newLastName.getText().toString()));
        deleteAccountButton.setOnClickListener(view -> deleteUserAccount(userEmail));
    }

    private void modifyAccount(String newFirstName, String newLastName) {
        Update update = new Update(newFirstName, newLastName);

        Call<Update> call = updatePlaceholderAPI.modifyUser(update);

        call.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(Call<Update> call, Response<Update> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginSuccess.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginSuccess.this, "Account deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Update> call, Throwable t) {
                Toast.makeText(LoginSuccess.this, "Fail", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginSuccess.this, "Account updated", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Delete> call, Throwable t) {
                Toast.makeText(LoginSuccess.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private TextWatcher updateUserTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String updateFirstNameEditText = newFirstName.getText().toString().trim();
            String updateLastNameEditText = newLastName.getText().toString().trim();

            modifyAccountButton.setEnabled(!updateFirstNameEditText.isEmpty() && !updateLastNameEditText.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}