
package com.sngs.swayam.business.network.model.Attribute;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetAttributeListBaseResponse {

    @SerializedName("attributeListData")
    private List<AttributeListDatum> mAttributeListData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;

    public List<AttributeListDatum> getAttributeListData() {
        return mAttributeListData;
    }

    public void setAttributeListData(List<AttributeListDatum> attributeListData) {
        mAttributeListData = attributeListData;
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
