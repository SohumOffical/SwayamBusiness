
package com.sngs.swayam.business.network.model.CustomerSignUp;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class CustomerDetail {

    @SerializedName("authId")
    private Long mAuthId;
    @SerializedName("authToken")
    private String mAuthToken;
    @SerializedName("customerContactNumber")
    private String mCustomerContactNumber;
    @SerializedName("customerName")
    private String mCustomerName;
    @SerializedName("userType")
    private String mUserType;

    public Long getAuthId() {
        return mAuthId;
    }

    public void setAuthId(Long authId) {
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

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}
