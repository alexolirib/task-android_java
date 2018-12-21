package com.alexolirib.tasks.business.interfaces;

import com.alexolirib.tasks.infra.operation.OperationResult;

public interface IPriorityBusiness {
    OperationResult<Boolean> getList();
}
