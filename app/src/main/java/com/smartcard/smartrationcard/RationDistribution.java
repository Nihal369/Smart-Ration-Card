package com.smartcard.smartrationcard;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RationDistribution extends AppCompatActivity {

    int ricePrice,wheatPrice,kerosenePrice,pulsesPrice,totalPrice,aplOrBplFactor,familyMembers;
    ImageView profilePic;
    TextView userNameText,categoryText,numberOfFamilyNumbersText,ricePriceText,wheatPriceText,kerosenePriceText,pulsesPriceText,totalPriceText,riceText,wheatText,keroseneText,pulsesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ration_distribution);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy( new StrictMode.ThreadPolicy.Builder().permitAll().build() );
        }

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
        pulsesText.setText("Pulses:"+(familyMembers*RationShop.getPulsesQuantity()*aplOrBplFactor)+"KG");

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
        //Complete a purchase
        sendEmail();
        sendSMS();
        //Move to next activity
        Intent intent=new Intent(RationDistribution.this,PurchaseComplete.class);
        startActivity(intent);
        finish();
    }


    private void sendEmail()
    {
        //Send a email of the purchase invoice

        //GET THE CURRENT DATE
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(date);


        //EDIT CONTENT OF EMAIL HERE
        String emailContent=
                        "Details of your purchase from Ration Shop on "+formattedDate
                        +"\n\n"
                        +"Customer: "
                        +LocalDB.getUserName()
                        +"\n"
                        +"Ration Card Number: "
                        +LocalDB.getRationCardID()
                        +"\n\n"

                        +"Rice "+(familyMembers*RationShop.getRiceQuantity()*aplOrBplFactor)+" KG: "
                        +familyMembers*RationShop.getRiceQuantity()*RationShop.getRicePrice()*aplOrBplFactor+" RS"
                        +"\n"
                        +"Wheat "+(familyMembers*RationShop.getWheatQuanity()*aplOrBplFactor)+" KG: "
                        +familyMembers*RationShop.getWheatQuanity()*RationShop.getWheatPrice()*aplOrBplFactor+" RS"
                        +"\n"
                        +"Kerosene "+(familyMembers*RationShop.getKeroseneQuantity()*aplOrBplFactor)+" LTR: "
                        +familyMembers*RationShop.getKeroseneQuantity()*RationShop.getKerosenePrice()*aplOrBplFactor+" RS"
                        +"\n"
                        +"Pulses "+(familyMembers*RationShop.getPulsesQuantity()*aplOrBplFactor)+" KG: "
                        +familyMembers*RationShop.getPulsesQuantity()*RationShop.getPulsesPrice()*aplOrBplFactor+" RS"
                        +"\n\n"
                        +"TOTAL PRICE: "
                        +totalPrice+" RS";


        //Send a mail to the customer
        BackgroundMail.newBuilder(this)
                .withUsername("smartrationcardvjc@gmail.com")
                .withPassword("agnirose")
                .withMailto(LocalDB.getEmailID())
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("Invoice for Ration Shop Purchase")
                .withBody(emailContent)
                .send();
    }


    private void sendSMS() {

        //Send SMS of the purchase invoice
        //EDIT CONTENT OF SMS HERE
        String smsContent =
                "\n\nCUSTOMER:"
                        + LocalDB.getUserName()
                        + "\n\n\n"

                        + "Rice:" + (familyMembers * RationShop.getRiceQuantity() * aplOrBplFactor) + "KG: "
                        + familyMembers * RationShop.getRiceQuantity() * RationShop.getRicePrice() * aplOrBplFactor
                        + "\n"
                        + "Wheat:" + (familyMembers * RationShop.getWheatQuanity() * aplOrBplFactor) + "KG: "
                        + familyMembers * RationShop.getWheatQuanity() * RationShop.getWheatPrice() * aplOrBplFactor
                        + "\n"
                        + "Kerosene:" + (familyMembers * RationShop.getKeroseneQuantity() * aplOrBplFactor) + "LTR: "
                        + familyMembers * RationShop.getKeroseneQuantity() * RationShop.getKerosenePrice() * aplOrBplFactor
                        + "\n"
                        + "Pulses:" + (familyMembers * RationShop.getPulsesQuantity() * aplOrBplFactor) + "KG: "
                        + familyMembers * RationShop.getPulsesQuantity() * RationShop.getPulsesPrice() * aplOrBplFactor
                        + "\n"
                        + "TOTAL PRICE:"
                        + totalPrice;

        //Calling the twilio api
        String ACCOUNT_SID,AUTH_TOKEN,TWILIO_PHONE_NUM;

        ACCOUNT_SID="AC358ecf2b8d1b56cd58a867fa34283a6b";
        AUTH_TOKEN="d86ad7473050dbd74cb520e9347b6ac1";
        TWILIO_PHONE_NUM="+1 858-239-2356";

        OkHttpClient client = new OkHttpClient();
        String url = "https://api.twilio.com/2010-04-01/Accounts/"+ACCOUNT_SID+"/SMS/Messages";
        String base64EncodedCredentials = "Basic " + Base64.encodeToString((ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP);

        RequestBody body = new FormBody.Builder()
                .add("From", TWILIO_PHONE_NUM)
                .add("To", "+91"+LocalDB.getPhoneNumber())
                .add("Body", smsContent)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", base64EncodedCredentials)
                .build();


        try {
            Response response = client.newCall(request).execute();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
