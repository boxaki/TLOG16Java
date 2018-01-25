/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import timelogger.excetions.NotNewMonthException;

/**
 *
 * @author Akos Varga
 */
public class TimeLogger {

    private final List<WorkMonth> months;
    
    public TimeLogger(){
        months = new ArrayList<>();
    }

    public List<WorkMonth> getMonths() {
        return months;
    }

    private boolean isNewMonth(WorkMonth monthToCheck) {
        for (WorkMonth existingMonth : months) {
            if (existingMonth.getDate().equals(monthToCheck.getDate())) {
                return false;                
            }
        }
        return true;
    }
    
    public void addNewMonth(WorkMonth monthToAdd) throws NotNewMonthException{
        if(isNewMonth(monthToAdd)){            
            months.add(monthToAdd);
            Collections.sort(months, Comparator.comparing(WorkMonth::getDate));
            
        }else{
            throw new NotNewMonthException("Month already exists!");
        }
        
    }

}
