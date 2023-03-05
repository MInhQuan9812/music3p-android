package com.example.deannhom;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WellcomeActivity extends AppCompatActivity {

    private View background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        background = (View) findViewById(R.id.background);




        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            background.setBackground(this.getDrawable(R.drawable.drawable_linear_bg_api31));
        } else {

            /**
             * If the current version is under S version, it does not allow to set angle is under 45.
             * In others word, it must be multiple of 45.
             */


            background.setBackground(this.getDrawable(R.drawable.drawable_linear_bg_api26));
        }

//        Button mSigInUpButton=(Button)findViewById(R.id.signInBtn);
//        mSigInUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(WellcomeActivity.this,SignUpActivity.class));
//            }
//        });
    }

}