/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import timelogger.excetions.EmptyTimeFieldException;
import timelogger.excetions.NotExpectedTimeOrderException;

/**
 *
 * @author Akos Varga
 */
public class Util {

    public static LocalTime roundToMultipleQuarterHour(LocalTime startTime, LocalTime endTime) throws EmptyTimeFieldException, NotExpectedTimeOrderException {
        if (!isMultipleQuarterHour(startTime, endTime)) {
            long duration = Duration.between(startTime, endTime).toMinutes();
            long mod = duration % 15;
            long roundedDuration = mod > 7 ? duration + 15 - mod : duration - mod;
            endTime = startTime.plusMinutes(roundedDuration);
        }
        return endTime;
    }

    public static boolean isSeparatedTime(Task newTask, List<Task> tasks) throws EmptyTimeFieldException {
        //Task matchingTask;
        LocalTime newTaskStartTime = newTask.getStartTime();
        LocalTime newTaskEndTime = newTask.getEndTime();
        /*
        matchingTask = tasks.stream()
                .filter(task -> task.getStartTime().isBefore(newTask.getEndTime()) && newTask.getStartTime().isBefore(task.getEndTime()))                
                .findFirst()
                .orElse(null);
         */
        for (Task existingTask : tasks) {
            LocalTime existingTaskStartTime = existingTask.getStartTime();
            LocalTime existingTaskEndTime = existingTask.getEndTime();
            if( existingTaskStartTime.equals(existingTaskEndTime) && (!existingTaskStartTime.isBefore(newTaskStartTime) && existingTaskStartTime.isBefore(newTaskEndTime))){
                return false;
            }
            if( newTaskStartTime.equals(newTaskEndTime) && (!newTaskStartTime.isBefore(existingTaskStartTime) && newTaskStartTime.isBefore(existingTaskEndTime)) ){
                return false;                
            }
            if (existingTask.getStartTime().isBefore(newTask.getEndTime()) && newTask.getStartTime().isBefore(existingTask.getEndTime())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWeekday(LocalDate actualDay) {
        DayOfWeek day = actualDay.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }

    public static boolean isMultipleQuarterHour(LocalTime startTime, LocalTime endTime) throws EmptyTimeFieldException, NotExpectedTimeOrderException {
        if (startTime == null || endTime == null) {
            throw new EmptyTimeFieldException();
        }
        if (startTime.isAfter(endTime)) {
            throw new NotExpectedTimeOrderException();
        }
        return isMultipleQuarterHour(Duration.between(startTime, endTime).toMinutes());
    }

    public static boolean isMultipleQuarterHour(long minutes) {
        return minutes % 15 == 0;
    }

}
