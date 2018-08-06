package com.example.nce23.moviereviewapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Movie Fnatic");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));


        }
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        progressDialog = new ProgressDialog(this);

    }




    public void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;

        } else {
            progressDialog.setMessage("Signing in...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "Welcome!" ,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                        finish();

                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Failed to log in,please try again",Toast.LENGTH_SHORT).show();
                    }

                }
            });

    }}



    public void clickSignUp (View v)
    {
        Intent SignUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(SignUpIntent);
    }

    public void clickLogin (View v)
    {
        userLogin();
    }


    public void clickForgetPassword (View v)
    {
        Intent forgetPasswordIntent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(forgetPasswordIntent);
    }
    public void onBackPressed() {
        //do nothing
    }

}
