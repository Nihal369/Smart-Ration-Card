package com.smartcard.smartrationcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class AddUser extends AppCompatActivity {

    //Object Decelerations
    EditText rationCardEditText,nameEditText,numberOfFamilyMembersEditText,mobileNumberEditText,emailEditText,profilePicEditText;
    //RadioGroup is a container that holds Radio Buttons
    RadioGroup categoryRadioGroup;
    String rationCardNumber,userName,mobileNumber,emailID,userCategory,profilePicUrl;
    int numberOfFamilyMembers;
    DatabaseReference mRootRef,numberRef,userRef;
    boolean isDuplicate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //Object initializations
        rationCardEditText=findViewById(R.id.numberEditText);
        nameEditText=findViewById(R.id.nameEditText);
        numberOfFamilyMembersEditText=findViewById(R.id.familyMembersEditText);
        mobileNumberEditText=findViewById(R.id.phoneNumberEditText);
        emailEditText=findViewById(R.id.emailEditText);
        profilePicEditText=findViewById(R.id.profilePicEditText);
        categoryRadioGroup=findViewById(R.id.categoryRadioGroup);
    }

    public void addUser(View view) {
        //Get texts from the text fields
        rationCardNumber = rationCardEditText.getText().toString();
        userName = nameEditText.getText().toString();
        if (!numberOfFamilyMembersEditText.getText().toString().equals("")) {
            numberOfFamilyMembers = Integer.parseInt(numberOfFamilyMembersEditText.getText().toString());
        }
        mobileNumber = mobileNumberEditText.getText().toString();
        emailID = emailEditText.getText().toString();
        profilePicUrl = profilePicEditText.getText().toString();

        //Get the selected button inside the radio group
        if (categoryRadioGroup.getCheckedRadioButtonId() == R.id.bplButton) {
            userCategory = "BPL";
        } else {
            userCategory = "APL";
        }


        if (isEditTextDataValid()) {
            //Get the database reference
            getDataBaseReference();

            //Add details to the created child
            userRef.child("category").setValue(userCategory);
            userRef.child("familyMembers").setValue(numberOfFamilyMembers);
            userRef.child("name").setValue(userName);
            userRef.child("numberOfRations").setValue(0);
            userRef.child("profilePic").setValue(profilePicUrl);//Standard Image
            userRef.child("email").setValue(emailID);
            userRef.child("phoneNumber").setValue(mobileNumber);
            Toasty.success(AddUser.this, "User added successfully", Toast.LENGTH_SHORT).show();
        }

    }

    private void getDataBaseReference()
    {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        numberRef = mRootRef.child("rationcardnumber");
        userRef = numberRef.child(rationCardNumber);
    }

    private boolean isEditTextDataValid()
    {
        //Check whether data entered in the edit texts are valid
        if(isRationCardNumberBlank())
        {
            return false;
        }

        if(isRationCardNumberDuplicate())
        {
            return false;
        }

        if(isNumberOfFamilyMembersBlank())
        {
            return false;
        }

        if(isNameBlank())
        {
            return false;
        }

        if(isPhoneNumberBlank())
        {
            return false;
        }

        if(isPhoneNumberNotTenDigits())
        {
            return false;
        }
        return true;
    }

    private boolean isRationCardNumberBlank()
    {
        if(rationCardEditText.getText().toString().equals(""))
        {
            //Ration Card Number field is blank,return true
            Toasty.warning(AddUser.this, "Ration Card Number is blank", Toast.LENGTH_SHORT).show();
            return  true;
        }
        //Ration Card Number field is not blank,so return false
        return false;
    }

    private boolean isRationCardNumberDuplicate()
    {

        //Get database reference
        getDataBaseReference();

        //Check if user exists
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Check if the user for the ration card number entered exists
                if(dataSnapshot.exists()) {
                    Toasty.warning(AddUser.this, "Ration Card Number is not Unique", Toast.LENGTH_SHORT).show();
                    isDuplicate = true;
                }
                else
                {
                    isDuplicate=false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Return whether the Ration Card Number is duplicate or not;
       if(isDuplicate)
       {
           return true;
       }
       else
       {
           return false;
       }
    }

    private boolean isNumberOfFamilyMembersBlank()
    {
        if(numberOfFamilyMembersEditText.getText().toString().equals(""))
        {
            Toasty.warning(AddUser.this, "Number of Family Members is blank", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isNameBlank()
    {
        if(nameEditText.getText().toString().equals(""))
        {
            Toasty.warning(AddUser.this, "Name is blank", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isPhoneNumberBlank()
    {
        if(mobileNumberEditText.getText().toString().equals(""))
        {
            Toasty.warning(AddUser.this, "Mobile number is blank", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isPhoneNumberNotTenDigits()
    {
        if(mobileNumberEditText.getText().toString().length()!=10)
        {
            Toasty.warning(AddUser.this, "Mobile number is not ten digits", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
