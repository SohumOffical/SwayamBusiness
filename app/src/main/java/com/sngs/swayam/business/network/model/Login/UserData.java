
package com.sngs.swayam.business.network.model.Login;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserData {

    @SerializedName("authId")
    private String mAuthId;
    @SerializedName("authToken")
    private String mAuthToken;
    @SerializedName("contactNumber")
    private String mCustomerContactNumber;
    @SerializedName("name")
    private String mCustomerName;
    @SerializedName("otp")
    private Long mOtp;
    @SerializedName("stepCompleted")
    private String mStepCompleted;
    @SerializedName("userType")
    private Long mUserType;

    public String getAuthId() {
        return mAuthId;
    }

    public void setAuthId(String authId) {
        mAuthId = authId;
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        mAuthToken = authToken;
    }

    public String getCustomerContactNumber() {
        return mCustomerContactNumber;
    }

    public void setCustomerContactNumber(String customerContactNumber) {
        mCustomerContactNumber = customerContactNumber;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public Long getOtp() {
        return mOtp;
    }

    public void setOtp(Long otp) {
        mOtp = otp;
    }

    public String getStepCompleted() {
        return mStepCompleted;
    }

    public void setStepCompleted(String stepCompleted) {
        mStepCompleted = stepCompleted;
    }

    public Long getUserType() {
        return mUserType;
    }

    public void setUserType(Long userType) {
        mUserType = userType;
    }

}
