package com.smartcard.smartrationcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class AdminPanel extends AppCompatActivity {

    //Object Decleration
    EditText ricePriceEditText,wheatPriceEditText,pulsesPriceEditText,kerosenePriceEditText,riceQuantityEditText,wheatQuantityEditText,
    keroseneQuantityEditText,pulsesQuantityEditText,aplFactorEditText,bplFactorEditText;

    String ricePrice,wheatPrice,pulsesPrice,kerosenePrice,riceQuantity,wheatQuantity,keroseneQuantity,pulsesQuantity,aplFactor,bplFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        //Object initializations
        ricePriceEditText=findViewById(R.id.ricepriceedittext);
        wheatPriceEditText=findViewById(R.id.wheatpriceedittext);
        pulsesPriceEditText=findViewById(R.id.pulsespriceedittext);
        kerosenePriceEditText=findViewById(R.id.kerosenepriceedittext);
        riceQuantityEditText=findViewById(R.id.ricequanityeedittext);
        wheatQuantityEditText=findViewById(R.id.wheatquanityeedittext);
        keroseneQuantityEditText=findViewById(R.id.kerosenequanityeedittext);
        pulsesQuantityEditText=findViewById(R.id.pulsesquanityeedittext);
        aplFactorEditText=findViewById(R.id.aplfactoredittext);
        bplFactorEditText=findViewById(R.id.bplfactoredittext);


        //Set hint of the edit texts
        ricePriceEditText.setText(String.valueOf(RationShop.getRicePrice()));
        wheatPriceEditText.setText(String.valueOf(RationShop.getWheatPrice()));
        kerosenePriceEditText.setText(String.valueOf(RationShop.getKerosenePrice()));
        pulsesPriceEditText.setText(String.valueOf(RationShop.getPulsesPrice()));
        riceQuantityEditText.setText(String.valueOf(RationShop.getRiceQuantity()));
        wheatQuantityEditText.setText(String.valueOf(RationShop.getWheatQuanity()));
        keroseneQuantityEditText.setText(String.valueOf(RationShop.getKeroseneQuantity()));
        pulsesQuantityEditText.setText(String.valueOf(RationShop.getPulsesQuantity()));
        aplFactorEditText.setText(String.valueOf(RationShop.getAplFactor()));
        bplFactorEditText.setText(String.valueOf(RationShop.getBplFactor()));
    }


    //Update Prices in the RationShop Class
    public void updatePrices(View view) {
        try {
            ricePrice = ricePriceEditText.getText().toString();
            RationShop.setRicePrice(Integer.parseInt(ricePrice));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            wheatPrice = wheatPriceEditText.getText().toString();
            RationShop.setWheatPrice(Integer.parseInt(wheatPrice));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            pulsesPrice = pulsesPriceEditText.getText().toString();
            RationShop.setPulsesPrice(Integer.parseInt(pulsesPrice));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            kerosenePrice = kerosenePriceEditText.getText().toString();
            RationShop.setKerosenePrice(Integer.parseInt(kerosenePrice));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            riceQuantity = riceQuantityEditText.getText().toString();
            RationShop.setRiceQuantity(Integer.parseInt(riceQuantity));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            wheatQuantity = wheatQuantityEditText.getText().toString();
            RationShop.setWheatQuantity(Integer.parseInt(wheatQuantity));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            pulsesQuantity = pulsesQuantityEditText.getText().toString();
            RationShop.setPulsesQuantity(Integer.parseInt(pulsesQuantity));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            keroseneQuantity = keroseneQuantityEditText.getText().toString();
            RationShop.setKeroseneQuantity(Integer.parseInt(keroseneQuantity));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            aplFactor = aplFactorEditText.getText().toString();
            RationShop.setAplFactor(Integer.parseInt(aplFactor));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            bplFactor = bplFactorEditText.getText().toString();
            RationShop.setBplFactor(Integer.parseInt(bplFactor));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        //Display a success toast
        Toasty.success(this, "Update Successful", Toast.LENGTH_SHORT).show();

        //Move to RFID_READ Activity
        Intent intent = new Intent(this, RFID_Read.class);
        startActivity(intent);
        finish();
    }

    public void cancelActivity(View view)
    {
        Intent intent=new Intent(this,RFID_Read.class);
        startActivity(intent);
        finish();
    }
}
