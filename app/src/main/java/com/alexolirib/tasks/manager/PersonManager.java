package com.alexolirib.tasks.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.alexolirib.tasks.business.interfaces.IPersonBusiness;
import com.alexolirib.tasks.business.PersonBusiness;
import com.alexolirib.tasks.infra.operation.OperationListener;
import com.alexolirib.tasks.infra.operation.OperationResult;

public class PersonManager {
    IPersonBusiness mPersonBusiness;

    public PersonManager(Context context) {
        mPersonBusiness = new PersonBusiness(context);
    }

    public void create(final String name, final String email, final String password, final OperationListener listener){
        AsyncTask<Void, Void, OperationResult<Boolean>> task = new AsyncTask<Void, Void,  OperationResult<Boolean>>() {
            //primeiro é executado esse método em seguida executa o OnpostExecute
            @Override
            protected  OperationResult<Boolean> doInBackground(Void... voids) {
                //implementar a business
                return mPersonBusiness.create(name, email, password);
            }

            @Override
            protected void onPostExecute( OperationResult<Boolean> result) {
                //aqui irá dizer se é sucesso ou falha
                int error = result.getError();
                if(error != OperationResult.NO_ERROR){
                    listener.onError(error, result.getErrorMessage());
                }else {
                    listener.onSuccess(result.getResult());
                }
            }
        };
        //inicializa as threads
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void login(final String email, final String pass, final OperationListener<Boolean> listener) {
        AsyncTask<Void, Void, OperationResult<Boolean>> task = new AsyncTask<Void, Void, OperationResult<Boolean>>() {
            @Override
            protected OperationResult<Boolean> doInBackground(Void... voids) {
                return mPersonBusiness.login(email, pass);
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

    public void logout() {
        mPersonBusiness.logout();
    }
}
