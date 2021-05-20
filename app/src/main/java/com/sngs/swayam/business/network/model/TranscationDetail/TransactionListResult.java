
package com.sngs.swayam.business.network.model.TranscationDetail;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TransactionListResult {

    @SerializedName("promotionPrice")
    private String mPromotionPrice;
    @SerializedName("promotionVisibility")
    private String mPromotionVisibility;
    @SerializedName("transactionAmount")
    private String mTransactionAmount;
    @SerializedName("transactionDate")
    private String mTransactionDate;
    @SerializedName("transactionId")
    private String mTransactionId;

    public String getPromotionPrice() {
        return mPromotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        mPromotionPrice = promotionPrice;
    }

    public String getPromotionVisibility() {
        return mPromotionVisibility;
    }

    public void setPromotionVisibility(String promotionVisibility) {
        mPromotionVisibility = promotionVisibility;
    }

    public String getTransactionAmount() {
        return mTransactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        mTransactionAmount = transactionAmount;
    }

    public String getTransactionDate() {
        return mTransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        mTransactionDate = transactionDate;
    }

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(String transactionId) {
        mTransactionId = transactionId;
    }

}
