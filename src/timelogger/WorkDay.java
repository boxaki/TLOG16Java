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

/**
 *
 * @author Akos Varga
 */
public class WorkDay {

    private final static int DEFAULT_REQUIRED_MIN_PER_DAY = 450;

    private final List<Task> tasks;
    private long requiredMinPerDay;
    private LocalDate actualDay;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public long getSumPerDay() {

        sumPerDay = 0;
        for (Task t : tasks) {
            sumPerDay += t.getMinPerTask();
        }
        return sumPerDay;
    }

    public long getExtraMinPerDay() {
        return getSumPerDay() - getRequiredMinPerDay();
    }

    public LocalTime getLatestTaskEndTime() {
        if (tasks.isEmpty()) {
            return null;
        }
        return tasks.get(tasks.size() - 1).getEndTime();

    }

    public void setRequiredMinPerDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
    }

    public void setActualDay(int year, int month, int day) {
        this.actualDay = LocalDate.of(year, month, day);
    }

    public void addTask(Task t) {
        if (Util.isMultipleQuarterHour(t.getMinPerTask()) && Util.isSepatedTime(t, tasks)) {
            tasks.add(t);
            Collections.sort(tasks, Comparator.comparing(Task::getStartTime));
        }
    }

}
