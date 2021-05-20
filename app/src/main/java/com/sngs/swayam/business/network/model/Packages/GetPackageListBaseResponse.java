
package com.sngs.swayam.business.network.model.Packages;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GetPackageListBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("packageList")
    private List<PackageList> mPackageList;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<PackageList> getPackageList() {
        return mPackageList;
    }

    public void setPackageList(List<PackageList> packageList) {
        mPackageList = packageList;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
