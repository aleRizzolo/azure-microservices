package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.client.constructors.Login;
import com.example.client.interfaces.LoginAPI;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton, sendUserToRegister;
    private LoginAPI loginPlaceHolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        sendUserToRegister = findViewById(R.id.register);

        //call the interfaces that locks submit button if a filed is empty
        emailInput.addTextChangedListener(loginTextWatcher);
        passwordInput.addTextChangedListener(loginTextWatcher);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginPlaceHolderAPI = retrofit.create(LoginAPI.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(emailInput.getText().toString(), passwordInput.getText().toString());
            }
        });

        //button that redirects to registration forms
        sendUserToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String password) {
        Login login = new Login(email, password);

        Call<Login> call = loginPlaceHolderAPI.loginUser(login);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginSuccess.class);
                    intent.putExtra("email", emailInput.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String submitEmailEditText = emailInput.getText().toString().trim();
            String submitPasswordEditText = passwordInput.getText().toString().trim();

            loginButton.setEnabled(!submitEmailEditText.isEmpty() && !submitPasswordEditText.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
