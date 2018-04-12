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
            wheatQuantity=1,
            keroseneQuantity=1,
            pulsesQuantity=1;

    //APL and BPL factor
    private static int aplFactor=1,bplFactor=2;

    public static int getRicePrice(){return ricePrice;}

    public static int getWheatPrice(){return wheatPrice;}

    public static int getKerosenePrice(){return kerosenePrice;}

    public static int getPulsesPrice(){return pulsesPrice;}

    public static int getRiceQuantity(){return riceQuantity;}

    public static int getWheatQuanity(){return wheatQuantity;}

    public static int getKeroseneQuantity(){return keroseneQuantity;}

    public static int getPulsesQuantity(){return pulsesQuantity;}

    public static int getAplFactor(){return aplFactor;}

    public static int getBplFactor(){return bplFactor;}

    public static void setKerosenePrice(int kerosenePrice) {RationShop.kerosenePrice = kerosenePrice;}

    public static void setAplFactor(int aplFactor) {
        RationShop.aplFactor = aplFactor;
    }

    public static void setBplFactor(int bplFactor) {
        RationShop.bplFactor = bplFactor;
    }

    public static void setPulsesPrice(int pulsesPrice) {
        RationShop.pulsesPrice = pulsesPrice;
    }

    public static void setKeroseneQuantity(int keroseneQuantity) {RationShop.keroseneQuantity = keroseneQuantity;}

    public static void setPulsesQuantity(int pulsesQuantity) {RationShop.pulsesQuantity = pulsesQuantity;}

    public static void setRicePrice(int ricePrice) {
        RationShop.ricePrice = ricePrice;
    }

    public static void setRiceQuantity(int riceQuantity) {
        RationShop.riceQuantity = riceQuantity;
    }

    public static void setWheatPrice(int wheatPrice) {
        RationShop.wheatPrice = wheatPrice;
    }

    public static void setWheatQuantity(int wheatQuantity) {RationShop.wheatQuantity = wheatQuantity;}

}
