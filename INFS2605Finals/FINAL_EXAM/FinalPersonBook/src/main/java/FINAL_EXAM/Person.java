/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FINAL_EXAM;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import static java.time.MonthDay.now;
import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author blair
 */
public class Person {
    
    private int personID;
    private String fullName;
    private boolean newContactNotYetSaved = false;
    private MonthDay bdayMonthDay;
    private int bdayDay;
    private int bdayMonth;
    private String BYear;
    private Year birthdayYear;
    private boolean importantPersonal  = false;
    private boolean importantBusiness  = false;
    private long DayTillBday;
    private String BirthDate;

    public Person(int personID, String fullName, int bdayDay, int bdayMonth, String BYear, boolean importantPersonal, boolean importantBusiness) {
        this.personID = personID;
        this.fullName = fullName;
        this.bdayDay = bdayDay;
        this.bdayMonth = bdayMonth;
        this.BYear = BYear;
        this.importantPersonal = importantPersonal;
        this.importantBusiness = importantBusiness;
        
        this.BirthDate = bdayDay + "/" + bdayMonth;
        
        Calendar c = Calendar.getInstance();
        
        this.bdayMonthDay = MonthDay.of(bdayMonth, bdayDay);
        
        LocalDate BD = bdayMonthDay.atYear(c.get(Calendar.YEAR));
         
        MonthDay tm2 = MonthDay.now();
        
        LocalDate today = LocalDate.now();
        LocalDate dd = tm2.atYear(c.get(Calendar.YEAR));
//       
        long p = ChronoUnit.DAYS.between(today, BD);
        
        if (p < 0){
             this.DayTillBday = 365 + p;
        } else {
        this.DayTillBday = ChronoUnit.DAYS.between(today, BD);   
        }
              
    }

    public long getDayTillBday() {
        return DayTillBday;
    }

    public void setDayTillBday(long DayTillBday) {
        this.DayTillBday = DayTillBday;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
    }

    


    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isNewContactNotYetSaved() {
        return newContactNotYetSaved;
    }

    public void setNewContactNotYetSaved(boolean newContactNotYetSaved) {
        this.newContactNotYetSaved = newContactNotYetSaved;
    }

    public MonthDay getBdayMonthDay() {
        return bdayMonthDay;
    }

    public void setBdayMonthDay(MonthDay bdayMonthDay) {
        this.bdayMonthDay = bdayMonthDay;
    }

    public int getBdayDay() {
        return bdayDay;
    }

    public void setBdayDay(int bdayDay) {
        this.bdayDay = bdayDay;
    }

    public int getBdayMonth() {
        return bdayMonth;
    }

    public void setBdayMonth(int bdayMonth) {
        this.bdayMonth = bdayMonth;
    }

    public String getBYear() {
        return BYear;
    }

    public void setBYear(String BYear) {
        this.BYear = BYear;
    }

    public Year getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(Year birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public boolean isImportantPersonal() {
        return importantPersonal;
    }

    public void setImportantPersonal(boolean importantPersonal) {
        this.importantPersonal = importantPersonal;
    }

    public boolean isImportantBusiness() {
        return importantBusiness;
    }

    public void setImportantBusiness(boolean importantBusiness) {
        this.importantBusiness = importantBusiness;
    }
    
    @Override
    public String toString(){
        return this.fullName;
    }
}