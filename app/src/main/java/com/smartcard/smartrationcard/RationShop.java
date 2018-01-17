package com.smartcard.smartrationcard;

public class RationShop {

    //Edit commodity prices here
    private static int
            //Price of quantities per KG or LTR
            ricePrice=2,
            wheatPrice=4,
            kerosenePrice=10,
            pulsesPrice=6,

            //Amount to be distributed,Actual amount=amount x APLorBPL factor
            riceQuantity=2,
            wheatQuanity=1,
            keroseneQuantity=1,
            pulsesQuantity=1;

    //APL and BPL factor
    private static int aplFactor=1,bplFactor=2;

    public static int getRicePrice(){return ricePrice;}

    public static int getWheatPrice(){return wheatPrice;}

    public static int getKerosenePrice(){return kerosenePrice;}

    public static int getPulsesPrice(){return pulsesPrice;}

    public static int getRiceQuantity(){return riceQuantity;}

    public static int getWheatQuanity(){return wheatQuanity;}

    public static int getKeroseneQuantity(){return keroseneQuantity;}

    public static int getPulsesQuantity(){return pulsesQuantity;}

    public static int getAplFactor(){return aplFactor;}

    public static int getBplFactor(){return bplFactor;}
}
