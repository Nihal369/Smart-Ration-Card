package com.smartcard.smartrationcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    //Move to admin panel activity
    public void moveToAdminPanel(View view)
    {
        Intent intent=new Intent(this,AdminPanel.class);
        startActivity(intent);
        finish();
    }
}
