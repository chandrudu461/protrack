package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class RetrieveData extends AppCompatActivity {

    TextView emailText,firstNameText,lastNameText,ageText,genderText,langText;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference reference,referenceImage;
    FirebaseStorage firebaseStorage;
    FirebaseUser firebaseUser;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        emailText = findViewById(R.id.email3);
        firstNameText = findViewById(R.id.firstName3);
        lastNameText = findViewById(R.id.lastName3);
        ageText = findViewById(R.id.age3);
        genderText = findViewById(R.id.gender3);
        langText = findViewById(R.id.lang3);
        imageView2 = findViewById(R.id.imageView2);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users").child(mAuth.getCurrentUser().getUid());
        referenceImage = database.getReference().child("Images/" + firebaseUser.getUid() + "/" +"hi");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.child("email").getValue().toString();
                String firstName = snapshot.child("firstName").getValue().toString();
                String lastName = snapshot.child("lastName").getValue().toString();
                String age = snapshot.child("age").getValue().toString();
                String gender = snapshot.child("gender").getValue().toString();
                String lang = snapshot.child("lang").getValue().toString();

                firstNameText.setText(firstName);
                lastNameText.setText(lastName);
                emailText.setText(email);
                ageText.setText(age);
                genderText.setText(gender);
                langText.setText(lang);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RetrieveData.this, "error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        referenceImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url = snapshot.getValue(String.class);
                Picasso.get().load(url).into(imageView2);
                Toast.makeText(RetrieveData.this, "Image Is Loaded Successfully!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RetrieveData.this, "Error Loading Image!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}