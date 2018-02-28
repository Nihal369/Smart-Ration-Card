package com.smartcard.smartrationcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class AdminLogin extends AppCompatActivity {

    //Object declerations
    EditText adminIdEditText,adminPasswordEditText;
    String adminID,adminPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        //Object initializations
        adminIdEditText=findViewById(R.id.adminIDEditText);
        adminPasswordEditText=findViewById(R.id.adminPasswordEditText);
    }
}
