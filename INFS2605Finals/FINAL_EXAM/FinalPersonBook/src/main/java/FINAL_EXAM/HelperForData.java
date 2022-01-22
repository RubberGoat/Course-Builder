/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FINAL_EXAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author blair
 */
public class HelperForData {
    public static ObservableList<Person> generateSamplePersonRecords() throws SQLException {
        
        // Get ResultSet of all people that exist in the database
        Connection conn = DriverManager.getConnection("jdbc:sqlite:PBDatabase.db");
        Statement st = conn.createStatement();
        String query = "SELECT * FROM Person";
        ResultSet rs = st.executeQuery(query);
               
        ObservableList<Person> PersonList =  FXCollections.observableArrayList();

        // Add each row in the ResultSet to the datalist
        while(rs.next()){

           
            PersonList.add(new Person(rs.getInt(1),
                    rs.getString(2), 
                    rs.getInt(3),
                    rs.getInt(4), 
                    rs.getString(5),
                    rs.getBoolean(6),
                    rs.getBoolean(7)));
        }
        
        
        
        
        CaseNote cn = new CaseNote();
        cn.setCaseNoteText("This case note was created at the time of program execution! :)");

        return PersonList;
    }
    
    // TIP-06: Alerts
    public static boolean trySetPersonBdayMonthDay(Person person, String monthString, String dayString) {
        boolean success = false;
        try {
            MonthDay bdayMonthDay = HelperForData.attemptToEstablishBirthdayFromStrings(monthString, dayString);
            person.setBdayMonthDay(bdayMonthDay);
            success = true;
        } catch (NumberFormatException nfe) {
            HelperForJavafx.alertDataEntryError(
                "We were not able to figure out this person's birthday based on the entered Month and Day."
                + " Please confirm that the entered Month and Day are both numbers."
            );
            success = false;
        } catch (DateTimeException dte) {
            HelperForJavafx.alertDataEntryError(
                "We were not able to figure out this person's birthday based on the entered Month and Day."
                + " Please confirm that the entered Month and Day match with an actual calendar Month and Day."
            );
            success = false;
        } catch(NullPointerException e){
            System.out.println("FUCK OFF");
        }
        
        return success;
    }
    
    public static boolean trySetPersonBdayYear(Person person, String yearString) {
        boolean success = false;
        
        try {
            Year bdayYear = HelperForData.attemptToEstablishBirthYearFromString(yearString);
            person.setBirthdayYear(bdayYear);
            success = true;
        } catch (NumberFormatException nfe) {
            HelperForJavafx.alertDataEntryError(
                "We were not able to figure out this person's birth year based on the entered Year."
                + " Please confirm that the entered Year is a number."
            );
            success = false;
        } catch (DateTimeException dte) {
            HelperForJavafx.alertDataEntryError(
                "We were not able to figure out this person's birth year based on the entered Year."
                + " Please confirm that the entered Year matches with an actual calendar Year."
            );
            success = false;
        } catch (NullPointerException e){
            System.out.println("nlp");
        }
        
       
        System.out.println("Year Bloonea" + success);
        return success;
    }
    
    // TIP-05: MonthDay validation, demonstrating Exception handling
    public static MonthDay attemptToEstablishBirthdayFromStrings(String monthString, String dayString) throws NumberFormatException, DateTimeException {
        int monthInt = Integer.valueOf(monthString);
        int dayInt = Integer.valueOf(dayString);
        
        MonthDay bdayMonthDay = MonthDay.of(monthInt, dayInt);
        return bdayMonthDay;
    }
    
    public static Year attemptToEstablishBirthYearFromString(String yearString) throws NumberFormatException, DateTimeException {
        int yearInt = Integer.valueOf(yearString);
        
        Year bdayYear = Year.of(yearInt);
        return bdayYear;
    }
    
    public static String formatLocalDateTime(LocalDateTime ldt) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        return ldt.format(dtf);
    }
}
