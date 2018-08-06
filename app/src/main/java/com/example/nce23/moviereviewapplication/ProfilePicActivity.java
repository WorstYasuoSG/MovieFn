package com.example.nce23.moviereviewapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePicActivity extends AppCompatActivity {

    private android.app.ProgressDialog ProgressDialog;
    private FirebaseAuth firebaseAuth;
    private Uri mainImageURI = null;
    private CircleImageView imageViewDp;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private Button saveSettings;
    private EditText editTextUsername;
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        ProgressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();


        imageViewDp = findViewById(R.id.imageViewDp);
        saveSettings = findViewById(R.id.saveSettings);
        editTextUsername = findViewById(R.id.editTextUserName);

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    if(task.getResult().exists()){

                        String editTextUsername = task.getResult().getString("Username");
                        String image = task.getResult().getString("image");
                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.ic_account_circle_black_24dp);

                        Glide.with(ProfilePicActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(imageViewDp);




                        Toast.makeText(ProfilePicActivity.this,"Data Exists ",Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    String error = task.getException().getMessage();
                    Toast.makeText(ProfilePicActivity.this,"FIRESTONE  RETRIEVE ERROR ",Toast.LENGTH_SHORT).show();

                }
            }
        });

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mainImageURI != null) {
                    ProgressDialog.setMessage("Setting Picture...");
                    ProgressDialog.show();
                    String user_id = firebaseAuth.getCurrentUser().getUid();
                    final String username = editTextUsername.getText().toString();
                    StorageReference image_path = storageReference.child("profile_images").child(user_id + ".jpq");
                    image_path.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {

                                Uri download_uri = task.getResult().getDownloadUrl();
                                Map<String, String> userMap = new HashMap<>();
                                userMap.put("image", download_uri.toString());
                                System.out.println(username);
                                userMap.put("Username",username);
                                String user_id = firebaseAuth.getCurrentUser().getUid();



                                firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            Toast.makeText(ProfilePicActivity.this,"Settings have been saved ",Toast.LENGTH_SHORT).show();
                                            Intent mainIntent = new Intent(ProfilePicActivity.this,HomePageActivity.class);
                                            startActivity(mainIntent);
                                            finish();

                                        }
                                        else{
                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProfilePicActivity.this,"FIRESTONE ERROR ",Toast.LENGTH_SHORT).show();
                                            ProgressDialog.cancel();


                                        }
                                    }
                                });
                            }


                            else{
                                String error = task.getException().getMessage();
                                Toast.makeText(ProfilePicActivity.this,"IMAGE ERROR ",Toast.LENGTH_SHORT).show();
                                ProgressDialog.cancel();
                            }

                        }
                    });
                }

            }
        });

        imageViewDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(ProfilePicActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                            .PERMISSION_GRANTED) {
                        Toast.makeText(ProfilePicActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(ProfilePicActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                    }
                    else{
                        BringImagePicker();
                    }
                }
                else{
                    BringImagePicker();
                }
            }
        });
    }
    private void BringImagePicker(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(ProfilePicActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageURI = result.getUri();
                imageViewDp.setImageURI(mainImageURI);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }



    public void toHomePage(View v){
        Intent secondIntent = new Intent(this,HomePageActivity.class);
        startActivity(secondIntent);
        finish();
    }
    public void onBackPressed() {
        //do nothing
    }
}
