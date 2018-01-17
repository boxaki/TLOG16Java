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

    private static final String VALID_REDMINE_TASKID = "\\d{4}";
    private static final String VALID_LT_TASKID = "LT-\\d{4}";

    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;

    public Task(String taskId) {
        this.taskId = taskId;
    }

    public Task(String taskId, int startHour, int startMin, int endHour, int endMinutes, String comment) {
        this(taskId, LocalTime.of(startHour, startMin), LocalTime.of(endHour, endMinutes), comment);
    }

    public Task(String taskId, String startTime, String endTime, String comment) {
        this(taskId, LocalTime.parse(startTime), LocalTime.parse(endTime), comment);
    }

    private Task(String taskId, LocalTime startTime, LocalTime endTime, String comment) {
        this.taskId = taskId;
        this.startTime = startTime;
        this.endTime = Util.roundToMultipleQuarterHour(startTime, endTime);
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

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setStartTime(int startHour, int startMin) {
        setStartTime(LocalTime.of(startHour, startMin));
    }

    public void setStartTime(String startTime) {
        setStartTime(LocalTime.parse(startTime));
    }
    
    public void setStartTime(LocalTime startTime){        
        this.startTime = startTime;
    }

    public void setEndTime(int endHour, int endMin) {
        setEndTime(LocalTime.of(endHour, endMin));
    }

    public void setEndTime(String endTime) {
        setEndTime(LocalTime.parse(endTime));
    }
    
    public void setEndTime(LocalTime endTime){
        this.endTime = endTime;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getMinPerTask() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    public boolean isValidTaskId() {
        return isValidRedmineTaskId() || isValidLTTaskId();
    }

    private boolean isValidRedmineTaskId() {
        return taskId.matches(VALID_REDMINE_TASKID);
    }

    private boolean isValidLTTaskId() {
        return taskId.matches(VALID_LT_TASKID);
    }

    
    @Override
    public String toString() {  
        String end = "-";
        if(endTime != null){
            end = endTime.toString();
        }               
        return String.format("Id:%12s started:%-10s finished:%-10s \"%s\"", taskId, startTime.toString(), end, comment);
    }

}
