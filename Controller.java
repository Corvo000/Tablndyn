package sample;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    Person selectedPerson = new Person();
    @FXML TableView<Person> tV = new TableView();
    @FXML TextField tf1 = new TextField();
    @FXML TextField tf2 = new TextField();
    @FXML TextField tf3 = new TextField();
    @FXML Button btnDelete = new Button();
    @FXML Button btnNew = new Button();
    @FXML Label lb = new Label();
    @FXML Button addBtn = new Button();
    @FXML MenuItem menuOpen = new MenuItem();
    @FXML MenuItem menuSave = new MenuItem();
    BooleanProperty selected = new SimpleBooleanProperty();
    BooleanProperty containText1 = new SimpleBooleanProperty(false);
    BooleanProperty containText2 = new SimpleBooleanProperty(false);
    BooleanProperty containText3 = new SimpleBooleanProperty(false);
    Stage stage = new Stage();

    public void newMode(ActionEvent actionEvent)
    {
        tV.getSelectionModel().clearSelection();
        tf1.clear();
        tf2.clear();
        tf3.clear();
        containText1.set(false);
        containText2.set(false);
        containText3.set(false);
    }
    public void deleteItem(ActionEvent actionEvent)
    {
        ObservableList<Person> data = tV.getItems();
        data.remove(tV.getSelectionModel().getSelectedIndex());
    }
    public void addPerson(ActionEvent actionEvent)
    {
        ObservableList<Person> data = tV.getItems();
        if(tV.getSelectionModel().getSelectedIndex() == -1)
        {
            data.add(new Person(tf1.getText(),tf2.getText(),tf3.getText()));
        }
        else
        {
            tV.getSelectionModel().getSelectedItem().setfN(tf1.getText());
            tV.getSelectionModel().getSelectedItem().setlN(tf2.getText());
            tV.getSelectionModel().getSelectedItem().seteA(tf3.getText());
            tV.refresh();
        }
        tf1.clear();
        tf2.clear();
        tf3.clear();
        containText1.set(false);
        containText2.set(false);
        containText3.set(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ObservableList<Person> data = tV.getItems();
        data.add(new Person("P1","P1","P1"));
        data.add(new Person("P2","P2","P2"));
        tV.getSelectionModel().selectedItemProperty().addListener((oV,o,n)->
        {
            if(n != null)
            {
                selectedPerson.setfN(n.getfN());
                selectedPerson.setlN(n.getlN());
                selectedPerson.seteA(n.geteA());
            }
        });
        tf1.textProperty().bindBidirectional(selectedPerson.fNProperty());
        tf2.textProperty().bindBidirectional(selectedPerson.lNProperty());
        tf3.textProperty().bindBidirectional(selectedPerson.eAProperty());
        btnDelete.disableProperty().bind(tV.getSelectionModel().selectedItemProperty().isNull());
        selected.bind(tV.getSelectionModel().selectedIndexProperty().greaterThan(-1));
        lb.textProperty().bind(Bindings.when(selected).then("Edit").otherwise("Create"));
        addBtn.disableProperty().bind(Bindings.when(tf1.textProperty().isEmpty().or(tf2.textProperty().isEmpty()).or(tf3.textProperty().isEmpty())).then(true).otherwise(false));
        tf1.focusedProperty().addListener((oV,o,n)-> {if(o) containText1.set(true);});
        tf2.focusedProperty().addListener((oV,o,n)-> {if(o) containText2.set(true);});
        tf3.focusedProperty().addListener((oV,o,n)-> {if(o) containText3.set(true);});
        tf1.styleProperty().bind(Bindings.when(containText1.and(tf1.textProperty().isEmpty())).then("-fx-border-color:red").otherwise(""));
        tf2.styleProperty().bind(Bindings.when(containText2.and(tf2.textProperty().isEmpty())).then("-fx-border-color:red").otherwise(""));
        tf3.styleProperty().bind(Bindings.when(containText3.and(tf3.textProperty().isEmpty())).then("-fx-border-color:red").otherwise(""));

    }

    public void doExit(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    public void doSave(ActionEvent actionEvent) throws IOException, ClassNotFoundException
    {
        FileChooser fC = new FileChooser();
        File f = fC.showSaveDialog(getStage());
        if(f != null)
        {
            try(ObjectOutputStream ouP = new ObjectOutputStream(new FileOutputStream(f));)
            {
                for( Person p : tV.getItems())
                {
                    ouP.writeObject(p);
                }
            }
            catch(EOFException e1) { }
            catch (IOException e2) { }
        }
    }

    public void doOpen(ActionEvent actionEvent)
    {
        FileChooser fC = new FileChooser();
        File f = fC.showSaveDialog(getStage());
        if(f != null)
        {
            try
            {
                ObjectInputStream inP = new ObjectInputStream(new FileInputStream(f));
                while(true)
                    tV.getItems().add((Person) inP.readObject());
            }
            catch(EOFException e1) { }
            catch (IOException e2) { }
            catch (ClassNotFoundException e) { }
        }
    }
    public void setStage(Stage s)
    {
           stage = s;
    }
    public Stage getStage()
    {
        return stage;
    }
}
