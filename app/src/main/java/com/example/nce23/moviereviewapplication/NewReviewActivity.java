package com.example.nce23.moviereviewapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class NewReviewActivity extends AppCompatActivity {

    private EditText editTextReview;
    private TextView textViewUsername1;
    private Button submitButton;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private ImageView imageViewDp;

    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        editTextReview = findViewById(R.id.editTextReview);
        textViewUsername1 = findViewById(R.id.textViewUsername1);
        submitButton = findViewById(R.id.submitButton);
        imageViewDp = findViewById(R.id.imageViewDp);


        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {



                if (task.isSuccessful()) {

                    if (task.getResult().exists()) {


                        String image = task.getResult().getString("image");
                        String user_name = task.getResult().getString("Username");

                        textViewUsername1.setText(user_name);


                        RequestOptions placeholder = new RequestOptions();
                        placeholder.placeholder(R.drawable.ic_account_circle_black_24dp);

                        Glide.with(NewReviewActivity.this).setDefaultRequestOptions(placeholder).load(image).into(imageViewDp);

                    }

                } else {


                    Toast.makeText(NewReviewActivity.this, "Firestore Retrieve Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String desc = editTextReview.getText().toString();

                if(!TextUtils.isEmpty(desc)){

                    Map<String,Object> postMap = new HashMap<>();
                    postMap.put("desc",desc);
                    postMap.put("user_id",user_id);
                    postMap.put("timestamp",FieldValue.serverTimestamp());


                    firebaseFirestore.collection("Reviews").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(), ReviewListActivity.class));
                                Toast.makeText(NewReviewActivity.this,"Review was added", Toast.LENGTH_SHORT).show();
                                finish();


                            }else{
                                Toast.makeText(NewReviewActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
                else{
                    Toast.makeText(NewReviewActivity.this,"Please write a review", Toast.LENGTH_SHORT).show();

                }

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent HomePageIntent = new Intent (NewReviewActivity.this,HomePageActivity.class);
                        startActivity(HomePageIntent);
                        break;
                    case R.id.nav_favourites:
                        Intent FavoritePageIntent = new Intent (NewReviewActivity.this,FavoritePageActivity.class);
                        startActivity(FavoritePageIntent);
                        break;
                    case R.id.nav_account:
                        Intent ProfilePageIntent = new Intent (NewReviewActivity.this,ProfilePageActivity.class);
                        startActivity(ProfilePageIntent);
                        break;


                }
            }
        });

    }
}

