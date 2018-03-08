package com.smartcard.smartrationcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUser extends AppCompatActivity {

    //Object Decelerations
    EditText rationCardEditText,nameEditText,numberOfFamilyMembersEditText,mobileNumberEditText,emailEditText;
    //RadioGroup is a container that holds Radio Buttons
    RadioGroup categoryRadioGroup;
    String rationCardNumber,userName,numberOfFamilyMembers,mobileNumber,emailID,userCategory;
    DatabaseReference mRootRef,numberRef,userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //Object initializations
        rationCardEditText=findViewById(R.id.numberEditText);
        nameEditText=findViewById(R.id.nameEditText);
        numberOfFamilyMembersEditText=findViewById(R.id.familyMembersEditText);
        mobileNumberEditText=findViewById(R.id.phoneNumberEditText);
        emailEditText=findViewById(R.id.emailEditText);
        categoryRadioGroup=findViewById(R.id.categoryRadioGroup);
    }

    public void addUser(View view)
    {
        //Get texts from the text fields
        rationCardNumber=rationCardEditText.getText().toString();
        userName=nameEditText.getText().toString();
        numberOfFamilyMembers=numberOfFamilyMembersEditText.getText().toString();
        mobileNumber=mobileNumberEditText.getText().toString();
        emailID=emailEditText.getText().toString();

        //Get the selected button inside the radio group
        if(categoryRadioGroup.getCheckedRadioButtonId()==R.id.bplButton)
        {
            userCategory="BPL";
        }
        else
        {
            userCategory="APL";
        }


        //Get the database reference
        mRootRef = FirebaseDatabase.getInstance().getReference();
        numberRef = mRootRef.child("rationcardnumber");
        userRef=numberRef.child(rationCardNumber);

        //Add details to the created child
        userRef.child("category").setValue(userCategory);
        userRef.child("familyMembers").setValue(numberOfFamilyMembers);
        userRef.child("name").setValue(userName);
        userRef.child("numberOfRations").setValue("0");
        userRef.child("profPic").setValue("https://cdn2.iconfinder.com/data/icons/ios-7-icons/50/user_male2-256.png");//Standard Image
        userRef.child("email").setValue(emailID);
        userRef.child("phoneNumber").setValue(mobileNumber);

    }
}
