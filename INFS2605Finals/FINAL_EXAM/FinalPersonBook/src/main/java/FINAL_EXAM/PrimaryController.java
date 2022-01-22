package FINAL_EXAM;

import java.io.IOException;
import static java.nio.file.Files.isHidden;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.MonthDay;
import java.time.Year;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class PrimaryController  {
    
    @FXML public ListView<Person> myListView;
    ObservableList<Person> tempPerson = FXCollections.observableArrayList();
    
    
    @FXML public ChoiceBox<CaseNote> choiceBoxForCaseNotes;
    ObservableList<CaseNote> tempCase = FXCollections.observableArrayList();
    
    @FXML public Label FN;
    @FXML public Label BD;
    @FXML public Label Day;
    @FXML public Label Month;
    @FXML public Label Year;
    @FXML public Label RT;
    
    @FXML public ChoiceBox cb;
    
    @FXML public Label lblCaseNotes;
    @FXML public Button btnNew;
    @FXML public Button btnCancel;
    @FXML public Button btnSaveNP;
    @FXML public Button btnSaveUP;
    @FXML public Button BDList;
    @FXML public Button btnNewCaseNote;
    @FXML public Button btnCancelNewCaseNote;
    @FXML public Button btnSaveCaseNote;
    @FXML public ToggleButton btnView;
    @FXML public ToggleButton btnEdit;
    @FXML public TextField txtFullName;
    @FXML public TextField txtBdayDay;
    @FXML public TextField SearchBar;
    @FXML public TextField txtBdayMonth;
    @FXML public TextField txtBdayYear;
    @FXML public TextArea txtCaseNotes;
    @FXML public CheckBox chkPersonal;
    @FXML public CheckBox chkBusiness;
    
    
    
    
    
//    public ObservableList<Person> people = FXCollections.observableArrayList(HelperForData.generateSamplePersonRecords());
        
    public Person currentlySelectedPerson;
    public CaseNote currentlySelectedCaseNote;
    public boolean changesMadeToPersonRecord;
    
    // prevent userDidSelectCaseNote() from executing when switching between person records.
    public boolean suppressCaseNoteListener = false;
    
    private Alert errorMessage = new Alert(Alert.AlertType.ERROR);

    public void initialize() throws SQLException {
        this.setupButtonIcons();

        
        // TIP-08: ToggleGroup
        ToggleGroup tgp = new ToggleGroup();
        tgp.getToggles().add(btnView);
        tgp.getToggles().add(btnEdit);
        
        // some buttons only visibile under very specific conditions
        HelperForJavafx.setNodesHidden(new Node[]{btnSaveUP, btnCancelNewCaseNote, btnSaveCaseNote}, true);
        
        // since View is selected by default
        this.setPersonDetailsEditMode(false);
        
        myListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                userDidSelectListItem(newValue);
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        
        choiceBoxForCaseNotes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                userDidSelectCaseNote(newValue);
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e){
                System.out.println("NPL");
            }
        });
        
        myListView.getItems().addAll(Database.getPerson());
        myListView.getSelectionModel().selectFirst();
        
        cb.getItems().add("English");
        cb.getItems().add("French");
        cb.getItems().add("Simplified Chinese");
        cb.getItems().add("Traditional Chinese");
        
        
        cb.getSelectionModel().selectFirst();
        
    }
    //
    // HANDLE USER ACTIONS
    //
    
    
    @FXML
    private void userChangeLanguage(){
        
        Stage s = App.getPrimaryStage();
             if(cb.getValue().equals("French")){
                 
                 s.setTitle("Carnet d'adresses");
                 FN.setText("Nom et prénom ");
                 btnNew.setText("Insérer");
                 lblCaseNotes.setText("Journal de bord");
                 btnCancel.setText("Annuler");
                 btnSaveUP.setText("Enregistrer");
                 btnSaveNP.setText("Enregistrer");
                 BD.setText("Date d'anniversaire");
                 Day.setText("Jour");
                 Month.setText("mois");
                 Year.setText("année");
                 RT.setText("Type de relation");
                 btnView.setText("Voir");
                 btnEdit.setText("Modifier");
                 SearchBar.setText("Recherche");
                 chkBusiness.setText("Important pour des raisons commerciales");
                 chkPersonal.setText("Important pour des raisons personnelles");
                 btnNewCaseNote.setText("Insérer");
                 btnCancelNewCaseNote.setText("Annuler");
                 BDList.setText("Calendrier d'anniversaire");  
                 
                 
             }  else if (cb.getValue().equals("English")) {
                 s.setTitle("Personbook");
                 
                 FN.setText("Full name");
                 
                 BD.setText("Birthday");
                 Day.setText("Day");
                 Month.setText("Month");
                 Year.setText("Year");
                 RT.setText("Relationship Type");
                 chkPersonal.setText("Important for personal reasons");
                 chkBusiness.setText("Important for business reasons");
                 lblCaseNotes.setText("Case notes");
                
                 btnNewCaseNote.setText("New");
                 btnNew.setText("New");
                 
                 btnView.setText("View");
                 btnEdit.setText("Edit");
                 
                 btnCancelNewCaseNote.setText("Cancel");
                 btnCancel.setText("Cancel");
                 
                 btnSaveUP.setText("Save");
                 btnSaveNP.setText("Save");
                 BDList.setText("Birthday list");  
                
                 SearchBar.setText("Search");
                 
                 
 
             } else if (cb.getValue().equals("Simplified Chinese")){
                 s.setTitle("通讯录");
                 
                 
                 FN.setText("全名");
                 
                 BD.setText("生日");
                 Day.setText("日");
                 Month.setText("月");
                 Year.setText("年");
                 RT.setText("关系");
                 chkPersonal.setText("朋友关系");
                 chkBusiness.setText("商业关系");
                 lblCaseNotes.setText("新建");
                
                 btnNewCaseNote.setText("新建");
                 btnNew.setText("新建");
                 
                 btnView.setText("看");
                 btnEdit.setText("编辑");
                 
                 btnCancelNewCaseNote.setText("取消");
                 btnCancel.setText("取消");
                 
                 btnSaveUP.setText("保存");
                 btnSaveNP.setText("保存");
                 BDList.setText("生日日历");  
                
                 SearchBar.setText("搜索");
                 
             }else if (cb.getValue().equals("Traditional Chinese")){
                 s.setTitle("通訊錄");
                 
                 
                 FN.setText("全名");
                 
                 BD.setText("生日");
                 Day.setText("日");
                 Month.setText("月");
                 Year.setText("年");
                 RT.setText("關係");
                 chkPersonal.setText("朋友關係");
                 chkBusiness.setText("商業關係");
                 lblCaseNotes.setText("日誌");
                
                 btnNewCaseNote.setText("新建");
                 btnNew.setText("新建");
                 
                 btnView.setText("看");
                 btnEdit.setText("編輯");
                 
                 btnCancelNewCaseNote.setText("取消");
                 btnCancel.setText("取消");
                 
                 btnSaveUP.setText("保存");
                 btnSaveNP.setText("保存");
                 BDList.setText("生日日曆");  
                
                 SearchBar.setText("搜索");
                

             } else { 
                 System.out.println("Language not recognised");
             }
       
        
        
    }

    @FXML
    private void userDidSelectListItem(Person selectedPerson) throws SQLException {
        
        tempCase.clear();
        choiceBoxForCaseNotes.getItems().clear();
        
        
        try{
        HelperForPersonGUI.populatePersonDetails(selectedPerson.getFullName(),selectedPerson.getBdayDay(),
                selectedPerson.getBdayMonth(),selectedPerson.getBYear(),selectedPerson.isImportantPersonal(),selectedPerson.isImportantBusiness(),
                txtFullName, txtBdayDay, txtBdayMonth, txtBdayYear, chkPersonal, chkBusiness);
             
        
        //populate case notes according to selected person
        for (int i = 0; i < Database.getCN().size(); i++){
            
            if(selectedPerson.getPersonID() == (Database.getCN().get(i).getPersonID())){
                tempCase.add(Database.getCN().get(i));
  
            }
        }
        
        choiceBoxForCaseNotes.getItems().addAll(tempCase);
         
        
        } catch (NullPointerException e){
            System.out.println("NLP");
        }

//      
        //set the view of case notes to the last item
        try{
            choiceBoxForCaseNotes.getSelectionModel().selectLast();
            CaseNote selectedCN = choiceBoxForCaseNotes.getSelectionModel().getSelectedItem();
            txtCaseNotes.setText(selectedCN.getCaseNoteText());
            
        } catch(NullPointerException e) {
            
            txtCaseNotes.setText("");
        }

        
        // by default, cannot edit case notes
        HelperForJavafx.setTextboxEditable(txtCaseNotes, false);
        
        // reset the change tracker
        this.changesMadeToPersonRecord = false;
        
        // finally, update the selection tracker
        this.currentlySelectedPerson = selectedPerson;
    }
    
     @FXML
    private void SearchBar() throws SQLException {
        String search = SearchBar.getText();
        
        if(SearchBar.getText().isEmpty()){
            myListView.getItems().clear();
            myListView.getItems().addAll(Database.getPerson());
        } else {
            tempPerson.clear();
            myListView.getItems().clear();


       
            try{
    //             Get ResultSet of all people that exist in the database
                Connection conn = DriverManager.getConnection("jdbc:sqlite:PBDatabase.db");
                Statement st = conn.createStatement();
                String query = "SELECT DISTINCT personID,FullName,bdayDay,bdayMonth,BYear,importantPersonal,importantBusiness FROM Person JOIN CaseNote ON personID =  cn_personID "
                        + "WHERE CaseNoteText LIKE '%" + search + "%' ";
                ResultSet rs = st.executeQuery(query);


                // Add each row in the ResultSet to the datalist
                while(rs.next()){

                         tempPerson.add(new Person(rs.getInt(1),
                                rs.getString(2), 
                                rs.getInt(3),
                                rs.getInt(4), 
                                rs.getString(5),
                                rs.getBoolean(6),
                                rs.getBoolean(7)));

                }
                
                    String querya = "SELECT DISTINCT personID,FullName,bdayDay,bdayMonth,BYear,importantPersonal,importantBusiness FROM Person WHERE FullName LIKE '%" + search + "%'";
                    ResultSet rsa = st.executeQuery(querya);


                // Add each row in the ResultSet to the datalist
                while(rsa.next()){

                         tempPerson.add(new Person(rsa.getInt(1),
                                rsa.getString(2), 
                                rsa.getInt(3),
                                rsa.getInt(4), 
                                rsa.getString(5),
                                rsa.getBoolean(6),
                                rsa.getBoolean(7)));

                }

                myListView.getItems().addAll(tempPerson);

                // Close 
                st.close();
                conn.close();
            
            } catch (Exception e){
                System.out.println("meh");
            };
        }
    }
    
    @FXML
    private void userDidSelectCaseNote(CaseNote selectedCaseNote) throws SQLException {
        

        if (!suppressCaseNoteListener) {
            // if save button was visible from before, it should be invisible now
            HelperForJavafx.setNodeHidden(btnSaveCaseNote, true);

            // populate case note text
           
            try{
            // Get ResultSet of all people that exist in the database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:PBDatabase.db");
            Statement st = conn.createStatement();
            String query = "SELECT caseNoteText FROM CaseNote WHERE caseNoteID = '" + selectedCaseNote.getCaseNoteID() + "'";
            ResultSet rs = st.executeQuery(query);


            // Add each row in the ResultSet to the datalist
            while(rs.next()){

                     txtCaseNotes.setText(rs.getString(1));
           
            }
            // Close 
            st.close();
            conn.close();
        
    

            // finally, update the selection tracker
            this.currentlySelectedCaseNote = selectedCaseNote;
            
            } catch (NullPointerException e){
                 txtCaseNotes.setText("");
            }
        }
    }
    
    
    @FXML
    private void userDidClickNew() {


        txtFullName.setText("");
        txtBdayDay.setText("0");
        txtBdayMonth.setText("0");
        txtBdayYear.setText("");
        txtCaseNotes.setText("");
        chkPersonal.setSelected(false);
        chkBusiness.setSelected(false);
        choiceBoxForCaseNotes.getItems().clear();
        
  
        btnEdit.setSelected(false);
        this.userDidClickEdit();
        
        btnSaveUP.setVisible(false);
        btnSaveUP.setManaged(false);
        btnSaveNP.setVisible(true);
        btnSaveNP.setManaged(true);
        
    }
    
    
    @FXML
    private void userDidAddNewCaseNote() {
        
        if(myListView.getSelectionModel().getSelectedItem()  == null){
          
            errorMessage.setTitle("No Person Selected");
            errorMessage.setHeaderText("");
            errorMessage.setContentText("Person has not been selected yet. "
                    + " Please select a person.");
            errorMessage.show();
            
        }else {
        txtCaseNotes.setText("");
        txtCaseNotes.requestFocus(); // TIP-09: requestFocus()
        
        this.setCaseNoteEditMode(true);
        }
    }
    
    @FXML
    private void userDidCancelNewCaseNote() throws SQLException {
        this.setCaseNoteEditMode(false);
        
        if (null != currentlySelectedCaseNote) {
            this.userDidSelectCaseNote(currentlySelectedCaseNote);
        } else {
            txtCaseNotes.setText("");
        }
    }
    
    @FXML
    private void userDidClickCancel() throws SQLException {
        this.setPersonDetailsEditMode(false);
        
        
        if (this.currentlySelectedPerson.isNewContactNotYetSaved()) {
            myListView.getItems().remove(currentlySelectedPerson);
        }
    }
    
    @FXML
    private void userDidClickEdit() {
        if(myListView.getSelectionModel().getSelectedItem()  == null){
            errorMessage.setTitle("No Person Selected");
            errorMessage.setHeaderText("");
            errorMessage.setContentText("Person has not been selected yet. "
                    + " Please select a person.");
            errorMessage.show();
        } else {
            this.setPersonDetailsEditMode(true);
            btnSaveNP.setVisible(false);
            btnSaveNP.setManaged(false);
        }
        
    }
    
    @FXML
    private void userDidClickSaveUP() throws SQLException {
        // TIP-10: Outsourcing to HelperForPersonGUI
        //if year is less than
        Person selectedPerson = myListView.getSelectionModel().getSelectedItem();
        if(txtBdayDay.getText().isEmpty() | txtBdayMonth.getText().isEmpty() 
                | Integer.parseInt(txtBdayDay.getText()) >= 32 
                | Integer.parseInt(txtBdayDay.getText()) < 1
                | Integer.parseInt(txtBdayMonth.getText()) >= 13
                | Integer.parseInt(txtBdayMonth.getText()) < 1){
            
            errorMessage.setTitle("Birthday is empty");
            errorMessage.setHeaderText("");
            errorMessage.setContentText("Day and Month cannot be empty. "
                    + " Please insert the proper data.");
            errorMessage.show();
            
        } else {
            
        
            boolean couldSaveSuccessfully = HelperForPersonGUI.updatePersonDetails(this.currentlySelectedPerson, txtFullName, txtBdayDay, txtBdayMonth, txtBdayYear, chkPersonal, chkBusiness);
            
            System.out.println("could save succesfully" + couldSaveSuccessfully);

            if (couldSaveSuccessfully) {

                //create connection
                Connection conn = DriverManager.getConnection("jdbc:sqlite:PBDatabase.db");
                //create statement	
                Statement st = conn.createStatement();

                //write the SQL query and the java code to insert all four pets
                PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Person SET   FullName=? , bdayDay = ? ,bdayMonth = ? "
                            + ",BYear = ? ,importantPersonal = ?  ,"
                            + "importantBusiness = ? WHERE personID = '" +  selectedPerson.getPersonID() + "'"
                );

                boolean BRselected = chkBusiness.isSelected();
                    if (BRselected) {
                        System.out.println(true);
                    } else {
                        System.out.println(false);
                    }

                    boolean PRselected = chkPersonal.isSelected();
                    if (PRselected) {
                        System.out.println(true);
                    } else {
                        System.out.println(false);
                    }


                ps.setString(1, txtFullName.getText());
                ps.setString(2, txtBdayDay.getText());
                ps.setString(3, txtBdayMonth.getText());
                ps.setString(4, txtBdayYear.getText());
                ps.setBoolean(5, PRselected);
                ps.setBoolean(6, BRselected);



                ps.executeUpdate();
                System.out.println("Person info updated");
                Database.printAll();



                this.setPersonDetailsEditMode(false);
                myListView.getItems().clear();
                myListView.getItems().addAll(Database.getPerson());


                try{
                choiceBoxForCaseNotes.getSelectionModel().selectFirst();
                CaseNote selectedCN = choiceBoxForCaseNotes.getSelectionModel().getSelectedItem();
                txtCaseNotes.setText(selectedCN.getCaseNoteText());
                 } catch(NullPointerException e) {
//
                txtCaseNotes.setText("");
                }
                 // show the new name already
            }
        }
    }
    
    @FXML
    private void userDidClickSaveNP() throws SQLException {
        // TIP-10: Outsourcing to HelperForPersonGUI
        
         if(txtBdayDay.getText().isEmpty() | txtBdayMonth.getText().isEmpty() 
                | Integer.parseInt(txtBdayDay.getText()) >= 32 
                | Integer.parseInt(txtBdayDay.getText()) < 1
                | Integer.parseInt(txtBdayMonth.getText()) >= 13
                | Integer.parseInt(txtBdayMonth.getText()) < 1){
            
            errorMessage.setTitle("Birthday is empty");
            errorMessage.setHeaderText("");
            errorMessage.setContentText("Day and Month cannot be empty. "
                    + " Please insert the proper data.");
            errorMessage.show();
            
        } else {
            

            boolean couldSaveSuccessfully = HelperForPersonGUI.updatePersonDetails(this.currentlySelectedPerson, txtFullName, txtBdayDay, txtBdayMonth, txtBdayYear, chkPersonal, chkBusiness);
                System.out.println("could save succesfully" + couldSaveSuccessfully);
            if (couldSaveSuccessfully) {
            Person selectedPerson = myListView.getSelectionModel().getSelectedItem();
            //create connection
            Connection conn = DriverManager.getConnection("jdbc:sqlite:PBDatabase.db");
            //create statement	
            Statement st = conn.createStatement();


            //write the SQL query and the java code to insert all four pets
            PreparedStatement ps = conn.prepareStatement(
                "INSERT OR IGNORE INTO Person (FullName, bdayDay, bdayMonth, BYear,"
                            + "importantPersonal, importantBusiness) "
                            + "VALUES (?,?,?,?,?,?)"
                );

            boolean BRselected = chkBusiness.isSelected();
                if (BRselected) {
                    System.out.println(true);
                } else {
                    System.out.println(false);
                }

                boolean PRselected = chkPersonal.isSelected();
                if (PRselected) {
                    System.out.println(true);
                } else {
                    System.out.println(false);
                }


            ps.setString(1, txtFullName.getText());
            ps.setString(2, txtBdayDay.getText());
            ps.setString(3, txtBdayMonth.getText());
            ps.setString(4, txtBdayYear.getText());
            ps.setBoolean(5, PRselected);
            ps.setBoolean(6, BRselected);



            ps.executeUpdate();
            System.out.println("Person inserted");
            Database.printAll();



            this.setPersonDetailsEditMode(false);
            myListView.getItems().clear();
            myListView.getItems().addAll(Database.getPerson());
        }

           
        }
    }
    @FXML
    private void userDidClickSaveCaseNote() throws SQLException {
        CaseNote newCaseNote = new CaseNote();
        newCaseNote.setCaseNoteText(txtCaseNotes.getText());

         Person selectedPerson = myListView.getSelectionModel().getSelectedItem();
         
        //create connection
        Connection conn = DriverManager.getConnection("jdbc:sqlite:PBDatabase.db");
        //create statement	
        Statement st = conn.createStatement();

        Calendar calendar = Calendar.getInstance();
//        
//        write the SQL query and the java code to insert data
        PreparedStatement ps = conn.prepareStatement(
            "INSERT OR IGNORE INTO CaseNote (cn_personID, caseNoteText, day, month ,year, hour, min, sec) VALUES (?,?,?,?,?,?,?,?)"
            );
        
        ps.setInt(1, selectedPerson.getPersonID());
        ps.setString(2, txtCaseNotes.getText());
        ps.setInt(3, calendar.get(Calendar.DAY_OF_MONTH));
        ps.setInt(4, calendar.get(Calendar.MONTH));
        ps.setInt(5, calendar.get(Calendar.YEAR));
        ps.setInt(6, calendar.get(Calendar.HOUR_OF_DAY));
        ps.setInt(7, calendar.get(Calendar.MINUTE));
        ps.setInt(8, calendar.get(Calendar.SECOND));



        ps.executeUpdate();
        System.out.println("CaseNote ADEDED");
        Database.printAll();

        
        this.setCaseNoteEditMode(false);
        
        // change selection to new case note
        choiceBoxForCaseNotes.setDisable(false); // there is now at least 1 case note, so this should not be disabled
        
        tempCase.clear();

         for (int i = 0; i < Database.getCN().size(); i++){
            
            if(selectedPerson.getPersonID() == (Database.getCN().get(i).getPersonID())){
                tempCase.add(Database.getCN().get(i));
                
                
            }
        }
        
        choiceBoxForCaseNotes.getItems().clear();
        choiceBoxForCaseNotes.getItems().addAll(tempCase);
         

        choiceBoxForCaseNotes.getSelectionModel().selectLast();
        this.userDidSelectCaseNote(newCaseNote);
    }
    
    @FXML
    private void userDidUpdatePersonDetails() {
        HelperForJavafx.disableButtonIfTextIsBlank(btnSaveUP, txtFullName.getText());
        HelperForJavafx.disableButtonIfTextIsBlank(btnSaveNP, txtFullName.getText());
    }
    @FXML
    private void userDidUpdateActiveCaseNote() {
        HelperForJavafx.disableButtonIfTextIsBlank(btnSaveCaseNote, txtCaseNotes.getText());
    }
    
    @FXML
    public void ViewBirthday() throws IOException {
        App.setRoot("BirthdayList");
    }
    
    //
    // FUNCTIONALITIES
    //
    
    private void setupButtonIcons() {
        HelperForJavafx.setupIconForButton(btnNew, "Farm-Fresh_add.png");
        HelperForJavafx.setupIconForButton(btnNewCaseNote, "Farm-Fresh_add.png");
        HelperForJavafx.setupIconForButton(btnEdit, "Farm-Fresh_pencil.png");
        HelperForJavafx.setupIconForButton(btnView, "Farm-Fresh_vcard.png");
        HelperForJavafx.setupIconForButton(btnSaveUP, "Farm-Fresh_diskette.png");
        HelperForJavafx.setupIconForButton(btnSaveNP, "Farm-Fresh_diskette.png");
        HelperForJavafx.setupIconForButton(btnSaveCaseNote, "Farm-Fresh_diskette.png");
        HelperForJavafx.setupIconForButton(btnCancel, "Farm-Fresh_delete.png");
        HelperForJavafx.setupIconForButton(btnCancelNewCaseNote, "Farm-Fresh_delete.png");
    }
    

    
    private void setPersonDetailsEditMode(boolean isEditMode) {
        HelperForJavafx.setTextboxesEditable(new TextField[]{txtFullName, txtBdayDay, txtBdayMonth, txtBdayYear}, isEditMode);
        HelperForJavafx.setNodesHidden(new Node[]{btnCancel, btnSaveUP}, !isEditMode);
        HelperForJavafx.setNodesHidden(new Node[]{btnCancel, btnSaveNP}, !isEditMode);

        
        // set disable/enable for nodes
        Node[] nodesToDisableIffEditing = {btnNewCaseNote, btnView, btnEdit, myListView};
        Node[] nodesToEnableIffEditing = {chkPersonal, chkBusiness};
        HelperForJavafx.setNodesDisabled(nodesToDisableIffEditing, isEditMode);
        HelperForJavafx.setNodesDisabled(nodesToEnableIffEditing, !isEditMode);
        
        // behaviour only required when entering edit mode
        txtFullName.requestFocus();
        
        // behaviour only required when exiting edit mode
        if (!isEditMode) {
            System.out.println("!isEditMode");
            
            btnSaveNP.setDisable(true);
            btnSaveUP.setDisable(true);// disable save button for next edit mode
            btnView.setSelected(true);

        }
    }
    
    private void setCaseNoteEditMode(boolean isEditMode) {
        // change which buttons are visible
        HelperForJavafx.setNodesHidden(new Node[]{choiceBoxForCaseNotes, btnNewCaseNote}, isEditMode);
        HelperForJavafx.setNodesHidden(new Node[]{btnCancelNewCaseNote, btnSaveCaseNote}, !isEditMode);
        
        // user should not be allowed to move between persons when in case note edit mode
        myListView.setDisable(isEditMode);
        txtCaseNotes.setDisable(!isEditMode);
        
        
        if (!isEditMode) { 
            btnSaveCaseNote.setDisable(true);
        }
        
        HelperForJavafx.setTextboxEditable(txtCaseNotes, true);
    }
}
