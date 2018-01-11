/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Akos Varga
 */
public class WorkDay {

    private final static int DEFAULT_REQUIRED_MIN_PER_DAY = 450;
    private final List<Task> tasks;
    private final long requiredMinPerDay;
    private final LocalDate actualDay;
    private long sumPerDay;

    public WorkDay() {
        this(DEFAULT_REQUIRED_MIN_PER_DAY);
    }

    public WorkDay(long requiredMinPerDay) {
        this(requiredMinPerDay, LocalDate.now());
    }

    public WorkDay(int year, int month, int day) {
        this(DEFAULT_REQUIRED_MIN_PER_DAY, year, month, day);
    }

    public WorkDay(long requiredMinPerDay, int year, int month, int day) {
        this(requiredMinPerDay, LocalDate.of(year, month, day));
    }

    private WorkDay(long requiredMinPerDay, LocalDate actualDay) {
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = actualDay;
        this.tasks = new ArrayList<>();
        this.sumPerDay = 0;
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public long getSumPerDay() {
        return sumPerDay;
    }

    public long getExtraMinPerDay() {
        return getSumPerDay() - getRequiredMinPerDay();
    }

    public boolean isSepatedTime(Task t) {
        LocalTime actualTaskStartTime;
        LocalTime actualTaskEndTime;
        LocalTime tasksStartTime = t.getStartTime();
        LocalTime tasksEndTime = t.getEndTime();

        for (Task actualTask : tasks) {
            actualTaskStartTime = actualTask.getStartTime();
            actualTaskEndTime = actualTask.getEndTime();
            if (isBetween(actualTaskStartTime, actualTaskEndTime, tasksStartTime) && isBetween(actualTaskStartTime, actualTaskEndTime, tasksStartTime)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBetween(LocalTime startTime, LocalTime endTime, LocalTime timeToCompare) {
        return !timeToCompare.isBefore(startTime) && !timeToCompare.isAfter(endTime);
    }
    
    public void addTask(Task t) {
        if(t.isMultipleQuarterHour() && isSepatedTime(t)){
            tasks.add(t);
            sumPerDay += t.getMinPerTask();
        }                
    }
    
    public boolean isWeekday(){
        DayOfWeek day = actualDay.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY ;
    }
    

}
