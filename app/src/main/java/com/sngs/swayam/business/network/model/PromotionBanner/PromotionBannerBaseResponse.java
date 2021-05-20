
package com.sngs.swayam.business.network.model.PromotionBanner;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PromotionBannerBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("promotionBannerListResult")
    private List<PromotionBannerListResult> mPromotionBannerListResult;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<PromotionBannerListResult> getPromotionBannerListResult() {
        return mPromotionBannerListResult;
    }

    public void setPromotionBannerListResult(List<PromotionBannerListResult> promotionBannerListResult) {
        mPromotionBannerListResult = promotionBannerListResult;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
