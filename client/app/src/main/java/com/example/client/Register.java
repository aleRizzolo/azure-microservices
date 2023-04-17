package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.client.constructors.Signup;
import com.example.client.interfaces.SignupAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
    private EditText emailInput, passwordInput, firstNameInput, lastNameInput;
    private Button submitButton;
    private SignupAPI signupAPIPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //action bar to go back at home page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        firstNameInput = findViewById(R.id.firstName);
        lastNameInput = findViewById(R.id.lastName);
        submitButton = findViewById(R.id.register);

        //call the interfaces that locks submit button if a filed is empty
        emailInput.addTextChangedListener(registrerTextWatcher);
        passwordInput.addTextChangedListener(registrerTextWatcher);
        firstNameInput.addTextChangedListener(registrerTextWatcher);
        lastNameInput.addTextChangedListener(registrerTextWatcher);

        //build retrofit for api call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        signupAPIPlaceholder = retrofit.create(SignupAPI.class);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(emailInput.getText().toString(), passwordInput.getText().toString(),
                        firstNameInput.getText().toString(), lastNameInput.getText().toString());
            }
        });
    }

    private void register(String email, String password, String firstName, String lastName) {
        Signup signup = new Signup(email, password, firstName, lastName);

        Call<Signup> call = signupAPIPlaceholder.signupUser(signup);

        call.enqueue(new Callback<Signup>() {
            @Override
            public void onResponse(Call<Signup> call, Response<Signup> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Register.this, "Signup success", Toast.LENGTH_SHORT).show();
                    emailInput.setText("");
                    passwordInput.setText("");
                    firstNameInput.setText("");
                    lastNameInput.setText("");
                }
            }

            @Override
            public void onFailure(Call<Signup> call, Throwable t) {
                Toast.makeText(Register.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private TextWatcher registrerTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String newEmailEditText = emailInput.getText().toString().trim();
            String newPasswordEditText = passwordInput.getText().toString().trim();
            String newFirstNameEditText = firstNameInput.getText().toString().trim();
            String newLastNameEditText = lastNameInput.getText().toString().trim();

            submitButton.setEnabled(!newEmailEditText.isEmpty() && !newPasswordEditText.isEmpty() && !newFirstNameEditText.isEmpty() && !newLastNameEditText.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}