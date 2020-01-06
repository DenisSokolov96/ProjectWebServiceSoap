package ru.rsatu.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.omg.CORBA.NameValuePair;
import ru.rsatu.ws.ClassWebService;
import ru.rsatu.ws.ClassWebService;
import ru.rsatu.ws.ClassWebServiceImpl;
import ru.rsatu.ws.Peoples;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class WebServiceClient {

    private static Logger log = Logger.getLogger(WebServiceClient.class);

    public static void main(String[] args) throws UnsupportedEncodingException {

        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Maxim");
        customer.setAge(29);

        objectToXML(customer);

        //sendXML(customer);

        //customer = xmlToObject(customer);
        //System.out.println("Id: " + customer.getId() + "\nName: " + customer.getName() +"\nAge: "+customer.getAge());
    }


    protected static void sendXML(Customer customer) throws UnsupportedEncodingException {

        String url = "http://www.webservicex.net";
        //String url = "http://localhost:9913/ProjectWebServiceSoap/service";
        CloseableHttpClient client =  HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        post.setHeader("Host", "www.webservicex.net");
        post.setHeader("Content-Type", "text/xml;charset=utf-8");
        post.setHeader("SOAPAction", "http://www.webservicex.net/GetGeoIP");

        StringEntity xmlString =new StringEntity( "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" +
                "  <soap:Body>\r\n" +
                "    <GetGeoIP xmlns=\"http://www.webservicex.net/\">\r\n" +
                "      <IPAddress>50.207.31.216</IPAddress>\r\n" +
                "    </GetGeoIP>\r\n" +
                "  </soap:Body>\r\n" +
                "</soap:Envelope>");

        post.setEntity(xmlString);

        HttpResponse response;
        try {
            response = client.execute(post);
            System.out.println("Response Code : " +
                    response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);new File("/home/denis/IntelliJIDEAProjects/ProjectWebServiceSoap/src/main/resources/InfoToServer.xml");
            }
            System.out.println(result.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void objectToXML(Customer customer){

        try {

            File file = new File("/home/denis/IntelliJIDEAProjects/ProjectWebServiceSoap/src/main/resources/InfoToServer.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(customer, file);


        } catch (JAXBException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }

    }

    private static Customer xmlToObject(Customer customer) {

        try {

            File file = new File("/home/denis/IntelliJIDEAProjects/ProjectWebServiceSoap/src/main/resources/InfoToClient.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            customer = (Customer) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return customer;
    }


}
/*
        String soapServiceUrl = "http://localhost:9913/ProjectWebServiceSoap/service";

        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(WebserviceSEI.class);
        factoryBean.setAddress(soapServiceUrl);

        FruitWebService webserviceSEI = (FruitWebService) factoryBean.create();

        Peoples result = webserviceSEI.getPeoples();
        System.out.println("Result: " + result);

 */

/*
        // создаем ссылку на wsdl описание
        URL url = new URL("http://localhost:9913/ProjectWebServiceSoap/service?wsdl");
        // Параметры следующего конструктора смотрим в самом первом теге WSDL описания - definitions
        // 1-ый аргумент смотрим в атрибуте targetNamespace
        // 2-ой аргумент смотрим в атрибуте name
        QName qname = new QName("http://ws.rsatu.ru/",
                                    "ClassWebServiceImplService");

        // Теперь мы можем дотянуться до тега service в wsdl описании
        Service service = Service.create(url, qname);

        // а далее и до вложенного в него тега port, чтобы
        // получить ссылку на удаленный от нас объект веб-сервиса
        ClassWebService classWS = service.getPort(ClassWebService.class);

        classWS.addElem("apple");
        classWS.addElem("banane");
        System.out.println(classWS.getAllList());
        classWS.clearList();
        classWS.addElem("orange");
        System.out.println(classWS.getAllList());
        System.out.println(classWS.getFirstEl());
        System.out.println(classWS.getLastEl());
 */