package com.example.deannhom;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deannhom.helper.DbHelper;

public class EditProfileActivity extends AppCompatActivity {
    private TextView username;
    private TextView email;
    private TextView password;
    private Button completedBtn;
    DbHelper DB;
    private SQLiteDatabase mDatabase;

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        completedBtn = findViewById(R.id.completedBtn);

        DB = new DbHelper(this);
        mDatabase = DB.getReadableDatabase();

        Cursor cursor = mDatabase.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PASSWORD},
                COLUMN_ID + "= " + 1, null, null, null, null);

        while (cursor.moveToNext()) {
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int passwordIndex=cursor.getColumnIndex(COLUMN_PASSWORD);
            if (emailIndex >= 0) {
                String emailI = cursor.getString(emailIndex);
                email.setText(emailI);
            }
            if (usernameIndex >= 0) {
                String usernameI = cursor.getString(usernameIndex);
                username.setText(usernameI);
            }
            if (usernameIndex >= 0) {
                String passwordI = cursor.getString(passwordIndex);
                password.setText(passwordI);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameEdit=username.getText().toString();
                String emailEdit=email.getText().toString();
                String passwordEdit=password.getText().toString();
                Boolean savedata=DB.saveUserData(1,usernameEdit,emailEdit,passwordEdit);

                if(TextUtils.isEmpty(usernameEdit)||TextUtils.isEmpty(emailEdit)||TextUtils.isEmpty(passwordEdit)){
                    Toast.makeText(EditProfileActivity.this,"Add information",Toast.LENGTH_SHORT).show();
                }else{
                    if(savedata==true){
                        Toast.makeText(EditProfileActivity.this,"Đã cập nhật thông tin",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(EditProfileActivity.this,"Người dùng đã tồn tại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}