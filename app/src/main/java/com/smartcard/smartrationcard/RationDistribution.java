package com.smartcard.smartrationcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RationDistribution extends AppCompatActivity {

    int ricePrice,wheatPrice,kerosenePrice,pulsesPrice,totalPrice,aplOrBplFactor,familyMembers;
    ImageView profilePic;
    TextView userNameText,categoryText,numberOfFamilyNumbersText,ricePriceText,wheatPriceText,kerosenePriceText,pulsesPriceText,totalPriceText,riceText,wheatText,keroseneText,pulsesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ration_distribution);

        //Object initializations
        profilePic=findViewById(R.id.profilePicImageView2);
        userNameText=findViewById(R.id.userNameTextView2);
        categoryText=findViewById(R.id.categoryTextView2);
        numberOfFamilyNumbersText=findViewById(R.id.numberOfFamilyMembersTextView2);
        ricePriceText=findViewById(R.id.ricePriceTextView);
        wheatPriceText=findViewById(R.id.wheatPriceTextView);
        kerosenePriceText=findViewById(R.id.kerosenePriceTextView);
        pulsesPriceText=findViewById(R.id.pulsesPriceTextView);
        totalPriceText=findViewById(R.id.totalPriceTextView);
        riceText=findViewById(R.id.riceTextView);
        wheatText=findViewById(R.id.wheatTextView);
        keroseneText=findViewById(R.id.keroseneTextView);
        pulsesText=findViewById(R.id.pulsesTextView);

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
        numberOfFamilyNumbersText.setText("Family Members:"+String.valueOf(LocalDB.getFamilyMembers()));


        //Set UI for the commodities
        familyMembers=LocalDB.getFamilyMembers();

        if(LocalDB.getCateogry().equals("APL"))
        {
            aplOrBplFactor=RationShop.getAplFactor();
        }
        else
        {
            aplOrBplFactor=RationShop.getBplFactor();
        }

        //Set UI
        riceText.setText("Rice:"+(familyMembers*RationShop.getRiceQuantity()*aplOrBplFactor)+"KG");
        wheatText.setText("Wheat:"+(familyMembers*RationShop.getWheatQuanity()*aplOrBplFactor)+"KG");
        keroseneText.setText("Kerosene:"+(familyMembers*RationShop.getKeroseneQuantity()*aplOrBplFactor)+"LTR");
        pulsesText.setText("Rice:"+(familyMembers*RationShop.getPulsesQuantity()*aplOrBplFactor)+"KG");

        ricePriceText.setText("PRICE:"+(RationShop.getRicePrice()/aplOrBplFactor)+" RS/KG");
        wheatPriceText.setText("PRICE:"+(RationShop.getWheatPrice()/aplOrBplFactor)+" RS/KG");
        kerosenePriceText.setText("PRICE:"+(RationShop.getKerosenePrice()/aplOrBplFactor)+" RS/LTR");
        pulsesPriceText.setText("PRICE:"+(RationShop.getPulsesPrice()/aplOrBplFactor)+" RS/KG");

        //Calculate total price
        ricePrice=familyMembers*RationShop.getRiceQuantity()*RationShop.getRicePrice()*aplOrBplFactor;
        wheatPrice=familyMembers*RationShop.getWheatQuanity()*RationShop.getWheatPrice()*aplOrBplFactor;
        kerosenePrice=familyMembers*RationShop.getKeroseneQuantity()*RationShop.getKerosenePrice()*aplOrBplFactor;
        pulsesPrice=familyMembers*RationShop.getPulsesQuantity()*RationShop.getPulsesPrice()*aplOrBplFactor;

        totalPrice=(ricePrice+wheatPrice+kerosenePrice+pulsesPrice)/(aplOrBplFactor);
        totalPriceText.setText("TOTAL PRICE:"+totalPrice+"RS");
    }

    public void completePurchase(View view)
    {
        Intent intent=new Intent(RationDistribution.this,PurchaseComplete.class);
        startActivity(intent);
        finish();
    }
}
