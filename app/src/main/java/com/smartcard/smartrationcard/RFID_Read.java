package com.smartcard.smartrationcard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.NfcAdapter;
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

import es.dmoral.toasty.Toasty;

public class RFID_Read extends AppCompatActivity {

    //Object and Variable Decelerations
    private String rfidTagValue;
    private NfcAdapter mNFCAdapter;
    EditText editText;
    private DatabaseReference mRootRef,rationCardNumberRef,userRef;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid__read);

        //Initialize the edit text object
        editText=findViewById(R.id.editText);
    }

    //Manually Enter Ration Card Number
    public void enterRfidTagValue(View view)
    {
        if(editText.getText()!=null) {
            rfidTagValue = editText.getText().toString();
            mRootRef = FirebaseDatabase.getInstance().getReference();
            //Get the child reference
            rationCardNumberRef = mRootRef.child("rationcardnumber");
            //rationcardnumber->1000,1001 etc
            userRef = rationCardNumberRef.child(rfidTagValue);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        LocalDB.setRationCardID(rfidTagValue);
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
    }
}
