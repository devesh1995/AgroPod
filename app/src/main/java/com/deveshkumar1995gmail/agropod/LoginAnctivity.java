package com.deveshkumar1995gmail.agropod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;



public class LoginAnctivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    private EditText mEmailField;

    private EditText mPasswordField;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_anctivity);


        mAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.loginid);

        mPasswordField = findViewById(R.id.password);

        findViewById(R.id.loginbtn).setOnClickListener(this);



    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);

        if (!validateForm()) {

            return;

        }
        // [START sign_in_with_email]

        mAuth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "signInWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginAnctivity.this, "Authentication Success!!.",Toast.LENGTH_SHORT).show();

                            updateUI(user);

                        } else {

                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Toast.makeText(LoginAnctivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                            updateUI(null);

                        }
                        // [START_EXCLUDE]

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginAnctivity.this, "Network error: Please try again.",Toast.LENGTH_LONG).show();

                        }


                        // [END_EXCLUDE]

                    }

                });

        // [END sign_in_with_email]

    }




    private boolean validateForm() {

        boolean valid = true;



        String email = mEmailField.getText().toString();

        if (TextUtils.isEmpty(email)) {

            mEmailField.setError("Required.");

            valid = false;

        } else {

            mEmailField.setError(null);

        }



        String password = mPasswordField.getText().toString();

        if (TextUtils.isEmpty(password)) {

            mPasswordField.setError("Required.");

            valid = false;

        } else {

            mPasswordField.setError(null);

        }



        return valid;

    }



    private void updateUI(FirebaseUser user) {

        if (user != null) {



            Intent i = new Intent(LoginAnctivity.this, MainPage.class);
            startActivity(i);


        } else {

            Toast.makeText(LoginAnctivity.this, "Something went worng: Try again!! ",Toast.LENGTH_LONG).show();
        }

    }






    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.loginbtn)

            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());


        }


}
