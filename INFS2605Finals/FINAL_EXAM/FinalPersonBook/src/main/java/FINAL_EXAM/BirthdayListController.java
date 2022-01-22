/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FINAL_EXAM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Aldo
 */
public class BirthdayListController {
    
    @FXML
    TableView<Person> personTbl;
    
    @FXML
    TableColumn <Person, String> FullName;

    @FXML
    TableColumn<Person, String> Birthday;
    
    @FXML
    TableColumn<Person, String> DTB;
    
 
    
    
     public void initialize() throws SQLException {


        ObservableList<Person> personList = FXCollections.observableArrayList(Database.getPerson());
        
        personTbl.setItems(personList);
        
        FullName.setCellValueFactory(new PropertyValueFactory<Person, String>("fullName"));
        
        Birthday.setCellValueFactory( new PropertyValueFactory<Person, String>("BirthDate"));
 
        DTB.setCellValueFactory(new PropertyValueFactory<Person, String>("DayTillBday"));
      
    
     System.out.println("HOOLAAA AMIGOS");
         
     }
     
    @FXML
    private void HuiJia() throws IOException{
          App.setRoot("primary");
    }
    
}
