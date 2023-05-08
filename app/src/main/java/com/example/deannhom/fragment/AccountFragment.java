package com.example.deannhom.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deannhom.EditProfileActivity;
import com.example.deannhom.MainActivity;
import com.example.deannhom.R;
import com.example.deannhom.SignInActivity;
import com.example.deannhom.helper.DbHelper;


public class AccountFragment extends Fragment {
    private TextView usernameTextView;
    private TextView emailTextView;
    private Button signOutButton;
    private SQLiteDatabase mDatabase;
    private Button editProfileButton;
    DbHelper DB;

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";

//    private static final String COLUMN_PASSWORD = "password";

    public AccountFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize UI elements
        usernameTextView = rootView.findViewById(R.id.usernameTextView);
        emailTextView = rootView.findViewById(R.id.emailTextView);
        signOutButton = rootView.findViewById(R.id.signOutButton);
        editProfileButton = rootView.findViewById(R.id.editProfileButton);

        // Create / Open the database
        DbHelper DB = new DbHelper(getActivity());
        mDatabase = DB.getReadableDatabase();

        // Get the user's data from the database
        Cursor cursor = mDatabase.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL},
                COLUMN_ID + "= " + 1, null, null, null, null);


        while (cursor.moveToNext()) {
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);

            if (emailIndex >= 0) {
                String email = cursor.getString(emailIndex);
                emailTextView.setText(email);
            }
            if (usernameIndex >= 0) {
                String username = cursor.getString(usernameIndex);
                usernameTextView.setText(username);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        // Set up sign out button click listener
        // Set up sign out button click listener
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the current user session
                // This can be done by removing any saved user data or preferences
                // For example, you can use the SharedPreferences API to clear saved user data
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();

                // Navigate back to the login screen
                // This can be done by starting a new activity or fragment
                // For example, you can use the Navigation Component to navigate to the login screen
                // Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_loginFragment);

                // Show a toast message to confirm sign out
                Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();

                // Navigate to the sign in screen
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
