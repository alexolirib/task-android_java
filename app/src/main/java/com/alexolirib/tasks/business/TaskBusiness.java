package com.alexolirib.tasks.business;

import android.content.Context;

import com.alexolirib.tasks.business.interfaces.ITaskBusiness;
import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.entities.APIResponse;
import com.alexolirib.tasks.entities.FullParameters;
import com.alexolirib.tasks.entities.TaskEntity;
import com.alexolirib.tasks.infra.URLBuilder;
import com.alexolirib.tasks.infra.operation.OperationResult;
import com.alexolirib.tasks.repository.api.ExternalRepository;
import com.alexolirib.tasks.utils.FormatUrlParameters;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

public class TaskBusiness extends BaseBusiness implements ITaskBusiness {
    private ExternalRepository mExternalRepository;

    public TaskBusiness(Context context) {
        super(context);
        this.mExternalRepository = new ExternalRepository(context);
        mContext = context;
    }

    @Override
    public OperationResult<Boolean> insert(TaskEntity task){
        OperationResult<Boolean> result = new OperationResult<>();

        try {
            URLBuilder builder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            builder.addResource(TaskConstants.ENDPOINT.TASK_INSERT);

            //obtendo o token
            AbstractMap<String, String> headerParams = super.getHeaderParams();

            AbstractMap<String, String> params = new HashMap<>();
            params.put(TaskConstants.API_PARAMETER.TASK.PRIORITY_ID, String.valueOf(task.getPriorityId()));
            params.put(TaskConstants.API_PARAMETER.TASK.DESCRIPTION, task.getDescription());
            params.put(TaskConstants.API_PARAMETER.TASK.COMPLETE, FormatUrlParameters.formatBoolean(task.getComplete()));
            params.put(TaskConstants.API_PARAMETER.TASK.DUE_DATE, FormatUrlParameters.formatDate(task.getDueDate()));

            FullParameters full = new FullParameters(TaskConstants.OPERATION_METHOD.POST, builder.getUri(),(HashMap) headerParams ,
                    (HashMap)params);

            APIResponse response = this.mExternalRepository.execute(full);
            if(response.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS){
                result.setResult(new Gson().fromJson(response.getJson(), Boolean.class));
            } else{
                result.setError(super.handleStatusCode(response.getStatusCode()),super.handleErrorMessage(response.getJson()));
            }

        } catch (Exception e){
            result.setError(super.handleExceptionCode(e),super.handleExceptionMessage(e));
        }
        return result;
    }

    @Override
    public OperationResult<List<TaskEntity>> getList(int filter) {
        OperationResult<List<TaskEntity>> result = new OperationResult<>();

        try{

            URLBuilder urlBuilder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            if(filter == TaskConstants.TASK_FILTER.NO_FILTER){
                urlBuilder.addResource(TaskConstants.ENDPOINT.TASK_GET_LIST_NO_FILTER);
            }else if(filter == TaskConstants.TASK_FILTER.OVERDUE){
                urlBuilder.addResource(TaskConstants.ENDPOINT.TASK_GET_LIST_overdue);
            } else if(filter == TaskConstants.TASK_FILTER.NEXT_7_DAYS){
                urlBuilder.addResource(TaskConstants.ENDPOINT.TASK_GET_LIST_NEXT_7_DAYS);
            }

            AbstractMap<String, String> headerParams = super.getHeaderParams();

            FullParameters full = new FullParameters(
                    TaskConstants.OPERATION_METHOD.GET,
                    urlBuilder.getUri(), (HashMap)headerParams, null);

            APIResponse response = this.mExternalRepository.execute(full);

            if(response.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS){

                Type collection = new TypeToken<List<TaskEntity>>(){}.getType();
                List<TaskEntity> list = new Gson().fromJson(response.getJson(),collection);

                result.setResult(list);
            } else{
                result.setError(super.handleStatusCode(response.getStatusCode()), super.handleErrorMessage(response.getJson()));
            }

        }catch (Exception e){
            result.setError(super.handleExceptionCode(e), super.handleExceptionMessage(e));
        }

        return result;
    }

    @Override
    public OperationResult<TaskEntity> get(int taskId) {

        // Retorno
        OperationResult<TaskEntity> operationResult = new OperationResult<>();

        try {

            // Monta query
            URLBuilder urlBuilder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            urlBuilder.addResource(TaskConstants.ENDPOINT.TASK_GET_SPECIFIC);
            urlBuilder.addResource(String.valueOf(taskId));

            // Adiciona cabeçalho
            AbstractMap<String, String> headerParameters = super.getHeaderParams();

            // Cria objeto de requisição com parâmetros
            FullParameters fullParameters = new FullParameters(TaskConstants.OPERATION_METHOD.GET, urlBuilder.getUri(), null, (HashMap) headerParameters);

            // Executa
            APIResponse httpResponse = this.mExternalRepository.execute(fullParameters);

            // Sucesso
            if (httpResponse.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS) {

                // Faz a conversão do json
                TaskEntity taskEntity = new Gson().fromJson(httpResponse.getJson(), TaskEntity.class);

                // Sucesso
                operationResult.setResult(taskEntity);

            } else {
                operationResult.setError(super.handleStatusCode(httpResponse.getStatusCode()), super.handleErrorMessage(httpResponse.getJson()));
            }
        } catch (Exception e) {
            operationResult.setError(super.handleExceptionCode(e), super.handleExceptionMessage(e));
        }

        return operationResult;
    }

