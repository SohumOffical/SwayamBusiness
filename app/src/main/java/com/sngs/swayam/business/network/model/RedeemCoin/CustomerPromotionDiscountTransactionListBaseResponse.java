
package com.sngs.swayam.business.network.model.RedeemCoin;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class CustomerPromotionDiscountTransactionListBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("promotionDiscountTransactionListResult")
    private List<PromotionDiscountTransactionListResult> mPromotionDiscountTransactionListResult;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<PromotionDiscountTransactionListResult> getPromotionDiscountTransactionListResult() {
        return mPromotionDiscountTransactionListResult;
    }

    public void setPromotionDiscountTransactionListResult(List<PromotionDiscountTransactionListResult> promotionDiscountTransactionListResult) {
        mPromotionDiscountTransactionListResult = promotionDiscountTransactionListResult;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
