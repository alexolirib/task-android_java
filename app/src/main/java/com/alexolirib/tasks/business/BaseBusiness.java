package com.alexolirib.tasks.business;

import android.content.Context;

import com.alexolirib.tasks.R;
import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.infra.InternetNotAvailableException;
import com.google.gson.Gson;

public class BaseBusiness {

    public Context mContext;

    public BaseBusiness(Context mContext) {
        this.mContext = mContext;
    }

    protected int handleExceptionCode(Exception e) {
        if (e instanceof InternetNotAvailableException) {
            return TaskConstants.STATUS_CODE.INTERNET_NOT_AVAILABLE;
        }
        return TaskConstants.STATUS_CODE.INTERNAL_SERVER_ERROR;
    }

    protected String handleExceptionMessage(Exception e) {
        if (e instanceof InternetNotAvailableException) {
            return this.mContext.getString(R.string.INTERNET_NOT_AVAILABLE);
        }
        return this.mContext.getString(R.string.UNEXPECTED_ERROR);

    }

    protected String handleErrorMessage(String json) {
        return new Gson().fromJson(json,String.class);
    }

    protected int handleStatusCode(Integer statusCode) {
        if(statusCode == TaskConstants.STATUS_CODE.FORBINDDEN){
            return TaskConstants.STATUS_CODE.FORBINDDEN;
        }else if(statusCode == TaskConstants.STATUS_CODE.NOT_FOUND){
            return TaskConstants.STATUS_CODE.NOT_FOUND;
        }else{
            return TaskConstants.STATUS_CODE.INTERNAL_SERVER_ERROR;
        }
    }
}
