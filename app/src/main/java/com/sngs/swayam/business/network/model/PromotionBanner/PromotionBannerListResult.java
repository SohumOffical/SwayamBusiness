
package com.sngs.swayam.business.network.model.PromotionBanner;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PromotionBannerListResult {

    @SerializedName("promotionBanner")
    private String mPromotionBanner;
    @SerializedName("promotionBannerId")
    private String mPromotionBannerId;

    private boolean is_checked;

    public boolean isIs_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public String getPromotionBanner() {
        return mPromotionBanner;
    }

    public void setPromotionBanner(String promotionBanner) {
        mPromotionBanner = promotionBanner;
    }

    public String getPromotionBannerId() {
        return mPromotionBannerId;
    }

    public void setPromotionBannerId(String promotionBannerId) {
        mPromotionBannerId = promotionBannerId;
    }

}
