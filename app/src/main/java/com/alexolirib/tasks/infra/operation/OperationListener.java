package com.alexolirib.tasks.infra.operation;

//<T> pode ter qual quer tipo de dado (parametro anonimo)
public class OperationListener<T> {

    public void onSuccess(T result){

    }

    public void onError(int errorCode, String errorMessage){

    }
}
