package com.example.deannhom.fragment;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.deannhom.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AccountFragment extends Fragment {

    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView playlistAmountTextView;
    private TextView creationTimeTextView;
    private Button signOutButton;



    private SQLiteDatabase mDatabase;


    private static final String TABLE_NAME = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PLAYLIST_AMOUNT = "playlist_amount";
    private static final String COLUMN_CREATION_TIME = "creation_time";
    private static final String COLUMN_PASSWORD = "password";

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize UI elements
        usernameTextView = rootView.findViewById(R.id.usernameTextView);
        emailTextView = rootView.findViewById(R.id.emailTextView);
        playlistAmountTextView = rootView.findViewById(R.id.playlistAmountTextView);
        creationTimeTextView = rootView.findViewById(R.id.creationTimeTextView);
        signOutButton = rootView.findViewById(R.id.signOutButton);

        // Create / Open the database
        DBHelper dbHelper = new DBHelper(getActivity());
        mDatabase = dbHelper.getReadableDatabase();

        // Get the user's data from the database
        Cursor cursor = mDatabase.query(TABLE_NAME, new String[]{COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PLAYLIST_AMOUNT, COLUMN_CREATION_TIME},
                null, null, null, null, null);

        int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
        int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
        int playlistAmountIndex = cursor.getColumnIndex(COLUMN_PLAYLIST_AMOUNT);
        int creationTimeIndex = cursor.getColumnIndex(COLUMN_CREATION_TIME);

        if (emailIndex >= 0) {
            String email = cursor.getString(emailIndex);
            emailTextView.setText(email);
        }

        if (usernameIndex >= 0) {
            String username = cursor.getString(usernameIndex);
            usernameTextView.setText(username);
        }

        if (playlistAmountIndex >= 0) {
            int playlistAmount = cursor.getInt(playlistAmountIndex);
            playlistAmountTextView.setText(String.valueOf(playlistAmount));
        }

        if (creationTimeIndex >= 0) {
            String creationTime = cursor.getString(creationTimeIndex);
            creationTimeTextView.setText(creationTime);
        }


        if (cursor != null) {
            cursor.close();
        }

        // Set up sign out button click listener
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
                // TODO: Implement sign out logic
            }
        });

        return rootView;
    }

    private static class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "my_db";
        private static final int DATABASE_VERSION = 1;
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_PLAYLIST_AMOUNT + " INTEGER, " +
                COLUMN_CREATION_TIME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
    }
}
