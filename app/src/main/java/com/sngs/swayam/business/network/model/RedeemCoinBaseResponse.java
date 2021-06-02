
package com.sngs.swayam.business.network.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RedeemCoinBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;
    @SerializedName("promotionPurchaseDiscountId")
    private int mPromotionPurchaseDiscountId;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }


    public int getmPromotionPurchaseDiscountId() {
        return mPromotionPurchaseDiscountId;
    }

    public void setmPromotionPurchaseDiscountId(int mPromotionPurchaseDiscountId) {
        this.mPromotionPurchaseDiscountId = mPromotionPurchaseDiscountId;
    }
}
