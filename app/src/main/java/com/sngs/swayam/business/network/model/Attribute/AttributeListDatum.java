
package com.sngs.swayam.business.network.model.Attribute;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AttributeListDatum {

    @SerializedName("attributeId")
    private String mAttributeId;
    @SerializedName("attributeName")
    private String mAttributeName;

    public String getAttributeId() {
        return mAttributeId;
    }

    public void setAttributeId(String attributeId) {
        mAttributeId = attributeId;
    }

    public String getAttributeName() {
        return mAttributeName;
    }

    public void setAttributeName(String attributeName) {
        mAttributeName = attributeName;
    }

}
