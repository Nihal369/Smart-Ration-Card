package com.smartcard.smartrationcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

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
        adminID=adminIdEditText.getText().toString();
        adminPassword=adminPasswordEditText.getText().toString();

        if(adminID.equals("admin") && adminPassword.equals("admin")) {
            Toasty.success(this, "Admin Login Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AdminPanel.class);
            startActivity(intent);
            finish();
        }

        else
        {
            Toasty.error(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
