package ru.rsatu.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
//веб-сервис будет использоваться для вызова методов
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface HelloWebService {

    //этот метод можно вызывать удаленно
    @WebMethod
    public String getHelloString(String name);
}