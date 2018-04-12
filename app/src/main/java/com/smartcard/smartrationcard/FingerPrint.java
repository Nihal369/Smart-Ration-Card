package com.smartcard.smartrationcard;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class FingerPrint extends AppCompatActivity implements FingerPrintAuthCallback {

    //Object decelerations
    private DatabaseReference mRootRef,rationCardNumberRef,userRef;
    Map<String, String> fireBaseMap;
    ImageView profilePic;
    TextView userNameText,categoryText,numberOfRationsText,numberOfFamilyNumbersText,emailText,phoneNumberText;
    FingerPrintAuthHelper mFingerPrintAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);
        profilePic=findViewById(R.id.profilePicImageView);
        userNameText=findViewById(R.id.userNameTextView);
        categoryText=findViewById(R.id.categoryTextView);
        numberOfRationsText=findViewById(R.id.numberOfRationsTextView);
        numberOfFamilyNumbersText=findViewById(R.id.numberOfFamilyMembersTextView);
        emailText=findViewById(R.id.emailTextView);
        phoneNumberText=findViewById(R.id.phoneNumberTextView);

        getInfoFromFireBase();

        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this,this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //start finger print authentication
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }




    private void getInfoFromFireBase()
    {
        getDataBaseReference();
        //Array list to store user details
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Map that stores retrived data from the DataSnapshot
                if(dataSnapshot!=null) {
                    fireBaseMap = (HashMap<String, String>) dataSnapshot.getValue();
                }


                //Retrieve each value of the map
                retrieveDataFromFirebaseMap();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void getDataBaseReference()
    {
            //Get the database reference
            mRootRef = FirebaseDatabase.getInstance().getReference();
            //Get the child reference
            rationCardNumberRef = mRootRef.child("rationcardnumber");
            //rationcardnumber->1000,1001 etc
            userRef = rationCardNumberRef.child(LocalDB.getRationCardID());
    }


    private void retrieveDataFromFirebaseMap()
    {
        if(fireBaseMap!=null) {
            //Retrieve data from the snapshot map
            for (String key : fireBaseMap.keySet()) {
                switch (key)
                {
                    case "category":LocalDB.setCategory(String.valueOf(fireBaseMap.get(key)));
                                    break;
                    case "familyMembers":LocalDB.setFamilyMembers(Integer.parseInt(String.valueOf(fireBaseMap.get(key))));
                                    break;
                    case "name":LocalDB.setUserName(String.valueOf(fireBaseMap.get(key)));
                                    break;
                    case "numberOfRations":LocalDB.setNumberOfRations(Integer.parseInt(String.valueOf(fireBaseMap.get(key))));
                                    break;
                    case "profilePic":LocalDB.setProfilePicUri(Uri.parse(String.valueOf(fireBaseMap.get(key))));
                                    break;
                    case "email":LocalDB.setEmailID(String.valueOf(fireBaseMap.get(key)));
                                    break;
                    case "phoneNumber":LocalDB.setPhoneNumber(String.valueOf(fireBaseMap.get(key)));
                                    break;
                }
            }
        }

        setUI();
    }

    private void setUI()
    {
        //Set the UI with retrieved data
        Picasso.with(this).
                load(LocalDB.getProfilePicUri())
                .placeholder(R.drawable.profpic)
                .error(R.drawable.profpic)
                .transform(new CircleTransform())
                .into(profilePic);

        userNameText.setText(LocalDB.getUserName());
        categoryText.setText("Category:"+LocalDB.getCateogry());
        numberOfRationsText.setText("Rations This Month:"+String.valueOf(LocalDB.getNumberOfRations()));
        numberOfFamilyNumbersText.setText("Family Members:"+String.valueOf(LocalDB.getFamilyMembers()));
        emailText.setText(LocalDB.getEmailID());
        phoneNumberText.setText(LocalDB.getPhoneNumber());
    }


    public void moveToNextActivity()
    {
        Intent myIntent=new Intent(this,RationDistribution.class);
        startActivity(myIntent);
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        //Device does not have finger print scanner.
        Toasty.error(this, "Device does not support Finger-prints", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoFingerPrintRegistered() {
        //There are no finger prints registered on this device.
        Toasty.error(this, "No Fingerprints registered in this device", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBelowMarshmallow() {
        //Device running below API 23 version of android that does not support finger print authentication.
        Toasty.error(this, "The Android Version does not support Finger-prints", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        //Authentication successful
        Toasty.success(this, "Success", Toast.LENGTH_SHORT).show();
        moveToNextActivity();
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        //Authentication failed
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                //Cannot recognize the fingerprint scanned.
                Toasty.error(this, "Wrong Fingerprint", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
