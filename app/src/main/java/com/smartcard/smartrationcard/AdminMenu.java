package com.smartcard.smartrationcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminMenu extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    public void goToAddUser(View view)
    {
        intent=new Intent(this,AddUser.class);
        startActivity(intent);
    }

    public void goToRemoveUser(View view)
    {
        intent=new Intent(this,RemoveUser.class);
        startActivity(intent);
    }

    public void goToAdminPanel(View view)
    {
        intent=new Intent(this,AdminPanel.class);
        startActivity(intent);
    }
}
