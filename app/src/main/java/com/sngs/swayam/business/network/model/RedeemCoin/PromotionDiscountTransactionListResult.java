
package com.sngs.swayam.business.network.model.RedeemCoin;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class PromotionDiscountTransactionListResult {

    @SerializedName("itemName")
    private String mItemName;
    @SerializedName("itemPurchasePrice")
    private String mItemPurchasePrice;
    @SerializedName("itemSellingPrice")
    private String mItemSellingPrice;
    @SerializedName("paymentMethod")
    private String mPaymentMethod;
    @SerializedName("promotionDiscount")
    private String mPromotionDiscount;
    @SerializedName("promotionPurchaseId")
    private String mPromotionPurchaseId;
    @SerializedName("userContactNumber")
    private String mUserContactNumber;
    @SerializedName("userName")
    private String muserName;


    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getItemPurchasePrice() {
        return mItemPurchasePrice;
    }

    public void setItemPurchasePrice(String itemPurchasePrice) {
        mItemPurchasePrice = itemPurchasePrice;
    }

    public String getItemSellingPrice() {
        return mItemSellingPrice;
    }

    public void setItemSellingPrice(String itemSellingPrice) {
        mItemSellingPrice = itemSellingPrice;
    }

    public String getPaymentMethod() {
        return mPaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        mPaymentMethod = paymentMethod;
    }

    public String getPromotionDiscount() {
        return mPromotionDiscount;
    }

    public void setPromotionDiscount(String promotionDiscount) {
        mPromotionDiscount = promotionDiscount;
    }

    public String getPromotionPurchaseId() {
        return mPromotionPurchaseId;
    }

    public void setPromotionPurchaseId(String promotionPurchaseId) {
        mPromotionPurchaseId = promotionPurchaseId;
    }

    public String getUserContactNumber() {
        return mUserContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        mUserContactNumber = userContactNumber;
    }

    public String getMuserName() {
        return muserName;
    }

    public void setMuserName(String muserName) {
        this.muserName = muserName;
    }
}
