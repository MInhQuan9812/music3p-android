package com.example.deannhom;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSignInButton;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Get references to UI elements
        mEmailEditText = findViewById(R.id.emailEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mSignInButton = findViewById(R.id.signInButton);

        // Get reference to SharedPreferences
        mSharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Check if user is already logged in
        if (mSharedPreferences.getBoolean("loggedIn", false)) {
            // If already logged in, go to main activity
            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
            finish();
        }

        // Set click listener for sign in button
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                // Check if input is valid
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else if (!email.equals("example@gmail.com") || !password.equals("password123")) {
                    Toast.makeText(SignInActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                } else {
                    // If input is valid, save login information and go to main activity
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.putString("email", email);
                    editor.apply();

                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
    }
}
