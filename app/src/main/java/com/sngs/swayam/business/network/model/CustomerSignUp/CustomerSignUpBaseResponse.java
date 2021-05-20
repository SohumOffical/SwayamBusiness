
package com.sngs.swayam.business.network.model.CustomerSignUp;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CustomerSignUpBaseResponse {

    @SerializedName("customerDetail")
    private CustomerDetail mCustomerDetail;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;

    public CustomerDetail getCustomerDetail() {
        return mCustomerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        mCustomerDetail = customerDetail;
    }

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

}
