package com.smartcard.smartrationcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        sendEmail();
        sendSMS();
        //Move to next activity
        Intent intent=new Intent(RationDistribution.this,PurchaseComplete.class);
        startActivity(intent);
        finish();
    }


    private void sendEmail()
    {
        //GET THE CURRENT DATE
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(date);


        //EDIT CONTENT OF EMAIL HERE
        String emailContent=
                        "Details of your purchase from Ration Shop on "+formattedDate
                        +"\n"
                        +"CUSTOMER:"
                        +LocalDB.getUserName()
                        +"\n"
                        +"RATION CARD NUMBER:"
                        +LocalDB.getRationCardID()
                        +"\n\n\n"

                        +"Rice:"+(familyMembers*RationShop.getRiceQuantity()*aplOrBplFactor)+"KG:"
                        +familyMembers*RationShop.getRiceQuantity()*RationShop.getRicePrice()*aplOrBplFactor
                        +"\n"
                        +"Wheat:"+(familyMembers*RationShop.getWheatQuanity()*aplOrBplFactor)+"KG:"
                        +familyMembers*RationShop.getWheatQuanity()*RationShop.getWheatPrice()*aplOrBplFactor
                        +"\n"
                        +"Kerosene:"+(familyMembers*RationShop.getKeroseneQuantity()*aplOrBplFactor)+"LTR:"
                        +familyMembers*RationShop.getKeroseneQuantity()*RationShop.getKerosenePrice()*aplOrBplFactor
                        +"\n"
                        +"Pulses:"+(familyMembers*RationShop.getPulsesQuantity()*aplOrBplFactor)+"KG:"
                        +familyMembers*RationShop.getPulsesQuantity()*RationShop.getPulsesPrice()*aplOrBplFactor
                        +"\n"
                        +"TOTAL PRICE:"
                        +totalPrice;


        BackgroundMail.newBuilder(this)
                .withUsername("smartrationcardvjc@gmail.com")
                .withPassword("agnirose")
                //TODO:REPLACE WITH CUSTOMER MAIL ID
                .withMailto("nihalismailk@gmail.com")
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("Invoice for Ration Shop Purchase")
                .withBody(emailContent)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {

                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {

                    }
                })
                .send();
    }


    private void sendSMS()
    {

        //EDIT CONTENT OF SMS HERE
        String smsContent=
                        "CUSTOMER:"
                        +LocalDB.getUserName()
                        +"\n\n\n"

                        +"Rice:"+(familyMembers*RationShop.getRiceQuantity()*aplOrBplFactor)+"KG:"
                        +familyMembers*RationShop.getRiceQuantity()*RationShop.getRicePrice()*aplOrBplFactor
                        +"\n"
                        +"Wheat:"+(familyMembers*RationShop.getWheatQuanity()*aplOrBplFactor)+"KG:"
                        +familyMembers*RationShop.getWheatQuanity()*RationShop.getWheatPrice()*aplOrBplFactor
                        +"\n"
                        +"Kerosene:"+(familyMembers*RationShop.getKeroseneQuantity()*aplOrBplFactor)+"LTR:"
                        +familyMembers*RationShop.getKeroseneQuantity()*RationShop.getKerosenePrice()*aplOrBplFactor
                        +"\n"
                        +"Pulses:"+(familyMembers*RationShop.getPulsesQuantity()*aplOrBplFactor)+"KG:"
                        +familyMembers*RationShop.getPulsesQuantity()*RationShop.getPulsesPrice()*aplOrBplFactor
                        +"\n"
                        +"TOTAL PRICE:"
                        +totalPrice;
        try {
            // Construct data
            String data = "";
            data += "username=" + URLEncoder.encode("nihal369", "ISO-8859-1");
            data += "&password=" + URLEncoder.encode("agnirose", "ISO-8859-1");
            data += "&message=" + URLEncoder.encode(smsContent, "ISO-8859-1");
            data += "&want_report=1";
            //TODO:REPLACE WITH CUSTOMER NUMBER
            data += "&msisdn=07356647169";// relace with the number

            // Send data
            URL url = new URL("http://bulksms.vsms.net:5567/eapi/submission/send_sms/2/2.0");

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                // Print the response output...
                System.out.println(line);
            }
            wr.close();
            rd.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
