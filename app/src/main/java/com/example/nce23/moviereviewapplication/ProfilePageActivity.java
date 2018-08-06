package com.example.nce23.moviereviewapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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



public class ProfilePageActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private TextView textViewUsername;
    private Button buttonLogout;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private ImageView imageViewDp;
    private String user_id;
    private FirebaseUser firebaseUser;



    private FirebaseAuth.AuthStateListener mAuthListener;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user_id = firebaseAuth.getCurrentUser().getUid();



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);

        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText(user.getEmail());
        imageViewDp = findViewById(R.id.imageViewDp);

        System.out.println(user_id);

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {



                if (task.isSuccessful()) {

                    if (task.getResult().exists()) {


                        String image = task.getResult().getString("image");
                        String user_name = task.getResult().getString("Username");

                        textViewUsername.setText(user_name);


                        RequestOptions placeholder = new RequestOptions();
                        placeholder.placeholder(R.drawable.ic_account_circle_black_24dp);

                        Glide.with(ProfilePageActivity.this).setDefaultRequestOptions(placeholder).load(image).into(imageViewDp);

                    }

                } else {


                    Toast.makeText(ProfilePageActivity.this, "Firestore Retrieve Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });




        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent HomePageIntent = new Intent (ProfilePageActivity.this,HomePageActivity.class);
                        startActivity(HomePageIntent);
                        break;
                    case R.id.nav_favourites:
                        Intent FavoritePageIntent = new Intent (ProfilePageActivity.this,FavoritePageActivity.class);
                        startActivity(FavoritePageIntent);
                        break;
                    case R.id.nav_account:
                        Intent ProfilePageIntent = new Intent (ProfilePageActivity.this,ProfilePageActivity.class);
                        startActivity(ProfilePageIntent);
                        break;


                }
            }
        });

    }
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(ProfilePageActivity.this, HomePageActivity.class));
        }
    }


    public void onClickLogOut (View v)
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }


    }


