package ru.rsatu.ws;

import org.apache.log4j.Logger;
import javax.jws.WebService;

// endpointInterface - указывающим полное имя класса интерфейса веб-сервиса
@WebService(endpointInterface = "ru.rsatu.ws.HelloWebService")
            //portName = "HelloWebServiceImplPort")
            //wsdlLocation = "http://localhost:9999/denis?wsdl")//"WEB-INF/wsdl/HelloWebServiceImpl")
public class HelloWebServiceImpl implements HelloWebService {

    public static Logger log = Logger.getLogger(HelloWebServiceImpl.class);

    @Override
    public String getHelloString(String name) {

        return "Hello, " + name + "!";
    }
}