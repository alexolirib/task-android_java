package com.alexolirib.tasks.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.alexolirib.tasks.business.TaskBusiness;
import com.alexolirib.tasks.entities.TaskEntity;
import com.alexolirib.tasks.infra.operation.OperationListener;
import com.alexolirib.tasks.infra.operation.OperationResult;

import java.util.List;

public class TaskManager {

    private TaskBusiness mTaskBusiness;

    public TaskManager(Context context){
        mTaskBusiness = new TaskBusiness(context);
    }

    public void insert(final TaskEntity entity, final OperationListener listener){
        AsyncTask<Void, Void, OperationResult<Boolean>> task = new AsyncTask<Void, Void, OperationResult<Boolean>>() {
            @Override
            protected OperationResult<Boolean> doInBackground(Void... voids) {
                return mTaskBusiness.insert(entity);
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

    public void getList(final int filter, final OperationListener listener) {
        AsyncTask<Void, Void, OperationResult<List<TaskEntity>>> task = new AsyncTask<Void, Void, OperationResult<List<TaskEntity>>>() {
            @Override
            protected OperationResult<List<TaskEntity>> doInBackground(Void... voids) {
                return mTaskBusiness.getList(filter);
            }

            @Override
            protected void onPostExecute(OperationResult<List<TaskEntity>> result) {
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

    public void get(final int taskId, final OperationListener<TaskEntity> listener) {
        AsyncTask<Void, Integer, OperationResult<TaskEntity>> task = new AsyncTask<Void, Integer, OperationResult<TaskEntity>>() {
            @Override
            protected OperationResult<TaskEntity> doInBackground(Void... voids) {
                return mTaskBusiness.get(taskId);
            }

            @Override
            protected void onPostExecute(OperationResult<TaskEntity> result) {
                int error = result.getError();
                if (error != OperationResult.NO_ERROR) {
                    listener.onError(error, result.getErrorMessage());
                } else {
                    listener.onSuccess(result.getResult());
                }
            }
        };

        // Executa async
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void update(final TaskEntity taskEntity, final OperationListener<Boolean> listener) {
        AsyncTask<Void, Integer, OperationResult<Boolean>> task = new AsyncTask<Void, Integer, OperationResult<Boolean>>() {
            @Override
            protected OperationResult<Boolean> doInBackground(Void... voids) {
                return mTaskBusiness.update(taskEntity);
            }

            @Override
            protected void onPostExecute(OperationResult<Boolean> result) {
                int error = result.getError();
                if (error != OperationResult.NO_ERROR) {
                    listener.onError(error, result.getErrorMessage());
                } else {
                    listener.onSuccess(result.getResult());
                }
            }
        };

        // Executa async
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void complete(final int id, final Boolean complete, final OperationListener<Boolean> listener) {
        AsyncTask<Void, Integer, OperationResult<Boolean>> task = new AsyncTask<Void, Integer, OperationResult<Boolean>>() {
            @Override
            protected OperationResult<Boolean> doInBackground(Void... voids) {
                return mTaskBusiness.complete(id, complete);
            }

            @Override
            protected void onPostExecute(OperationResult<Boolean> result) {
                int error = result.getError();
                if (error != OperationResult.NO_ERROR) {
                    listener.onError(error, result.getErrorMessage());
                } else {
                    listener.onSuccess(result.getResult());
                }
            }
        };

        // Executa async
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void delete(final int taskId, final OperationListener<Boolean> listener) {
        AsyncTask<Void, Integer, OperationResult<Boolean>> task = new AsyncTask<Void, Integer, OperationResult<Boolean>>() {
            @Override
            protected OperationResult<Boolean> doInBackground(Void... voids) {
                return mTaskBusiness.delete(taskId);
            }

            @Override
            protected void onPostExecute(OperationResult<Boolean> result) {
                int error = result.getError();
                if (error != OperationResult.NO_ERROR) {
                    listener.onError(error, result.getErrorMessage());
                } else {
                    listener.onSuccess(result.getResult());
                }
            }
        };

        // Executa async
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
