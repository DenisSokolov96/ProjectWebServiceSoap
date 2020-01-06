package ru.rsatu.ws;

import org.apache.log4j.Logger;
import ru.rsatu.client.WebServiceClient;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="FruitWebServiceImpl")
@XmlType(propOrder = {"allList","firstEl", "lastEl"})
// endpointInterface - указывающим полное имя класса интерфейса веб-сервиса
@WebService(endpointInterface = "ru.rsatu.ws.ClassWebService")
public class ClassWebServiceImpl implements ClassWebService {

    public List<String> listStr = new ArrayList<String>();
    //private List<String> allList;
    //private String firstEl;
    //private String lastEl;

    @Override
    public void addElem(String str){
        listStr.add(str);
    }

    @Override
    public void clearList(){
        listStr.clear();
    }

    @XmlElement(name="AllList")
    @Override
    public String getAllList() {
        return listStr.toString();
    }

    @XmlElement(name="FirstEl")
    @Override
    public String getFirstEl() {
        if (!listStr.isEmpty()) return listStr.get(0);
        else return "";
    }

    @XmlElement(name="LastEl")
    public String getLastEl() {
        if (!listStr.isEmpty()) return listStr.get(listStr.size()-1);
        else return "";
    }

    @Override
    public Peoples getPeoples() {
        Peoples peoples = new Peoples();
        peoples.setId(1);
        peoples.setName("Tom");
        return peoples;
    }
}