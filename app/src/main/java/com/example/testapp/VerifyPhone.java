package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {

    private EditText otp;
    private Button verify;

    private FirebaseAuth mAuth;
    String phonenumber;
    String otpid;

    PhoneAuthCredential phoneAuthCredential;
    PhoneAuthProvider.ForceResendingToken token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);


        mAuth = FirebaseAuth.getInstance();
        otp = findViewById(R.id.otp);
        verify = findViewById(R.id.verify);

        phonenumber = getIntent().getStringExtra("mobile").toString();

        initiateotp();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().isEmpty()){
                    Toast.makeText(VerifyPhone.this, "Blank Field cannot be processed!", Toast.LENGTH_SHORT).show();
                }
                else if(otp.getText().toString().length() != 6){
                    Toast.makeText(VerifyPhone.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                    sighInWithPhoneAuthCredential(credential);
                }
            }
        });


//
//        mAuth = FirebaseAuth.getInstance();
//
//
//
//        verifyPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(otpValid){
//                    String otp = getOtpNumberOne.getText().toString() + getOtpNumberTwo.getText().toString() + getOtpNumberThree.getText().toString()
//                            + getOtpNumberFour.getText().toString() + getOtpNumberFive.getText().toString() + getOtpNumberSix.getText().toString();
//
//                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,otp);
//                    verifyAuthentication(credential);
//                }
//
//            }
//        });
//
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//
//                verificationId = s;
//                token = forceResendingToken;
//                resendOtp.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
//                super.onCodeAutoRetrievalTimeOut(s);
//                resendOtp.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                verifyAuthentication(phoneAuthCredential);
//                resendOtp.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//                Toast.makeText(VerifyPhone.this, "OTP verification Failed!!!", Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        sendOTP(phone);
//
//
//
//        resendOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resendOTP(phone);
//            }
//        });
//
//    }
//
//    private void verifyAuthentication(PhoneAuthCredential credential) {
//        mAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                Toast.makeText(VerifyPhone.this, "Account Created and linked!!!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//
//    public void sendOTP(String phoneNumber){
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this,mCallbacks);
//    }
//    public void resendOTP(String phoneNumber){
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS,this,mCallbacks,token);
//
//    }
//
//    public void validateField(EditText field){
//        if(field.getText().toString().isEmpty()){
//            field.setError("Required");
//            otpValid = false;
//        }
//        else{
//            otpValid = true;
//        }
    }

    private void initiateotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                        otpid = s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        sighInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(VerifyPhone.this, "e.getMessage()", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }
    private void sighInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(VerifyPhone.this,RegisterActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(VerifyPhone.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
   }
}