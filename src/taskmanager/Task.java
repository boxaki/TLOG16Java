/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.time.Duration;
import java.time.LocalTime;

/**
 *
 * @author Akos Varga
 */
public class Task { 
    
    private static final String  VALID_TASKID_1 = "\\d{4}";
    private static final String  VALID_TASKID_2 = "LT-[\\d{4}]";
    private static final int QUARTER_HOUR = 15;

    private final String taskId;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String comment;
    
    public Task(String taskId, int startHour, int startMinutes, int endHour, int endMinutes, String comment){
        this(taskId, LocalTime.of(startHour, startMinutes), LocalTime.of(endHour, endMinutes), comment );
    }
    
    public Task(String taskId, String startTime, String endTime, String comment){
        this(taskId, LocalTime.parse(startTime), LocalTime.parse(endTime), comment);
    }
    
    private Task(String taskId, LocalTime startTime, LocalTime endTime, String comment){
        this.taskId = taskId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getComment() {
        return comment;
    }
    
    public long getMinPerTask(){
        return Duration.between(startTime, endTime).toMinutes();
    }
    
    public boolean isValidTaskId(){
        return taskId.matches(VALID_TASKID_1) || taskId.matches(VALID_TASKID_2);
    }
    
    public boolean isMultipleQuarterHour(){
        return getMinPerTask()%QUARTER_HOUR == 0;
    }
    
        
}
