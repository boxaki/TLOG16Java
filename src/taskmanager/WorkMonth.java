/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Akos Varga
 */
public class WorkMonth {

    private final static boolean WEEKEND_DISABLED = false;
    private final List<WorkDay> days;
    private final YearMonth date;
    private long sumPerMonth;
    private long requiredMinPerMonth;

    public WorkMonth(int year, int month) {
        date = YearMonth.of(year, month);
        days = new ArrayList<>();
        requiredMinPerMonth = 0;
        sumPerMonth = 0;
    }

    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }

    public long getSumPerMonth() {
        return sumPerMonth;
    }

    public long getRequiredMinPerMonth() {
        return requiredMinPerMonth;
    }

    public long getExtraMinPerMonth() {
        long extraMinPerMonth = 0;
        for (WorkDay actualDay : days) {
            extraMinPerMonth += actualDay.getExtraMinPerDay();
        }
        return extraMinPerMonth;
    }

    public boolean isNewDate(WorkDay newWorkDay) {
        for (WorkDay existingWorkDay : days) {
            if (existingWorkDay.getActualDay().equals(newWorkDay.getActualDay())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isSameMonth(WorkDay newWorkDay){
        YearMonth newWorkDayYearMonth = YearMonth.from(newWorkDay.getActualDay());
        
        return newWorkDayYearMonth.equals(date);
    }
    
    public void addWorkDay(WorkDay wd){
        addWorkDay(wd, WEEKEND_DISABLED);
    }

    public void addWorkDay(WorkDay wd, boolean isWeekendEnabled){
        if(isWeekendEnabled || wd.isWeekday()){
            if(isSameMonth(wd) && isNewDate(wd)){
                days.add(wd);
                sumPerMonth += wd.getSumPerDay();
                requiredMinPerMonth += wd.getRequiredMinPerDay();
            }
        }        
    }
}
