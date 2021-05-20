
package com.sngs.swayam.business.network.model.Packages;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PackageDetail {

    @SerializedName("factorValue")
    private String mFactorValue;
    @SerializedName("maximumPromotion")
    private String mMaximumPromotion;
    @SerializedName("minimumCustomer")
    private String mMinimumCustomer;
    @SerializedName("monthlyPayment")
    private String mMonthlyPayment;
    @SerializedName("numberOfCustomer")
    private String mNumberOfCustomer;
    @SerializedName("pricePerPromotion")
    private String mPricePerPromotion;
    @SerializedName("unitPrice")
    private String mUnitPrice;
    @SerializedName("packageDetailId")
    private String mPackageDetailId;
    @SerializedName("isPurchased")
    private String mIsPurchased;


    private  boolean is_selected;




    public String getFactorValue() {
        return mFactorValue;
    }

    public void setFactorValue(String factorValue) {
        mFactorValue = factorValue;
    }

    public String getMaximumPromotion() {
        return mMaximumPromotion;
    }

    public void setMaximumPromotion(String maximumPromotion) {
        mMaximumPromotion = maximumPromotion;
    }

    public String getMinimumCustomer() {
        return mMinimumCustomer;
    }

    public void setMinimumCustomer(String minimumCustomer) {
        mMinimumCustomer = minimumCustomer;
    }

    public String getMonthlyPayment() {
        return mMonthlyPayment;
    }

    public void setMonthlyPayment(String monthlyPayment) {
        mMonthlyPayment = monthlyPayment;
    }

    public String getNumberOfCustomer() {
        return mNumberOfCustomer;
    }

    public void setNumberOfCustomer(String numberOfCustomer) {
        mNumberOfCustomer = numberOfCustomer;
    }

    public String getPricePerPromotion() {
        return mPricePerPromotion;
    }

    public void setPricePerPromotion(String pricePerPromotion) {
        mPricePerPromotion = pricePerPromotion;
    }

    public String getUnitPrice() {
        return mUnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        mUnitPrice = unitPrice;
    }

    public String getmPackageDetailId() {
        return mPackageDetailId;
    }

    public void setmPackageDetailId(String mPackageDetailId) {
        this.mPackageDetailId = mPackageDetailId;
    }

    public String getmIsPurchased() {
        return mIsPurchased;
    }

    public void setmIsPurchased(String mIsPurchased) {
        this.mIsPurchased = mIsPurchased;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }
}
