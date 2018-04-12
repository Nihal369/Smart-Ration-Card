package com.smartcard.smartrationcard;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import es.dmoral.toasty.Toasty;

public class RFID_Read extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    String tagInfo;
    private DatabaseReference mRootRef,rationCardNumberRef,userRef;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Objective:Initialize the NFC Adapter Object and  Other Objects
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid__read);

        //Initialize the NFC Adapter with the default NFC adapter of the phone
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        //Check whether the phone has nfc or if nfc is not enabled
        if(nfcAdapter == null)
        {
            Toasty.error(this,"PHONE DOES NOT HAVE NFC",Toast.LENGTH_LONG).show();
        }
        else if(!nfcAdapter.isEnabled())
        {
            Toasty.warning(this,"PLEASE ENABLE NFC",Toast.LENGTH_LONG).show();
        }

        //Pending Intent Object is used to launch the activity when an RFID card is detected
        pendingIntent=PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        //Objective:Enable NFC Reading when the app is resumed
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        //Objective:Disable NFC Reading when the app is paused
        super.onPause();
        if(nfcAdapter!=null)
        {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //Objective:When an RFID Card is read,the phone launches an intent,This function executes the code inside it when the RFID Card is read
        super.onNewIntent(intent);

        //Identify the intent type,We need the intent type TAG_DISCOVERED
        String action = intent.getAction();
        //Used to clear any unwanted intents,Eg:Camera intent,Browser intent etc
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            //Ration Card is detected successfully
            Toasty.success(this, "RATION CARD DETECTED", Toast.LENGTH_LONG).show();

            //Get the tag object from the intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            //If there is no card with the intent,Something is wrong
            if (tag == null)
            {
                Toasty.error(this, "ERROR READING THE CARD", Toast.LENGTH_LONG).show();
            }

            else
            {
                //Get the tag serial number
                //Serial number from the tag is Hex from,We need to convert it to a string
                //Initialize a blank string as the serial number
                tagInfo="";

                //Get the serial number in a hex array from
                byte[] tagId = tag.getId();

                //Convert the hex array to String
                for (byte aTagId : tagId) {
                    tagInfo += Integer.toHexString(aTagId & 0xFF);
                }

                Log.i("TAG info",tagInfo);

                //Process the RFID Card info
                processRFID();
            }
        }
    }

    //Process RFID tag id
    public void processRFID() {

        //Find the subtree having the root as the serial number of the card
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //Get the child reference
        rationCardNumberRef = mRootRef.child("rationcardnumber");
        //rationcardnumber->1000,1001 etc
        userRef = rationCardNumberRef.child(tagInfo);

        //Check if the ration card number is registered with the database
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
