/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import timelogger.excetions.EmptyTimeFieldException;
import timelogger.excetions.FutureWorkException;
import timelogger.excetions.NegativeMinutesOfWorkException;
import timelogger.excetions.NotSeparatedTimesException;

/**
 *
 * @author Akos Varga
 */
public class WorkDay {

    private final static int DEFAULT_REQUIRED_MIN_PER_DAY = 450;
    private final static LocalDate DEFAULT_ACTUAL_DAY = LocalDate.now();

    private final List<Task> tasks;
    private long requiredMinPerDay;
    private LocalDate actualDay;
    private long sumPerDay;

    public WorkDay() {
        this.tasks = new ArrayList<>();
        this.requiredMinPerDay = DEFAULT_REQUIRED_MIN_PER_DAY;
        this.actualDay = DEFAULT_ACTUAL_DAY;
        this.sumPerDay = 0;
    }

    public WorkDay(long requiredMinPerDay) throws NegativeMinutesOfWorkException {
        this();
        setRequiredMinOrThrowIfNegative(requiredMinPerDay);
    }

    public WorkDay(int year, int month, int day) throws FutureWorkException {
        this();
        LocalDate date = LocalDate.of(year, month, day);
        setActualDayOrThrowIfFutureDay(date);        
    }

    public WorkDay(long requiredMinPerDay, int year, int month, int day) throws NegativeMinutesOfWorkException, FutureWorkException {
        this(year, month, day);
        setRequiredMinOrThrowIfNegative(requiredMinPerDay);        
    }
 
    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task t) throws EmptyTimeFieldException, NotSeparatedTimesException {
        if (Util.isSeparatedTime(t, tasks)) {
            if (Util.isMultipleQuarterHour(t.getMinPerTask())) {
                tasks.add(t);
                Collections.sort(tasks, Comparator.comparing(Task::getStartTime));
            }
        } else {
            throw new NotSeparatedTimesException("Time intervals overlapping each other!");
        }
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public void setRequiredMinPerDay(long requiredMinPerDay) throws NegativeMinutesOfWorkException {
        setRequiredMinOrThrowIfNegative(requiredMinPerDay);        
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public void setActualDay(int year, int month, int day) throws FutureWorkException {
        LocalDate dayToSet = LocalDate.of(year, month, day);
        setActualDayOrThrowIfFutureDay(dayToSet);        
    }

    public long getSumPerDay() throws EmptyTimeFieldException {

        sumPerDay = 0;
        for (Task t : tasks) {
            sumPerDay += t.getMinPerTask();
        }
        return sumPerDay;
    }

    public long getExtraMinPerDay() throws EmptyTimeFieldException {
        return getSumPerDay() - getRequiredMinPerDay();
    }

    public LocalTime getEndTimeOfLatestTask() throws EmptyTimeFieldException {
        if (tasks.isEmpty()) {
            return null;
        }
        return tasks.get(tasks.size() - 1).getEndTime();
    }

    private void setRequiredMinOrThrowIfNegative(long requiredMin) throws NegativeMinutesOfWorkException {
        if (requiredMin < 0) {
            throw new NegativeMinutesOfWorkException("Required minutes for a day cannot be negative!");
        }
        this.requiredMinPerDay = requiredMin;
    }

    private void setActualDayOrThrowIfFutureDay(LocalDate date) throws FutureWorkException {
        LocalDate today = LocalDate.now();
        if (date.isAfter(today)) {
            throw new FutureWorkException("Future date is not allowed!");
        }
        this.actualDay = date;
    }

}
