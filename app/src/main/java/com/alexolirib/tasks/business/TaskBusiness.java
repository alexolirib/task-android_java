package com.alexolirib.tasks.business;

import android.content.Context;

import com.alexolirib.tasks.business.interfaces.ITaskBusiness;
import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.entities.APIResponse;
import com.alexolirib.tasks.entities.FullParameters;
import com.alexolirib.tasks.entities.PriorityEntity;
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
    private Context mContext;
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
            params.put(TaskConstants.API_PARAMETER.TASK.PRIORITYID, String.valueOf(task.getPriorityId()));
            params.put(TaskConstants.API_PARAMETER.TASK.DESCRIPTION, task.getDescription());
            params.put(TaskConstants.API_PARAMETER.TASK.COMPLETE, FormatUrlParameters.formatBoolean(task.getComplete()));
            params.put(TaskConstants.API_PARAMETER.TASK.DUEDATE, FormatUrlParameters.formatDate(task.getDueDate()));

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
    public OperationResult<List<TaskEntity>> getList() {
        OperationResult<List<TaskEntity>> result = new OperationResult<>();

        try{

            URLBuilder urlBuilder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            urlBuilder.addResource(TaskConstants.ENDPOINT.TASK_GET_LIST_NO_FILTER);

            AbstractMap<String, String> headerParams = super.getHeaderParams();

            FullParameters full = new FullParameters(
                    TaskConstants.OPERATION_METHOD.GET,
                    urlBuilder.getUri(), null, (HashMap)headerParams);

            APIResponse response = this.mExternalRepository.execute(full);

            if(response.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS){

                Type collection = new TypeToken<List<PriorityEntity>>(){}.getType();
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

}
