package com.alexolirib.tasks.business;

import android.content.Context;

import com.alexolirib.tasks.business.interfaces.IPersonBusiness;
import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.entities.APIResponse;
import com.alexolirib.tasks.entities.FullParameters;
import com.alexolirib.tasks.entities.HeaderEntity;
import com.alexolirib.tasks.infra.SecurityPreferences;
import com.alexolirib.tasks.infra.URLBuilder;
import com.alexolirib.tasks.infra.operation.OperationResult;
import com.alexolirib.tasks.repository.api.ExternalRepository;
import com.alexolirib.tasks.utils.FormatUrlParameters;
import com.google.gson.Gson;

import java.util.AbstractMap;
import java.util.HashMap;

public class PersonBusiness extends  BaseBusiness implements IPersonBusiness {

    private ExternalRepository mExternalRepository;
    private Context mContext;

    public PersonBusiness(Context context) {
        super(context);
        this.mExternalRepository = new ExternalRepository(context);
        this.mContext = context;
    }

    @Override
    public OperationResult<Boolean> create(String name, String email, String password) {

        OperationResult<Boolean> result = new OperationResult<>();
        try {

            URLBuilder builder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            builder.addResource(TaskConstants.ENDPOINT.AUTHENTICATION_CREATE);

            AbstractMap<String, String> params = new HashMap<>();
            params.put(TaskConstants.API_PARAMETER.PERSON.NAME, name);
            params.put(TaskConstants.API_PARAMETER.PERSON.EMAIL, email);
            params.put(TaskConstants.API_PARAMETER.PERSON.PASSWORD, password);
            params.put(TaskConstants.API_PARAMETER.PERSON.RECEIVE_NEWS, FormatUrlParameters.formatBoolean(false));

            FullParameters full = new FullParameters(TaskConstants.OPERATION_METHOD.POST,
                    builder.getUri(),
                    null,
                    (HashMap)params);

            APIResponse response = this.mExternalRepository.execute(full);
            //converter o json
            if(response.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS){
                HeaderEntity entity = new Gson().fromJson(response.getJson(), HeaderEntity.class);

                SecurityPreferences preferences = new SecurityPreferences(mContext);
                preferences.storeString(TaskConstants.HEADER.PERSON_KEY, entity.getPersonKey());
                preferences.storeString(TaskConstants.HEADER.TOKEN_KEY, entity.getToken());
                preferences.storeString(TaskConstants.USER.NAME, entity.getName());
                preferences.storeString(TaskConstants.USER.EMAIL, email);

                result.setResult(true);

            }else{
                result.setError(super.handleStatusCode(response.getStatusCode()), super.handleErrorMessage(response.getJson()));
            }

        }catch (Exception e){
            //para não ter que ficar repetindo código
            result.setError(super.handleExceptionCode(e), super.handleExceptionMessage(e));
        }
        return result;
    }
    @Override
    public OperationResult<Boolean> login(String email, String pass) {
        OperationResult<Boolean> result = new OperationResult<>();

        try{

            URLBuilder urlBuilder = new URLBuilder(TaskConstants.ENDPOINT.ROOT);
            urlBuilder.addResource(TaskConstants.ENDPOINT.AUTHENTICATION_LOGIN);

            AbstractMap<String, String> params = new HashMap<>();
            params.put(TaskConstants.API_PARAMETER.PERSON.EMAIL, email);
            params.put(TaskConstants.API_PARAMETER.PERSON.PASSWORD, pass);

            FullParameters full = new FullParameters(
                    TaskConstants.OPERATION_METHOD.POST,
                    urlBuilder.getUri(), null, (HashMap)params);

            APIResponse response = this.mExternalRepository.execute(full);

            if(response.getStatusCode() == TaskConstants.STATUS_CODE.SUCCESS){
                HeaderEntity entity = new Gson().fromJson(response.getJson(), HeaderEntity.class);

                SecurityPreferences preferences = new SecurityPreferences(mContext);
                preferences.storeString(TaskConstants.HEADER.PERSON_KEY, entity.getPersonKey());
                preferences.storeString(TaskConstants.HEADER.TOKEN_KEY, entity.getToken());
                preferences.storeString(TaskConstants.USER.NAME, entity.getName());
                preferences.storeString(TaskConstants.USER.EMAIL, email);

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
    public void logout() {

        SecurityPreferences preferences = new SecurityPreferences(mContext);
        preferences.removeStoredString(TaskConstants.HEADER.TOKEN_KEY);
        preferences.removeStoredString(TaskConstants.HEADER.PERSON_KEY);
        preferences.removeStoredString(TaskConstants.USER.EMAIL);
        preferences.removeStoredString(TaskConstants.USER.NAME);
    }
}
