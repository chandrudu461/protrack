package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/*
    This activity is for forgotPassword,
    we'll send the reset password email, to reset the password

 */

public class ForgotPassword extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordButton;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.email2); // Taking email from user
        resetPasswordButton = findViewById(R.id.reset);

        auth = FirebaseAuth.getInstance();  // Firebase authentication variable

        resetPasswordButton.setOnClickListener(new View.OnClickListener() { // If resetPasswordButton is clicked, we'll reset Password method
            @Override
            public void onClick(View v) {
                resetPassword();  // This method sends the email to user to reset the password
            }
        });
    }
    private void resetPassword(){
        String email = emailEditText.getText().toString().trim();  // converting email to String
        if(email.isEmpty()){  // If email is empty, we cannot proceed further
            emailEditText.setError("email is required");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){  // If email is not a valid email, we'll send toast to provide a valid email
            emailEditText.setError("Please provide valid email!!!");
            emailEditText.requestFocus();
            return;
        }
        // FirebaseAuth auth variable has sendPasswordResetEmail method
        // This method sends email to user to reset the password
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) { // task stores the result
                if(task.isSuccessful()){   // If task is successful , means the passwordReset email is sent to the user.
                    Toast.makeText(ForgotPassword.this, "Check your Email to reset your password!", Toast.LENGTH_SHORT).show();
                }
                else{ // If anything wrong happens , we'll toast the message to try again.
                    Toast.makeText(ForgotPassword.this, "Try Again! Something wrong happened!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}