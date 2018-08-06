package com.example.nce23.moviereviewapplication;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;


public class HomePageActivity extends AppCompatActivity {

    ImageView imageButton1;
    ImageView imageButton2;
    ImageView imageButton3;
    ImageView imageButton4;
    ImageView imageButton5;
    ImageView imageButton6;
    ImageView imageButton7;
    ImageView imageButton8;
    ImageView imageButton9;
    ImageView imageButton10;
    ImageView imageButton0;
    ImageView imageButton11;
    ImageView imageButton12;
    ImageView imageButton13;
    ImageView imageButton14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar().setTitle("Movie Fnatic");

        imageButton0 = (ImageView) findViewById(R.id.imageButton0);
        imageButton1 = (ImageView) findViewById(R.id.imageButton1);
        imageButton2 = (ImageView) findViewById(R.id.imageButton2);
        imageButton3 = (ImageView) findViewById(R.id.imageButton3);
        imageButton4 = (ImageView) findViewById(R.id.imageButton4);
        imageButton5 = (ImageView) findViewById(R.id.imageButton5);
        imageButton6 = (ImageView) findViewById(R.id.imageButton6);
        imageButton7 = (ImageView) findViewById(R.id.imageButton7);
        imageButton8 = (ImageView) findViewById(R.id.imageButton8);
        imageButton9 = (ImageView) findViewById(R.id.imageButton9);
        imageButton10 = (ImageView) findViewById(R.id.imageButton20);
        imageButton0 = (ImageView) findViewById(R.id.imageButton0);
        imageButton11 = (ImageView) findViewById(R.id.imageButton11);
        imageButton12 = (ImageView) findViewById(R.id.imageButton12);
        imageButton13 = (ImageView) findViewById(R.id.imageButton13);
        imageButton14 = (ImageView) findViewById(R.id.imageButton50);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);



        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent HomePageIntent = new Intent (HomePageActivity.this,HomePageActivity.class);
                        startActivity(HomePageIntent);
                        break;
                    case R.id.nav_favourites:
                        Intent FavoritePageIntent = new Intent (HomePageActivity.this,FavoritePageActivity.class);
                        startActivity(FavoritePageIntent);
                        break;
                    case R.id.nav_account:
                        Intent ProfilePageIntent = new Intent (HomePageActivity.this,ProfilePageActivity.class);
                        startActivity(ProfilePageIntent);
                        break;


                }

            }
        });

    }

    public void onClickDescPage (View v)
    {
     Intent DescPageIntent = new Intent(this,MovieDescActivity.class);
     startActivity(DescPageIntent);
    }
    public void onBackPressed() {
        //do nothing
    }


}
