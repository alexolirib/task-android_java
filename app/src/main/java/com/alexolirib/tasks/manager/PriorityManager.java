package com.alexolirib.tasks.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.alexolirib.tasks.business.PriorityBusiness;
import com.alexolirib.tasks.business.interfaces.IPriorityBusiness;
import com.alexolirib.tasks.infra.operation.OperationListener;
import com.alexolirib.tasks.infra.operation.OperationResult;

public class PriorityManager {

    IPriorityBusiness mPriorityBusiness;

    public PriorityManager(Context context) {
        this.mPriorityBusiness = new PriorityBusiness(context);
    }

    public void getList(final OperationListener<Boolean> listener) {
        AsyncTask<Void, Void, OperationResult<Boolean>> task = new AsyncTask<Void, Void, OperationResult<Boolean>>() {
            @Override
            protected OperationResult<Boolean> doInBackground(Void... voids) {
                return mPriorityBusiness.getList();
            }

            @Override
            protected void onPostExecute(OperationResult<Boolean> result) {
                int error = result.getError();
                if(error != OperationResult.NO_ERROR){
                    listener.onError(error, result.getErrorMessage());
                } else {
                    listener.onSuccess(result.getResult());
                }
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
