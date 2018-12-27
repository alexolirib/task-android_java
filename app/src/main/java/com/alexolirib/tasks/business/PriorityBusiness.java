package com.alexolirib.tasks.business;

import android.content.Context;

import com.alexolirib.tasks.business.interfaces.IPriorityBusiness;
import com.alexolirib.tasks.constants.PriorityCacheConstants;
import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.entities.APIResponse;
import com.alexolirib.tasks.entities.FullParameters;
import com.alexolirib.tasks.entities.HeaderEntity;
import com.alexolirib.tasks.entities.PriorityEntity;
import com.alexolirib.tasks.infra.SecurityPreferences;
import com.alexolirib.tasks.infra.URLBuilder;
import com.alexolirib.tasks.infra.operation.OperationResult;
import com.alexolirib.tasks.repository.api.ExternalRepository;
import com.alexolirib.tasks.repository.local.PriorityRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

public class PriorityBusiness extends BaseBusiness implements IPriorityBusiness {

    private ExternalRepository mExternalRepository;
    private PriorityRepository mPriorityRepository;

    public PriorityBusiness(Context context) {
        super(context);
        mExternalRepository = new ExternalRepository(context);
        mPriorityRepository = PriorityRepository.getInstance(context);
    }

    @Override
    public OperationResult<Boolean> getList() {
        OperationResult<Boolean> result = new OperationResult<>();

        try{

            URLBuilder urlBuilder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            urlBuilder.addResource(TaskConstants.ENDPOINT.PRIORITY_GET);

            AbstractMap<String, String> headerParams = super.getHeaderParams();

            FullParameters full = new FullParameters(
                    TaskConstants.OPERATION_METHOD.GET,
                    urlBuilder.getUri(), null, (HashMap)headerParams);

            APIResponse response = this.mExternalRepository.execute(full);

            if(response.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS){

                Type collection = new TypeToken<List<PriorityEntity>>(){}.getType();
                List<PriorityEntity> list = new Gson().fromJson(response.getJson(),collection);

                //salvar no banco as prioridades
                this.mPriorityRepository.clearData();
                this.mPriorityRepository.insert(list);

                //salvo valores no cache
                PriorityCacheConstants.setValues(list);

                result.setResult(true);
            } else{
                result.setError(super.handleStatusCode(response.getStatusCode()), super.handleErrorMessage(response.getJson()));
            }

        }catch (Exception e){
            result.setError(super.handleExceptionCode(e), super.handleExceptionMessage(e));
        }

        return result;
    }

    @Override
    public List<PriorityEntity> getListLocal() {
        return this.mPriorityRepository.getList();
    }
}
