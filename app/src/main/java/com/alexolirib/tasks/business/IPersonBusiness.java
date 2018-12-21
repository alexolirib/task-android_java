package com.alexolirib.tasks.business;

import com.alexolirib.tasks.infra.operation.OperationResult;

public interface IPersonBusiness {
    OperationResult<Boolean> create(String name, String email, String password);

    OperationResult<Boolean> login(String email, String pass);

    void logout();
}
