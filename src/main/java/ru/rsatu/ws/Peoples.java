package ru.rsatu.ws;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "peoples")
public class Peoples implements Serializable{

    private int id;
    private String name;

    public Peoples() {
    }

    public Peoples(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Peoples{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
