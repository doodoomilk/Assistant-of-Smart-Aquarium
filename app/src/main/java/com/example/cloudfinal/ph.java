package com.example.cloudfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ph extends AppCompatActivity {

    private TextView txt_ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            setTitle(mBundle.getString("Title"));
        }

        txt_ph = findViewById(R.id.txt_ph);
        txt_ph.setText("Coming soon.....");

    }
}
