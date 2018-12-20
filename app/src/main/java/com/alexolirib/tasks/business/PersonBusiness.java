package com.alexolirib.tasks.business;

import android.content.Context;

import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.entities.APIResponse;
import com.alexolirib.tasks.entities.FullParameters;
import com.alexolirib.tasks.infra.URLBuilder;
import com.alexolirib.tasks.infra.operation.OperationResult;
import com.alexolirib.tasks.repository.api.ExternalRepository;
import com.alexolirib.tasks.utils.FormatUrlParameters;

import java.util.AbstractMap;
import java.util.HashMap;

public class PersonBusiness {

    private ExternalRepository mExternalRepository;

    public PersonBusiness(Context context) {
        this.mExternalRepository = new ExternalRepository(context);
    }

    public OperationResult<Boolean> create(String name, String email, String password) {

        OperationResult<Boolean> result = new OperationResult<>();
        try {

            URLBuilder builder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            builder.addResource(TaskConstants.ENDPOINT.AUTHENTICATION_CREATE);

            AbstractMap<String, String> params = new HashMap<>();
            params.put(TaskConstants.API_PARAMETER.NAME, name);
            params.put(TaskConstants.API_PARAMETER.EMAIL, email);
            params.put(TaskConstants.API_PARAMETER.PASSWORD, password);
            params.put(TaskConstants.API_PARAMETER.RECEIVE_NEWS, FormatUrlParameters.formatBoolean(false));

            FullParameters full = new FullParameters(TaskConstants.OPERATION_METHOD.POST,
                    builder.getUri(),
                    null,
                    (HashMap)params);

            APIResponse response = this.mExternalRepository.execute(full);
            String as = "";

        } catch (Exception e){
            return  result;
        }
        return result;
    }
}
