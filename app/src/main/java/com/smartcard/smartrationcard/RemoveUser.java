package com.smartcard.smartrationcard;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import es.dmoral.toasty.Toasty;

public class RemoveUser extends AppCompatActivity {

    //Object decelerations
    EditText removeUserEditText;
    String rationCardNumber;
    DatabaseReference mRootRef,numberRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_user);

        //Object initialization
        removeUserEditText=findViewById(R.id.removeUserEditText);
    }

    public void removeUser(View view)
    {
        //Get the text entered in the editText form
        rationCardNumber=removeUserEditText.getText().toString();

        //Get the firebase reference
        mRootRef = FirebaseDatabase.getInstance().getReference();
        numberRef = mRootRef.child("rationcardnumber");

        //Check if user exists
        numberRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Check if the user for the ration card number entered exists
                if(dataSnapshot.exists())
                {
                    //Delete the entire node corresponding
                    new FancyAlertDialog.Builder(RemoveUser.this)
                            .setTitle("Attend the victim")
                            .setBackgroundColor(Color.parseColor("#F44336"))  //Don't pass R.color.colorvalue
                            .setMessage("Are you sure you want to delete the user")
                            .setNegativeBtnText("No")
                            .setPositiveBtnBackground(Color.parseColor("#D32F2F"))  //Don't pass R.color.colorvalue
                            .setPositiveBtnText("Yes")
                            .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                            .setAnimation(Animation.POP)
                            .isCancellable(true)
                            .setIcon(R.drawable.ic_error_outline_white_48dp, Icon.Visible)

                            .OnPositiveClicked(new FancyAlertDialogListener() {
                                @Override
                                public void OnClick() {

                                    numberRef.child(rationCardNumber).removeValue();
                                    Toasty.success(RemoveUser.this, rationCardNumber + "deleted", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .OnNegativeClicked(new FancyAlertDialogListener() {
                                @Override
                                public void OnClick() {
                                }
                            })
                            .build();


                }
                else
                {
                    Toasty.error(RemoveUser.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
