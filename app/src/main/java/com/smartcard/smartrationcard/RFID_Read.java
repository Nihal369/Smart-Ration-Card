package com.smartcard.smartrationcard;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;

import es.dmoral.toasty.Toasty;

public class RFID_Read extends AppCompatActivity {

    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid__read);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter == null)
        {
            Toasty.error(this,"PHONE DOES NOT HAVE NFC",Toast.LENGTH_LONG).show();
        }
        else if(!nfcAdapter.isEnabled())
        {
            Toasty.warning(this,"PLEASE ENABLE NFC",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Toasty.success(this, "RATION CARD DETECTED", Toast.LENGTH_LONG).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            if (tag == null)
            {
                Toasty.error(this, "ERROR READING THE CARD", Toast.LENGTH_LONG).show();
            }

            else
            {
                String tagInfo="";

                byte[] tagId = tag.getId();

                for (byte aTagId : tagId) {
                    tagInfo += Integer.toHexString(aTagId & 0xFF);
                }

                Toasty.success(this, tagInfo, Toast.LENGTH_LONG).show();
                Log.i("NIHAL",tagInfo);
            }
        }


    }

    //Move to AdminLogin Activity
    public void moveToAdminLogin(View view)
    {
        Intent intent = new Intent(RFID_Read.this, AdminLogin.class);
        startActivity(intent);
    }
}
