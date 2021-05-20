
package com.sngs.swayam.business.network.model.Packages;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PackageList {

    @SerializedName("packageDetail")
    private List<PackageDetail> mPackageDetail;
    @SerializedName("packageId")
    private String mPackageId;
    @SerializedName("packageName")
    private String mPackageName;

    private String pack_detail_pos;
    private String selected_pack_pos;


    public List<PackageDetail> getPackageDetail() {
        return mPackageDetail;
    }

    public void setPackageDetail(List<PackageDetail> packageDetail) {
        mPackageDetail = packageDetail;
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

    public String getPack_detail_pos() {
        return pack_detail_pos;
    }

    public void setPack_detail_pos(String pack_detail_pos) {
        this.pack_detail_pos = pack_detail_pos;
    }

    public String getSelected_pack_pos() {
        return selected_pack_pos;
    }

    public void setSelected_pack_pos(String selected_pack_pos) {
        this.selected_pack_pos = selected_pack_pos;
    }
}
