/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Akos Varga
 */
public class Util {

    public static LocalTime roundToMultipleQuarterHour(LocalTime startTime, LocalTime endTime) {
        
        if(isMultipleQuarterHour(startTime, endTime)){
            return endTime;
        }
        
        long duration = Duration.between(startTime, endTime).toMinutes();
        long mod = duration % 15;
        long roundedDuration = mod > 7 ? duration + 15 - mod : duration - mod;
        
        return startTime.plusMinutes(roundedDuration);
      
    }

    public static boolean isSepatedTime(Task newTask, List<Task> tasks) {
       
        Task matchingTask = tasks.stream()
                .filter(task -> task.getStartTime().isBefore(newTask.getEndTime()) && newTask.getStartTime().isBefore(task.getEndTime()))
                .findFirst()
                .orElse(null);
       
        if(matchingTask == null){
            System.out.println("separated time");
        }
        else
            System.out.println("not separated time");
       

        return matchingTask == null;
    }

    public static boolean isWeekday(LocalDate actualDay) {
        DayOfWeek day = actualDay.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }
    
    public static boolean isMultipleQuarterHour(LocalTime startTime, LocalTime endTime){
        return isMultipleQuarterHour(Duration.between(startTime, endTime).toMinutes());
    }

    public static boolean isMultipleQuarterHour(long minutes) {
        if(minutes %15 == 0){
            System.out.println("is multiple");
        }
        else
            System.out.println("not multiple");
        return minutes % 15 == 0;
    }

}
