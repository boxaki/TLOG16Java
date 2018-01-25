/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import java.time.Duration;
import java.time.LocalTime;
import timelogger.excetions.EmptyTimeFieldException;
import timelogger.excetions.InvalidTaskIdException;
import timelogger.excetions.NoTaskIdException;
import timelogger.excetions.NotExpectedTimeOrderException;

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

    public Task(String taskId) throws InvalidTaskIdException, NoTaskIdException {
        setTaskId(taskId);
    }

    public Task(String taskId, String startTime, String endTime, String comment) throws NotExpectedTimeOrderException, EmptyTimeFieldException, InvalidTaskIdException, NoTaskIdException {
        this(taskId);
        setStartTime(startTime);
        if (endTime == null) {
            throw new EmptyTimeFieldException("Missing end time!");
        }
        setEndTime(endTime);
        setComment(comment);
    }

    public Task(String taskId, int startHour, int startMin, int endHour, int endMin, String comment) throws NotExpectedTimeOrderException, EmptyTimeFieldException, InvalidTaskIdException, NoTaskIdException {
        this(taskId, String.format("%02d:%02d", startHour, startMin), String.format("%02d:%02d", endHour, endMin), comment);
    }

    public String getTaskId() {
        return taskId;
    }

    public final void setTaskId(String taskId) throws InvalidTaskIdException, NoTaskIdException {
        if (taskId == null) {
            throw new NoTaskIdException("Missing task id!");
        }
        if (!isValidTaskId(taskId)) {
            throw new InvalidTaskIdException("Invalid task id!");
        }
        this.taskId = taskId;
    }

    public static boolean isValidTaskId(String taskId) {
        return isValidRedmineTaskId(taskId) || isValidLTTaskId(taskId);
    }

    private static boolean isValidRedmineTaskId(String taskId) {
        return taskId.matches(VALID_REDMINE_TASKID);
    }

    private static boolean isValidLTTaskId(String taskId) {
        return taskId.matches(VALID_LT_TASKID);
    }

    public LocalTime getStartTime(){       
        return startTime;
    }

    public void setStartTime(int startHour, int startMin) throws NotExpectedTimeOrderException, EmptyTimeFieldException {
        setStartTime(LocalTime.of(startHour, startMin));
    }

    public final void setStartTime(String startTime) throws NotExpectedTimeOrderException, EmptyTimeFieldException {
        setStartTime(LocalTime.parse(startTime));
    }

    public final void setStartTime(LocalTime startTime) throws NotExpectedTimeOrderException, EmptyTimeFieldException {
        if (endTime == null) {
            this.startTime = startTime;
        } else if (!startTime.isAfter(this.endTime)) {
            this.startTime = startTime;
            endTime = Util.roundToMultipleQuarterHour(this.startTime, endTime); 
        } else {
            throw new NotExpectedTimeOrderException("Start time must not be later than end time!");
        }
    }

    public LocalTime getEndTime() throws EmptyTimeFieldException {
        if (endTime == null) {
            throw new EmptyTimeFieldException("Missing end time");
        }
        return endTime;
    }

    public void setEndTime(int endHour, int endMin) throws NotExpectedTimeOrderException, EmptyTimeFieldException {
        setEndTime(LocalTime.of(endHour, endMin));
    }

    public final void setEndTime(String endTime) throws NotExpectedTimeOrderException, EmptyTimeFieldException { 
        if (endTime == null) {
            throw new EmptyTimeFieldException("Missing end time!");
        }
        setEndTime(LocalTime.parse(endTime));
    }

    public final void setEndTime(LocalTime endTime) throws NotExpectedTimeOrderException, EmptyTimeFieldException {
        
        if (!startTime.isAfter(endTime)) {
            this.endTime = Util.roundToMultipleQuarterHour(startTime, endTime);
        } else {
            throw new NotExpectedTimeOrderException("Start time must not be later than end time!");
        }

        this.endTime = Util.roundToMultipleQuarterHour(startTime, endTime);
    }

    public String getComment() {
        return comment;
    }

    public final void setComment(String comment) {
        if (comment == null) {
            comment = "";
        }
        this.comment = comment;
    }

    public long getMinPerTask() throws EmptyTimeFieldException {
        LocalTime taskStartTime = getStartTime();
        LocalTime taskEndTime = getEndTime();

        return Duration.between(taskStartTime, taskEndTime).toMinutes();
    }
    
    @Override
    public String toString() {
        String end = "not yet";
        if (!endTime.equals(startTime) ) {
            end = endTime.toString();
        }
        return String.format("Id:%12s   started:%-10s   finished:%-10s \"%s\"", taskId, startTime.toString(), end, comment);
    }

}
