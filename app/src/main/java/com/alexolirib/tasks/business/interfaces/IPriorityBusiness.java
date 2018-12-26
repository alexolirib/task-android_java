package com.alexolirib.tasks.business.interfaces;

import com.alexolirib.tasks.entities.PriorityEntity;
import com.alexolirib.tasks.infra.operation.OperationResult;

import java.util.List;

public interface IPriorityBusiness {
    OperationResult<Boolean> getList();

    List<PriorityEntity> getListLocal();
}
