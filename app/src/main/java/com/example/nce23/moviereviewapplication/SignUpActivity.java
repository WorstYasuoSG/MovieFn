package com.example.nce23.moviereviewapplication;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;



import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail2;
    private EditText editTextPassword2;
    private EditText editTextPassword3;
    private ProgressDialog ProgressDialog;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    private TextView textViewChangeDp;

    Uri imageHoldUri = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        ProgressDialog = new ProgressDialog(this);


        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);
        editTextEmail2 = (EditText) findViewById(R.id.editTextEmail2);
        editTextPassword3 = (EditText) findViewById(R.id.editTextPassword3);





    }


    public void clickSignUp2(View v) {

        registerUser();

    }

    public void registerUser() {
        String email = editTextEmail2.getText().toString().trim();
        String password = editTextPassword2.getText().toString().trim();
        String reTypePassword = editTextPassword3.getText().toString().trim();



        if (TextUtils.isEmpty(email)) {
                    //email is empty
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }



                if (TextUtils.isEmpty(password)) {
                    //password is empty
                    Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty((reTypePassword))) {
                    //retype password is empty
                    Toast.makeText(this, "Please Re-Enter Password", Toast.LENGTH_SHORT).show();
                    return;

                }
                if ((password).length() < 7 || (reTypePassword).length() < 7) {
                    //Password must be more than 7 Characters
                    Toast.makeText(this, "Your Password has to be more than 7 characters", Toast.LENGTH_SHORT).show();
                    return;


                }
                if (!password.equals(reTypePassword)) {
                    //Passwords do not match
                    Toast.makeText(this, "Your Passwords do not match,please retry", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    ProgressDialog.setMessage("Registering...");
                    ProgressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        //userProfile();
                        Toast.makeText(getApplicationContext(), "Choose A Profile Picture!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ProfilePicActivity.class));
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to register,please try again", Toast.LENGTH_SHORT).show();
                        ProgressDialog.cancel();


                    }
                }

            }

            );



        }


    }
//    public void userProfile()
//    {
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if(user != null)
//        {
//
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(editTextUsername.getText().toString().trim()).build();
//
//                    //.setPhotoUri(Uri.parse(")
//            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                }
//            });
//        }
//
//
//
//    }

}