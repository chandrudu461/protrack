package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

/*
    This is Register Activity , In this user is registered to our app using his email, password.

 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    private Button registerUser,signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();  //Firebase Authentication variable declaration.

        registerUser = findViewById(R.id.register1);
        registerUser.setOnClickListener(this);   // onClickListener for registerUser, written in Override onClick method.
        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = findViewById(R.id.email1);
        editTextPassword = findViewById(R.id.password1);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register1:   // If registerUser clicks, this registerUser() function is called, which handles user registration.
                registerUser();
                break;
            case R.id.signIn:     // If sign in is clicked, this will redirect the user to sign in page
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }

    private void registerUser() {  // In this method, we'll register a user with email and password.

        String email = editTextEmail.getText().toString().trim();        // Taking email and password from here.
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) { // If email is empty , error displayed on screen
            editTextEmail.setError("email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {   // If invalid email is given, asks for a valid email
            editTextEmail.setError("Enter valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        // This method creates a user with email and password

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {  // if user is created, task is successful.
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) { // we'll toast a message that user is registered successfully.
                            Toast.makeText(RegisterActivity.this, "User Registered Successfully!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            // anything wrong happens, we'll toast a message that user is failed to register.
                            Toast.makeText(RegisterActivity.this, "Failed to Register!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}