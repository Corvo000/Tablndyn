package sample;

import javafx.beans.property.SimpleStringProperty;

import java.io.*;

public class Person implements Serializable
{
    private SimpleStringProperty fN = new SimpleStringProperty("");
    private SimpleStringProperty lN = new SimpleStringProperty("");
    private SimpleStringProperty eA = new SimpleStringProperty("");

    public Person()
    {
        this("","","");
    }
    public Person(String f, String n, String e)
    {
        this.setfN(f);
        this.setlN(n);
        this.seteA(e);
    }
    public String getfN() {
        return fN.get();
    }

    public SimpleStringProperty fNProperty() {
        return fN;
    }

    public void setfN(String fN) {
        this.fN.set(fN);
    }

    public String getlN() {
        return lN.get();
    }

    public SimpleStringProperty lNProperty() {
        return lN;
    }

    public void setlN(String lN) {
        this.lN.set(lN);
    }

    public String geteA() {
        return eA.get();
    }

    public SimpleStringProperty eAProperty() {
        return eA;
    }

    public void seteA(String eA) {
        this.eA.set(eA);
    }

    public void readObject(ObjectInputStream inP) throws ClassNotFoundException, IOException
    {
        fN = new SimpleStringProperty(inP.readUTF());
        lN = new SimpleStringProperty(inP.readUTF());
        eA = new SimpleStringProperty(inP.readUTF());
    }
    public void writeObject(ObjectOutputStream ouP) throws ClassNotFoundException, IOException
    {
        ouP.writeUTF(fN.getValue());
        ouP.writeUTF(lN.getValue());
        ouP.writeUTF(eA.getValue());
    }

}
