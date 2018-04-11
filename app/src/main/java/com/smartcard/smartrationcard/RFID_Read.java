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
    String tagInfo;
    private DatabaseReference mRootRef,rationCardNumberRef,userRef;
    PendingIntent pendingIntent;

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

        pendingIntent=PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }



    @Override
    protected void onPause() {
        super.onPause();
        if(nfcAdapter!=null)
        {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //Intent intent=getIntent();
        String action = intent.getAction();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Toasty.success(this, "RATION CARD DETECTED", Toast.LENGTH_LONG).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            if (tag == null)
            {
                Toasty.error(this, "ERROR READING THE CARD", Toast.LENGTH_LONG).show();
            }

            else
            {
                tagInfo="";

                byte[] tagId = tag.getId();

                for (byte aTagId : tagId) {
                    tagInfo += Integer.toHexString(aTagId & 0xFF);
                }

                processRFID();
            }
        }
    }

    //Process RFID tag id
    public void processRFID() {

        Log.i("NIHAL","INSIDE PROCESS RFID");
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //Get the child reference
        rationCardNumberRef = mRootRef.child("rationcardnumber");
        //rationcardnumber->1000,1001 etc
        userRef = rationCardNumberRef.child(tagInfo);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    LocalDB.setRationCardID(tagInfo);
                    Intent intent = new Intent(RFID_Read.this, FingerPrint.class);
                    startActivity(intent);
                }
                else
                {
                    Toasty.error(RFID_Read.this, "Invalid User", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Move to AdminLogin Activity
    public void moveToAdminLogin(View view)
    {
        Intent intent = new Intent(RFID_Read.this, AdminLogin.class);
        startActivity(intent);
    }

}
