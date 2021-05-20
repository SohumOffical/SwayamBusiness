
package com.sngs.swayam.business.network.model.SubCategory;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SubCategoryListDatum {

    @SerializedName("categoryId")
    private String mCategoryId;
    @SerializedName("categoryName")
    private String mCategoryName;
    @SerializedName("serviceId")
    private String mServiceId;
    @SerializedName("serviceName")
    private String mServiceName;
    @SerializedName("subCategoryId")
    private String mSubCategoryId;
    @SerializedName("subCategoryName")
    private String mSubCategoryName;

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

    public String getServiceId() {
        return mServiceId;
    }

    public void setServiceId(String serviceId) {
        mServiceId = serviceId;
    }

    public String getServiceName() {
        return mServiceName;
    }

    public void setServiceName(String serviceName) {
        mServiceName = serviceName;
    }

    public String getSubCategoryId() {
        return mSubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        mSubCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return mSubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        mSubCategoryName = subCategoryName;
    }

}
