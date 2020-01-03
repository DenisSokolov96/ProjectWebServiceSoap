package ru.rsatu.client;

// нужно, чтобы получить wsdl описание и через него
// дотянуться до самого веб-сервиса
import com.sun.corba.se.spi.activation.Server;
import org.apache.log4j.Logger;
import ru.rsatu.ws.HelloWebService;
import ru.rsatu.ws.HelloWebServiceImpl;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;

public class HelloWebServiceClient {

    private static Logger log = Logger.getLogger(HelloWebServiceClient.class);

    public static void main(String[] args) throws MalformedURLException {

        log.info("Start Client...");

        // создаем ссылку на wsdl описание
        URL url = new URL("http://localhost:9913/ProjectWebServiceSoap/service?wsdl");
        // Параметры следующего конструктора смотрим в самом первом теге WSDL описания - definitions
        // 1-ый аргумент смотрим в атрибуте targetNamespace
        // 2-ой аргумент смотрим в атрибуте name
        QName qname = new QName("http://ws.rsatu.ru/",
                                    "HelloWebServiceImplService");

        // Теперь мы можем дотянуться до тега service в wsdl описании
        Service service = Service.create(url, qname);

        // а далее и до вложенного в него тега port, чтобы
        // получить ссылку на удаленный от нас объект веб-сервиса
        HelloWebService hello = service.getPort(HelloWebServiceImpl.class);

        System.out.println(hello.getHelloString("Денис. Работает"));
        log.info("...Stop Client.");
    }

}

/*     //javarush
       // создаем ссылку на wsdl описание
        URL url = new URL("http://localhost:9999/vs/denis?wsdl");

        // Параметры следующего конструктора смотрим в самом первом теге WSDL описания - definitions
        // 1-ый аргумент смотрим в атрибуте targetNamespace
        // 2-ой аргумент смотрим в атрибуте name
        QName qname = new QName("http://localhost:9913/SoapProject_war_exploded/service",
                                    "HelloWebServiceImplService");

        // Теперь мы можем дотянуться до тега service в wsdl описании,
        Service service = Service.create(url, qname);
        // а далее и до вложенного в него тега port, чтобы
        // получить ссылку на удаленный от нас объект веб-сервиса
        HelloWebService hello = service.getPort(HelloWebServiceImpl.class);

        // Ура! Теперь можно вызывать удаленный метод
        System.out.println(hello.getHelloString("Денис. Работает"));
 */