package com.alexolirib.tasks.business.interfaces;

import com.alexolirib.tasks.business.BaseBusiness;
import com.alexolirib.tasks.entities.TaskEntity;
import com.alexolirib.tasks.infra.operation.OperationResult;

import java.util.List;

public interface ITaskBusiness {
    OperationResult<Boolean> insert(TaskEntity task);
    OperationResult<List<TaskEntity>> getList(int filter);
    OperationResult<TaskEntity> get(int id);
    OperationResult<Boolean> update(TaskEntity taskEntity);
    OperationResult<Boolean> complete(int id, Boolean complete);
    OperationResult<Boolean> delete(int taskId);
}