    @Override
    public OperationResult<Boolean> update(TaskEntity taskEntity) {
        // Retorno
        OperationResult<Boolean> operationResult = new OperationResult<>();

        try {

            // Monta query
            URLBuilder urlBuilder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            urlBuilder.addResource(TaskConstants.ENDPOINT.TASK_UPDATE);

            AbstractMap<String, String> parameters = new HashMap<>();
            parameters.put(TaskConstants.API_PARAMETER.TASK.ID, String.valueOf(taskEntity.getId()));
            parameters.put(TaskConstants.API_PARAMETER.TASK.DESCRIPTION, taskEntity.getDescription());
            parameters.put(TaskConstants.API_PARAMETER.TASK.PRIORITY_ID, String.valueOf(taskEntity.getPriorityId()));
            parameters.put(TaskConstants.API_PARAMETER.TASK.DUE_DATE, FormatUrlParameters.formatDate(taskEntity.getDueDate()));
            parameters.put(TaskConstants.API_PARAMETER.TASK.COMPLETE, FormatUrlParameters.formatBoolean(taskEntity.getComplete()));

            // Adiciona cabeçalho
            AbstractMap<String, String> headerParameters = super.getHeaderParams();

            // Cria objeto de requisição com parâmetros
            FullParameters fullParameters = new FullParameters(TaskConstants.OPERATION_METHOD.PUT, urlBuilder.getUri(), (HashMap) parameters, (HashMap) headerParameters);

            // Executa
            APIResponse httpResponse = this.mExternalRepository.execute(fullParameters);

            // Sucesso
            if (httpResponse.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS) {
                operationResult.setResult(new Gson().fromJson(httpResponse.getJson(), Boolean.class));
            } else {
                operationResult.setError(super.handleStatusCode(httpResponse.getStatusCode()), super.handleErrorMessage(httpResponse.getJson()));
            }
        } catch (Exception e) {
            operationResult.setError(super.handleExceptionCode(e), super.handleExceptionMessage(e));
        }

        return operationResult;
    }

    @Override
    public OperationResult<Boolean> complete(int id, Boolean complete) {
        // Retorno
        OperationResult<Boolean> operationResult = new OperationResult<>();

        try {

            // Monta query
            URLBuilder urlBuilder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);

            if (complete) {
                urlBuilder.addResource(TaskConstants.ENDPOINT.TASK_COMPLETE);
            } else {
                urlBuilder.addResource(TaskConstants.ENDPOINT.TASK_UNCOMPLETE);
            }

            AbstractMap<String, String> parameters = new HashMap<>();
            parameters.put(TaskConstants.API_PARAMETER.TASK.ID, String.valueOf(id));

            // Adiciona cabeçalho
            AbstractMap<String, String> headerParameters = super.getHeaderParams();

            // Cria objeto de requisição com parâmetros
            FullParameters fullParameters = new FullParameters(TaskConstants.OPERATION_METHOD.PUT, urlBuilder.getUri(), (HashMap) parameters, (HashMap) headerParameters);

            // Executa
            APIResponse httpResponse = this.mExternalRepository.execute(fullParameters);

            // Sucesso
            if (httpResponse.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS) {
                operationResult.setResult(new Gson().fromJson(httpResponse.getJson(), Boolean.class));
            } else {
                operationResult.setError(super.handleStatusCode(httpResponse.getStatusCode()), super.handleErrorMessage(httpResponse.getJson()));
            }
        } catch (Exception e) {
            operationResult.setError(super.handleExceptionCode(e), super.handleExceptionMessage(e));
        }

        return operationResult;
    }

    @Override
    public OperationResult<Boolean> delete(int taskId) {
        // Retorno
        OperationResult<Boolean> operationResult = new OperationResult<>();

        try {

            // Monta query
            URLBuilder urlBuilder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            urlBuilder.addResource(TaskConstants.ENDPOINT.TASK_DELETE);

            AbstractMap<String, String> parameters = new HashMap<>();
            parameters.put(TaskConstants.API_PARAMETER.TASK.ID, String.valueOf(taskId));

            // Adiciona cabeçalho
            AbstractMap<String, String> headerParameters = super.getHeaderParams();

            // Cria objeto de requisição com parâmetros
            FullParameters fullParameters = new FullParameters(TaskConstants.OPERATION_METHOD.DELETE, urlBuilder.getUri(), (HashMap) parameters, (HashMap) headerParameters);

            // Executa
            APIResponse httpResponse = this.mExternalRepository.execute(fullParameters);

            // Sucesso
            if (httpResponse.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS) {
                operationResult.setResult(new Gson().fromJson(httpResponse.getJson(), Boolean.class));
            } else {
                operationResult.setError(super.handleStatusCode(httpResponse.getStatusCode()), super.handleErrorMessage(httpResponse.getJson()));
            }
        } catch (Exception e) {
            operationResult.setError(super.handleExceptionCode(e), super.handleExceptionMessage(e));
        }

        return operationResult;
    }



}
