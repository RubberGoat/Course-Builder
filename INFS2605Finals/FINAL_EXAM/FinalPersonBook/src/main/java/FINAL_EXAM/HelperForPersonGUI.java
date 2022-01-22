/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FINAL_EXAM;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author blair
 */
public class HelperForPersonGUI {
    public static void populatePersonDetails(String fullname, int day,int month,String year,boolean pr, boolean br, 
            TextField txtFullName,TextField txtBdayDay, TextField txtBdayMonth, 
            TextField txtBdayYear, CheckBox chkPersonal, CheckBox chkBusiness  
    ) {
        
        try{
         // set name if available

            txtFullName.setText(fullname);

        // set birthday MonthDay if available.
            // TIP: to quickly convert an int to a string without too much fuss, concatenate with an empty string
            txtBdayDay.setText(Integer.toString(day));
            txtBdayMonth.setText(Integer.toString(month));

        
        // set birthday year if available. 

            txtBdayYear.setText(year);

        
        // personal and business checkboxes
        chkPersonal.setSelected(pr);
        chkBusiness.setSelected(br);
        } catch (NullPointerException e){
            System.out.println("NullPointer");
        }
    }
    
    public static boolean updatePersonDetails(
        Person person,
        TextField txtFullName,
        TextField txtBdayDay, TextField txtBdayMonth, TextField txtBdayYear,
        CheckBox chkPersonal, CheckBox chkBusiness
    ) {
        boolean successfulSoFar = true;
        
        
        // try set birthday; if bad birthday is set, prevent further progress
        String monthString = txtBdayMonth.getText();
        String dayString = txtBdayDay.getText();
        if (!monthString.trim().isEmpty() || !dayString.trim().isEmpty()) {
            successfulSoFar = HelperForData.trySetPersonBdayMonthDay(person, monthString, dayString);
            
            System.out.println("Setbday " + successfulSoFar);
        }
        
        
        try{
        // try set birth year; if bad birth year is set, prevent further progress
            String yearString = txtBdayYear.getText();
            if (!yearString.trim().isEmpty() ) {
                successfulSoFar = HelperForData.trySetPersonBdayYear(person, yearString);

                System.out.println("Setyear " + successfulSoFar);
            }



//            if(!txtBdayYear.getText().isEmpty() && yearString.length() > 4 && yearString.length() < 4 ){
//                successfulSoFar = false;
//
//                 System.out.println("year not empty " + successfulSoFar);
//            } else {
//                successfulSoFar = true;
//            }
        
        
        } catch (NullPointerException e){
            System.out.println("nullpointer");
        
        }
            
        
        // save
        if (successfulSoFar) {    
            person.setFullName(txtFullName.getText());
        
            person.setImportantPersonal(chkPersonal.isSelected());
            person.setImportantBusiness(chkBusiness.isSelected());
            
            if (person.isNewContactNotYetSaved()) {
                person.setNewContactNotYetSaved(false);
            }
        }
        
        System.out.println("Update VBoolead" + successfulSoFar );
        
        return successfulSoFar;
    }
    
  

}
