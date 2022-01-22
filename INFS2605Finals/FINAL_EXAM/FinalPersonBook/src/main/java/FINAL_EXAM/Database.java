/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FINAL_EXAM;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Aldo
 */
public class Database {
    
    final private String database= "jdbc:sqlite:PBDatabase.db";
    
//    setup database
    public static void setupDatabase() throws ClassNotFoundException, SQLException{
         Class.forName("org.sqlite.JDBC");
            
            //create connection
            Connection conn = DriverManager.getConnection("jdbc:sqlite:PBDatabase.db");

            //create statement
            Statement st = conn.createStatement();

            // Create Person Table, with id
            String createPerson = "CREATE TABLE IF NOT EXISTS Person ("
                    + "personID INTEGER PRIMARY KEY AUTOINCREMENT"
                    + ", FullName String NOT NULL"
                    + ", bdayDay Int NOT NULL"
                    + ", bdayMonth Int NOT NULL"
                    + ", BYear String  "
                    + ", importantPersonal Boolean"
                    + ", importantBusiness Boolean)";
            
            st.execute(createPerson);
            
           String createCaseNote = "CREATE TABLE IF NOT EXISTS CaseNote ("
                    + "caseNoteID INTEGER PRIMARY KEY AUTOINCREMENT"
                    + ", cn_personID  INTEGER NOT NULL"
                    + ", caseNoteText  String NOT NULL"
                    + ", day int NOT NULL"
                    + ", month int NOT NULL"
                    + ", year int NOT NULL"
                    + ", hour int NOT NULL"
                    + ", min int NOT NULL"
                    + ", sec int NOT NULL) ";
                    
            
            st.execute(createCaseNote);
            
            // Close connections and statements

            st.close();
            conn.close();
            
            System.out.println("Database Connected.");
        
        
    }
    
    //insert some data
    public static void insertData() throws SQLException, FileNotFoundException{
        //create connection
        Connection conn = DriverManager.getConnection("jdbc:sqlite:PBDatabase.db");
        
        //write the SQL query and the java code to insert 
        PreparedStatement pStC = conn.prepareStatement(
            "INSERT OR IGNORE INTO Person (personID, FullName , bdayDay ,bdayMonth,  BYear, importantPersonal , importantBusiness) VALUES (?,?,?,?,?,?,?)"
        );

        // Person Data to insert
        int[] personID = {1,2};
        String[] FullName = {"Andrew Bob", "Asap Rocky"};
        int[] bdayDay = {9,2};
        int[] bdayMonth = {12,5};
        String[] BYear = {"1990", null};
        boolean[] importantPersonal = {true,false};
        boolean[] importantBusiness = {false,true};
        

        // Loop to insert via sanitised prepared statements
        for (int i = 0; i < 2; i++) {
            pStC.setInt(1, personID[i]);
            pStC.setString(2, FullName[i]);
            pStC.setInt(3, bdayDay[i]);
            pStC.setInt(4, bdayMonth[i]);
            pStC.setString(5, BYear[i]);
            pStC.setBoolean(6, importantPersonal[i]);
            pStC.setBoolean(7, importantBusiness[i]);
            pStC.executeUpdate();
        }
        
        pStC.close();
        
        //insert case note data
        //write the SQL query and the java code to insert 
        PreparedStatement pStCN = conn.prepareStatement(
            "INSERT OR IGNORE INTO CaseNote (caseNoteID ,cn_personID, caseNoteText, day, month ,year, hour, min, sec) VALUES (?,?,?,?,?,?,?,?,?)"
        );

        // Person Data to insert
        int[] caseNoteID = {1,2};
        int[] cn_personID = {1,1};
        String[] caseNoteText = {"AHHHHHHHHHHHHHHHHHHHHH", "Daddddy PLEASEE"};
        int[] day = {12,23};
        int[] month = {12,2};
        int[] year = {2020,2021};
        int[] hour = {22,18};
        int[] min = {16,25};
        int[] sec = {16,45};

        

        // Loop to insert via sanitised prepared statements
        for (int i = 0; i < 2; i++) {
            pStCN.setInt(1, caseNoteID[i]);
            pStCN.setInt(2, cn_personID[i]);
            pStCN.setString(3, caseNoteText[i]);
            pStCN.setInt(4, day[i]);
            pStCN.setInt(5, month[i]);
            pStCN.setInt(6, year[i]);
            pStCN.setInt(7, hour[i]);
            pStCN.setInt(8, min[i]);
            pStCN.setInt(9, sec[i]);
            pStCN.executeUpdate();
        }
        
        //close connection
        pStCN.close();
        conn.close();
        
    }
    
    //get person data
    public static ObservableList<Person> getPerson() throws SQLException {
        
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
        // Close 
        st.close();
        conn.close();
        return PersonList;
    }
    
    
    
    
    
//    get casenote
    public static ObservableList<CaseNote> getCN() throws SQLException {
        
        // Get ResultSet of all people that exist in the database
        Connection conn = DriverManager.getConnection("jdbc:sqlite:PBDatabase.db");
        Statement st = conn.createStatement();
        String query = "SELECT * FROM CaseNote";
        ResultSet rs = st.executeQuery(query);
               
        ObservableList<CaseNote> CNList =  FXCollections.observableArrayList();

        // Add each row in the ResultSet to the datalist
        while(rs.next()){

           
            CNList.add(new CaseNote(rs.getInt(1),
                    rs.getInt(2), 
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getInt(5),
                    rs.getInt(6),
                    rs.getInt(7),
                    rs.getInt(8),
                    rs.getInt(9)));
        }
        // Close 
        st.close();
        conn.close();
        return CNList;
    }
    
    
    //print data
    public static void printAll() throws SQLException{
        //create connection
        Connection conn = DriverManager.getConnection("JDBC:sqlite:PBDatabase.db");

        //create statement
        Statement st = conn.createStatement ();

        //write the SQL query and the java code to retrieve and print out all Data
        
        
        //print person data
        System.out.println("People in Database");
        
        
        String selectQuery = "SELECT * FROM Person ";

        ResultSet rs = st.executeQuery(selectQuery);

        while(rs.next()){
                System.out.println(rs.getString(1) + ", " 
                + rs.getString(2) + ", " 
                + rs.getString(3) + ", " 
                + rs.getString(4) + ", "
                + rs.getString(5) + ", "   
                + rs.getString(6) + ", "        
                + rs.getString(7));
        }
        
        //print case note
        System.out.println("CaseNotes in Database");
        
        String selectCN = "SELECT * FROM CaseNote ";

        ResultSet rscn = st.executeQuery(selectCN);

        while(rscn.next()){
                System.out.println(rscn.getString(1) + ", " 
                + rscn.getString(2) + ", "  
                + rscn.getString(3) + ", "   
                + rscn.getString(4) + ", "   
                + rscn.getString(5) + ", "   
                + rscn.getString(6) + ", "   
                + rscn.getString(7) + ", "   
                + rscn.getString(8) + ", "   
                + rscn.getString(9));
        }

        st.close();
        conn.close();
    }
    
    //
    
    
    
}
