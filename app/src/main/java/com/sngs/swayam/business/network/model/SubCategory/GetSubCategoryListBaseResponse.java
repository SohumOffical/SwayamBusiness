
package com.sngs.swayam.business.network.model.SubCategory;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetSubCategoryListBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("subCategoryListData")
    private List<SubCategoryListDatum> mSubCategoryListData;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<SubCategoryListDatum> getSubCategoryListData() {
        return mSubCategoryListData;
    }

    public void setSubCategoryListData(List<SubCategoryListDatum> subCategoryListData) {
        mSubCategoryListData = subCategoryListData;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
