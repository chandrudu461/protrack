package com.example.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

/*

    This is the FirebaseUtilities java class, in this we'll write all the methods related to firebase in public mode,
    and easily access the functions in our Activities.

    Here we're writing code for Firebase Storage


 */

public class FirebaseUtilities {
    Context context;
    ProgressDialog progressDialog;   // Progress dialog appears while Loading...
    FirebaseStorage firebaseStorage; // Firebase Storage variable
    FirebaseAuth mAuth;              // Firebase Authentication variable
    FirebaseUser firebaseUser;       // Firebase user variable


    public FirebaseUtilities(Context context){
        this.context = context;
        firebaseStorage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    String getTimeStamp(){  // To get the timestamp.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public void uploadImage(Uri uri){  // To upload the image
        progressDialog.show();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        // This storageReference is the reference we're storing our images, pdfs in storage.
        // current path is set as, "Images/ {userid} /"
        StorageReference storageReference = firebaseStorage.getReference().child("Images/" + firebaseUser.getUid() + "/" +"hi");
        storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) { // result of firebase storage stores in task
                if(task.isSuccessful()){    // if task is successful, that means the data is stored to the storage successfully
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() { // this gives the url of image we uploaded
                                                                                                          // to the storage
                        @Override
                        public void onSuccess(Uri uri) {
                            progressDialog.dismiss();
                            Log.d("storageReference URL", "On Success" + uri.toString());
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) { // if data storing is failed progress dialog will disappear and we'll print
                progressDialog.dismiss();                 // the stack trace to know where the error occured.
                e.printStackTrace();                      // also toast the message that image upload is failed
                Toast.makeText(context, "Image Upload falied"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}