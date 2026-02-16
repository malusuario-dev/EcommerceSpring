package com.example.Ecomerce.common.application.mediator;

import com.example.Ecomerce.common.application.Request;
import com.example.Ecomerce.common.application.RequestHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class Mediator {

    Map< ? extends  Class <?> , RequestHandler<?,?>>requestHandlerMap;

    public  Mediator (List<RequestHandler<?,?>> requestHandlers){
        requestHandlerMap=    requestHandlers.stream().collect(Collectors.toMap(RequestHandler::getRequestType , Function.identity()));

    }
    public <R,T extends Request<R>>R dispatch (T request){
        RequestHandler<T,R> handler = (RequestHandler<T, R>) requestHandlerMap.get(request.getClass());
        if (handler ==null){
            throw  new RuntimeException("no Hanlder found for Request type"+request.getClass());

        }
        return handler.handle(request);
    }

    @Async
    public <R, T extends Request<R>> void dispatchAsinc(T request) {
        this.dispatch(request);
    }
}
