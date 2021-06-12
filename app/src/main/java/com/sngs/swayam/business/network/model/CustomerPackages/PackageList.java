
package com.sngs.swayam.business.network.model.CustomerPackages;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PackageList {

    @SerializedName("monthlyPayment")
    private String mMonthlyPayment;
    @SerializedName("numberOfCustomer")
    private String mNumberOfCustomer;
    @SerializedName("packageId")
    private String mPackageId;
    @SerializedName("packageName")
    private String mPackageName;
    @SerializedName("packagePurchasedDate")
    private String mPackagePurchasedDate;

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

    public String getPackageId() {
        return mPackageId;
    }

    public void setPackageId(String packageId) {
        mPackageId = packageId;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getPackagePurchasedDate() {
        return mPackagePurchasedDate;
    }

    public void setPackagePurchasedDate(String packagePurchasedDate) {
        mPackagePurchasedDate = packagePurchasedDate;
    }

}
