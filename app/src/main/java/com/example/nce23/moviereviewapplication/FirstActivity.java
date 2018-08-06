package com.example.nce23.moviereviewapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FirstActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Movie Fnatic");

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));

        }
    }

    public void clickToLogin (View v)
    {

        Intent LoginIntent = new Intent(this,LoginActivity.class);
        startActivity(LoginIntent);
    }
    public void clickSignUp (View v)
    {
        Intent SignUpIntent = new Intent (this,SignUpActivity.class);
        startActivity(SignUpIntent);
    }
}
