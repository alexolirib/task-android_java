package com.alexolirib.tasks.infra.operation;

public class OperationResult<T> {

    public static final int NO_ERROR = -1;
    private  int mError = NO_ERROR;
    private String mErrorMessage = "";
    private T anonimousType;

    public int getError() {
        return mError;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setError(int mError,String mErrorMessage) {
        this.mError = mError;
        this.mErrorMessage = mErrorMessage;
    }

    public T getResult() {
        return anonimousType;
    }

    public void setResult(T anonimousType) {
        this.anonimousType = anonimousType;
    }
}
