package com.example.nce23.moviereviewapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class FavoritePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_page);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent HomePageIntent = new Intent (FavoritePageActivity.this,HomePageActivity.class);
                        startActivity(HomePageIntent);
                        break;
                    case R.id.nav_favourites:
                        Intent FavoritePageIntent = new Intent (FavoritePageActivity.this,FavoritePageActivity.class);
                        startActivity(FavoritePageIntent);
                        break;
                    case R.id.nav_account:
                        Intent ProfilePageIntent = new Intent (FavoritePageActivity.this,ProfilePageActivity.class);
                        startActivity(ProfilePageIntent);
                        break;


                }
            }
        });

    }
    }

