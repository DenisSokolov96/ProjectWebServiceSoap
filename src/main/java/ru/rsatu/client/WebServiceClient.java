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

import javax.swing.text.Document;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.soap.*;


public class WebServiceClient {

    private static Logger log = Logger.getLogger(WebServiceClient.class);

    public static void test(){
        try {
            String url = "http://localhost:9913/ProjectWebServiceSoap/service";//"http://www.holidaywebservice.com/HolidayService_v2/HolidayService2.asmx?op=GetHolidaysAvailable";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","text/soap+xml; charset=utf-8");
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<soap12:Envelope xmlns:xsi=\"https://www.standart-namespace.com/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> " +
                    " <soap12:Body> " +
                    " <ClassWebServiceImpl name=" + "name" +"> " +
                    " </ClassWebServiceImpl>" +
                    " </soap12:Body>" +
                    "</soap12:Envelope>";
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(xml);
            wr.flush();
            wr.close();
            String responseStatus = con.getResponseMessage();
            System.out.println("Response status: "+responseStatus);
            System.out.println("Error: " + con.getResponseCode());
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getErrorStream()));//con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("response:" + response.toString());
        } catch (Exception e) {
            System.out.println(e);
            log.error(e);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException {

        test();
        /*Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Maxim");
        customer.setAge(29);

        objectToXML(customer);


        String soapEndpointUrl = "http://localhost:9913/ProjectWebServiceSoap/service?xml";
        String soapAction = "http://localhost:9913/ProjectWebServiceSoap/service?wsdl";

        callSoapWebService(soapEndpointUrl, soapAction);*/
        //customer = xmlToObject(customer);
        //System.out.println("Id: " + customer.getId() + "\nName: " + customer.getName() +"\nAge: "+customer.getAge());
    }
    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "ClassWebServiceImplService";
        String myNamespaceURI = "http://localhost:9913/ProjectWebServiceSoap/service";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            /*
            Constructed SOAP Request Message:
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="http://www.webserviceX.NET">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <myNamespace:GetInfoByCity>
                        <myNamespace:USCity>New York</myNamespace:USCity>
                    </myNamespace:GetInfoByCity>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("customer", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("name", myNamespace);
        soapBodyElem1.addTextNode("Maxim");
    }

    private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
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
/*
//String url = "http://www.webservicex.net";
        String url = "http://localhost:9913/ProjectWebServiceSoap/service";
        CloseableHttpClient client =  HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        post.setHeader("Host", "http://localhost:9913/ProjectWebServiceSoap/service");
        post.setHeader("Content-Type", "text/xml;charset=utf-8");
        post.setHeader("SOAPAction", "http://localhost:9913/ProjectWebServiceSoap/service");

        StringEntity xmlString =new StringEntity( "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" +
                "  <soap:Body>\r\n" +
                "    <GetGeoIP xmlns=\"http://localhost:9913/ProjectWebServiceSoap/service\">\r\n" +
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
                result.append(line);
                new File("/home/denis/IntelliJIDEAProjects/ProjectWebServiceSoap/src/main/resources/InfoToServer.xml");
            }
            System.out.println(result.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 */