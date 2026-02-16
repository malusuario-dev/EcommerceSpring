package com.example.Ecomerce.common.application;


public interface RequestHandler <T extends Request<R>, R>{
    R handle(T request);
    Class<T> getRequestType();
}
