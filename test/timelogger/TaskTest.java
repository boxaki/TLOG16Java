/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.excetions.EmptyTimeFieldException;
import timelogger.excetions.InvalidTaskIdException;
import timelogger.excetions.NoTaskIdException;
import timelogger.excetions.NotExpectedTimeOrderException;

/**
 *
 * @author Akos Varga
 */
public class TaskTest {

    public TaskTest() {
    }

    @Test(expected = NotExpectedTimeOrderException.class)
    public void testTaskTimeOrder() throws Exception {
        Task t = new Task("1234", "09:00", "05:00", "test");
    }

    @Test(expected = EmptyTimeFieldException.class)
    public void testTaskNoEndTime() throws Exception {
        Task t = new Task("1234", "07:00", null, "test");
    }

    @Test
    public void testGetMinPerTask() throws Exception {
        long minutes = 75;
        Task t = new Task("1234", "07:30", "08:45", "");
        assertEquals(minutes, t.getMinPerTask());
    }

    @Test(expected = InvalidTaskIdException.class)
    public void testRedmineTaskId() throws Exception {
        Task t = new Task("154858");
    }

    @Test(expected = InvalidTaskIdException.class)
    public void testLTTaskId() throws Exception {
        Task t = new Task("LT-154858");
    }

    @Test(expected = NoTaskIdException.class)
    public void testMissingTaskId() throws Exception {
        Task t = new Task(null);
    }

    @Test
    public void testGetComment() throws Exception {
        String comment;
        Task t = new Task("1234", "03:00", "05:00", null);
    }

    @Test
    public void testRoundingInConstructor() throws Exception {
        LocalTime endTime = LocalTime.of(7, 45);
        Task t = new Task("1357", "07:30", "07:50", "abcabc");
        assertEquals(endTime, t.getEndTime());
    }

    @Test
    public void testRoundingWithStartTimeChange1() throws Exception {
        Task t = new Task("2468", "08:00", "09:00", "commment");
        String endTime = t.getEndTime().toString();
        long minutes = t.getMinPerTask();
        t.setStartTime("08:12");
        assertEquals(minutes % 15, t.getMinPerTask() % 15);
        assertNotEquals(endTime, t.getEndTime().toString());
    }

    @Test
    public void testRoundingWithStartTimeChange2() throws Exception {
        Task t = new Task("2468", 8, 0, 9, 0, "commment");
        String endTime = t.getEndTime().toString();
        long minutes = t.getMinPerTask();
        t.setStartTime(8, 12);
        assertEquals(minutes % 15, t.getMinPerTask() % 15);
        assertNotEquals(endTime, t.getEndTime().toString());
    }

    @Test
    public void testRoundingWithStartTimeChange3() throws Exception {
        Task t = new Task("2468", 8, 0, 9, 0, "commment");
        String endTime = t.getEndTime().toString();
        long minutes = t.getMinPerTask();
        LocalTime startTime = LocalTime.of(8, 12);
        t.setStartTime(startTime);
        assertEquals(minutes % 15, t.getMinPerTask() % 15);
        assertNotEquals(endTime, t.getEndTime().toString());
    }

    @Test
    public void testRoundingWithEndTime1() throws Exception {
        Task t = new Task("4365", "08:00", "09:00", "comment");
        long minutes = t.getMinPerTask();
        String endTime = "09:13";
        t.setEndTime(endTime);
        assertEquals(minutes % 15, t.getMinPerTask() % 15);
        assertNotEquals(endTime, t.getEndTime().toString());
    }

    @Test
    public void testRoundingWithEndTime2() throws Exception {
        Task t = new Task("4365", 8, 0, 9, 0, "comment");
        long minutes = t.getMinPerTask();
        int endHour = 9;
        int endMinutes = 13;
        t.setEndTime(endHour, endMinutes);
        assertEquals(minutes % 15, t.getMinPerTask() % 15);
        assertNotEquals(endMinutes, t.getEndTime().getMinute());
    }

    @Test
    public void testRoundingWithEndTime3() throws Exception {
        Task t = new Task("4365", "08:00", "09:00", "comment");
        long minutes = t.getMinPerTask();
        LocalTime endTime = LocalTime.of(9, 13);
        t.setEndTime(endTime);
        assertEquals(minutes % 15, t.getMinPerTask() % 15);
        assertNotEquals(endTime, t.getEndTime());
    }

    @Test(expected = NoTaskIdException.class)
    public void testSetTaskIdNull() throws Exception {
        Task t = new Task("1234");
        t.setTaskId(null);
    }

    @Test(expected = InvalidTaskIdException.class)
    public void testSetTaskIdInvalid() throws Exception {
        Task t = new Task("1234");
        t.setTaskId("Invalid!!!");
    }

    @Test(expected = NotExpectedTimeOrderException.class)
    public void testSetStartTime() throws Exception {
        Task t = new Task("1234", "08:00", "09:00", "");
        t.setStartTime("10:00");
    }

    @Test(expected = NotExpectedTimeOrderException.class)
    public void testSetEndTime() throws Exception {
        Task t = new Task("1234", "08:00", "09:00", "");
        t.setEndTime("07:00");
    }

    @Test(expected = EmptyTimeFieldException.class)
    public void testGetMinPerTaskNull() throws Exception {
        Task t = new Task("2345");
        t.getMinPerTask();
    }

    @Test
    public void testStartTime() throws Exception {
        Task t = new Task("1234", "07:30", "07:45", "quickie");
        LocalTime startTime = LocalTime.parse("07:00");
        t.setStartTime(startTime);
        assertEquals("07:00", t.getStartTime().toString());
    }

    @Test
    public void testEndTime() throws Exception {
        Task t = new Task("1234", "07:30", "07:45", "quickie");
        LocalTime endTime = LocalTime.parse("08:00");
        t.setEndTime(endTime);
        assertEquals("08:00", t.getEndTime().toString());

    }
    
    @Test
    public void testTask() throws Exception{
        String taskId = "1234";
        String startTime = "06:11";
        String endTime = "07:54";
        String comment = "abc";
        Task t = new  Task(taskId, startTime, endTime, comment);
        assertEquals(taskId, t.getTaskId());
        assertEquals(LocalTime.parse(startTime), t.getStartTime() );
        assertNotEquals(endTime, t.getEndTime());
        assertEquals(0, t.getMinPerTask() % 15);
        assertEquals(comment, t.getComment());
    }
            

}
