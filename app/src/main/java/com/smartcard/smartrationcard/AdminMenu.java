package com.smartcard.smartrationcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminMenu extends AppCompatActivity {

    //Button Click functions for the three functions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    public void goToAddUser(View view)
    {
        Intent intent;
        intent=new Intent(this,AddUser.class);
        startActivity(intent);
    }

    public void goToRemoveUser(View view)
    {
        Intent intent;
        intent=new Intent(this,RemoveUser.class);
        startActivity(intent);
    }

    public void goToAdminPanel(View view)
    {
        Intent intent;
        intent=new Intent(this,AdminPanel.class);
        startActivity(intent);
    }
}
