
package com.sngs.swayam.business.network.model.TranscationDetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TranscationDetailBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;
    @SerializedName("transactionListResult")
    private List<TransactionListResult> mTransactionListResult;

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

    public List<TransactionListResult> getTransactionListResult() {
        return mTransactionListResult;
    }

    public void setTransactionListResult(List<TransactionListResult> transactionListResult) {
        mTransactionListResult = transactionListResult;
    }

}
