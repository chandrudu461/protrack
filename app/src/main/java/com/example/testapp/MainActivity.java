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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button signin,register;
    private TextView forgotPassword;
    private EditText emailText,passwordText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();


        //If user is already logged in, this will redirect user to profile activity
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        signin = findViewById(R.id.signIn);
        register = findViewById(R.id.register);
        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        register.setOnClickListener(this);//this onClick listener registers the user
        signin.setOnClickListener(this);//this onClick listener is for user sign in


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register: // case when the user clicked on register It will redirect user to RegisterActiviy
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.signIn:  // case when user clicked on sign in button, with this userLogin() method calls and user login
                userLogin();
                break;
            case R.id.forgotPassword:  // For forgotPassword button
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }

    }

    private void userLogin() {  // User Login Function
        String email = emailText.getText().toString().trim();     // Taking email and password from user
        String password = passwordText.getText().toString().trim();

        if(email.isEmpty()){  // If email is empty error arises in app
            emailText.setError("Email is required!!");
            emailText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){  // If email is not a valid email requests for a valid email
            emailText.setError("Please provide valid email!");
            emailText.requestFocus();
            return;
        }
        if(password.isEmpty()){ // If password is empty error arises in app
            passwordText.setError("password is required!!");
            passwordText.requestFocus();
            return;
        }
        if(password.length() < 6){  // If password length is less than 6, requests for at least 6 digit password
            passwordText.setError("min password length is 6 characters");
            passwordText.requestFocus();
            return;
        }

        // User sign in with taken email and password
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {    // task will store the result whether the user is logged in or not
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(task.isSuccessful()){          // If the task is successful user is logged in successfully, we send email to verify
                    if(user.isEmailVerified()){   // If email is verified, we redirect the user to Profile activity
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    }
                    else{  // If email is not verified, we will send email verification.
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check Your Email to verify your account!", Toast.LENGTH_SHORT).show();

                        // by seeing the toast , user will go to email and verifies his account. After verification, we allow him to
                        // our app
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials!", Toast.LENGTH_SHORT).show();
                    // if email password mismatched, user is failed to login, we'll show it in toast.
                }
            }
        });

    }
}