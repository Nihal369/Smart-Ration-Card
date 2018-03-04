package com.smartcard.smartrationcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class AdminPanel extends AppCompatActivity {

    //Object Decleration
    EditText ricePriceEditText,wheatPriceEditText,pulsesPriceEditText,kerosenePriceEditText,riceQuantityEditText,wheatQuantityEditText,
    keroseneQuantityEditText,pulsesQuantityEditText,aplFactorEditText,bplFactorEditText;

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
        ricePriceEditText.setHint(RationShop.getRicePrice());
        wheatPriceEditText.setHint(RationShop.getWheatPrice());
        kerosenePriceEditText.setHint(RationShop.getKerosenePrice());
        pulsesPriceEditText.setHint(RationShop.getPulsesPrice());
        riceQuantityEditText.setHint(RationShop.getRiceQuantity());
        wheatQuantityEditText.setHint(RationShop.getWheatQuanity());
        keroseneQuantityEditText.setHint(RationShop.getKeroseneQuantity());
        pulsesQuantityEditText.setHint(RationShop.getPulsesQuantity());
        aplFactorEditText.setHint(RationShop.getAplFactor());
        bplFactorEditText.setHint(RationShop.getBplFactor());
    }
}
