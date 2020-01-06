package ru.rsatu.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
//веб-сервис будет использоваться для вызова методов
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ClassWebService {


    //удаленный вызов
    @WebMethod
    public String getAllList();

    @WebMethod
    public void addElem(String str);

    @WebMethod
    public void clearList();

    @WebMethod
    public String getFirstEl();

    @WebMethod
    public String getLastEl();

    @WebMethod
    Peoples getPeoples();
}