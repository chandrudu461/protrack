package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class NormalActivity extends AppCompatActivity {
    private Button logout;
    private TextView TextfullName,TextEmail,TextAge,TextPhone;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        logout = findViewById(R.id.logout);
        TextfullName = findViewById(R.id.fullname2);
        TextAge = findViewById(R.id.AgeId);           // these are all the dummy references
        TextEmail = findViewById(R.id.Email1);
        TextPhone = findViewById(R.id.phone);
        logout.setOnClickListener(new View.OnClickListener() { // if logout is clicked, we'll sign out the user and redirect to main activity
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); // sign out the user
                startActivity(new Intent(NormalActivity.this,MainActivity.class)); // redirect to main activity
            }
        });

        fab = findViewById(R.id.fab); // ProfileActivity Button

        fab.setOnClickListener(new View.OnClickListener() { // If we click the Floating point button, we'll redirect to ProfileActivity
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NormalActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}