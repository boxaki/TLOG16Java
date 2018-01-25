/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.excetions.EmptyTimeFieldException;
import timelogger.excetions.FutureWorkException;
import timelogger.excetions.NegativeMinutesOfWorkException;
import timelogger.excetions.NotSeparatedTimesException;

/**
 *
 * @author Akos Varga
 */
public class WorkDayTest {

    public WorkDayTest() {
    }

    @Test
    public void testGetExtraMinPerDay1() throws Exception {
        Task t = new Task("8493", "07:30", "08:45", "no comment");
        WorkDay wd = new WorkDay();
        wd.addTask(t);
        long expectedMinutes = -375;
        assertEquals(expectedMinutes, wd.getExtraMinPerDay());
    }

    @Test
    public void testGetRequiredMinPerDayWithoutAddedTask() throws Exception {
        long requiredMinutes = 412;
        WorkDay wd = new WorkDay(requiredMinutes);
        assertEquals(-requiredMinutes, wd.getExtraMinPerDay());
    }

    //3
    @Test(expected = NegativeMinutesOfWorkException.class)
    public void testSetNegativeRequiredMinPerDay() throws Exception {
        WorkDay wd = new WorkDay();
        wd.setRequiredMinPerDay(-1);
    }

    //4
    @Test(expected = NegativeMinutesOfWorkException.class)
    public void testNegativeRequiredMinPerDayInConstructor() throws Exception {
        WorkDay wd = new WorkDay(-100);
    }

    //5
    @Test(expected = FutureWorkException.class)
    public void testSetActualDayToFutureDate() throws Exception {
        WorkDay wd = new WorkDay();
        wd.setActualDay(3000, 1, 1);
    }

    //6
    @Test(expected = FutureWorkException.class)
    public void testFutureDateInConstructor() throws Exception {
        WorkDay wd = new WorkDay(3000, 1, 1);
    }

    //7
    @Test
    public void testSumPerDayForMultipleTasks() throws Exception {
        Task task1 = new Task("1111", "07:30", "08:45", "First task");
        Task task2 = new Task("2222", "08:45", "09:45", "task2");
        WorkDay wd = new WorkDay();
        wd.addTask(task1);
        wd.addTask(task2);

        long expectedSumPerDay = 135;
        assertEquals(expectedSumPerDay, wd.getSumPerDay());
    }

    //8
    @Test
    public void testSumPerDayWithoutTask() throws Exception {
        WorkDay wd = new WorkDay();
        long expectedSumPerDay = 0;
        assertEquals(expectedSumPerDay, wd.getSumPerDay());
    }

    //9
    @Test
    public void testGetEndTimeOfLatestTask() throws Exception {
        Task t1 = new Task("1234", "07:30", "08:45", "first");
        Task t2 = new Task("2345", "09:30", "11:45", "second");

        WorkDay wd = new WorkDay();
        wd.addTask(t1);
        wd.addTask(t2);

        LocalTime expectedLatestTask = LocalTime.of(11, 45);
        assertEquals(expectedLatestTask, wd.getEndTimeOfLatestTask());
    }

    //10
    @Test
    public void testGetEndTimeOfLatestTaskIfNull() throws Exception {
        WorkDay wd = new WorkDay();
        assertEquals(null, wd.getEndTimeOfLatestTask());
    }

    //11
    @Test(expected = NotSeparatedTimesException.class)
    public void testAddTaskIfTimeIntervalsNotSeparated() throws Exception {
        Task t1 = new Task("1234", "07:30", "08:45", "first task");
        Task t2 = new Task("2345", "08:30", "09:45", "first task");
        WorkDay wd = new WorkDay();
        wd.addTask(t1);
        wd.addTask(t2);
    }

    //12
    @Test
    public void testConstructorWithDateAndRequiredMin() throws Exception {
        long requiredMinPerDay = 234;
        int year = 1210;
        int month = 1;
        int day = 5;
        WorkDay wd = new WorkDay(requiredMinPerDay, year, month, day);
        assertEquals(requiredMinPerDay, wd.getRequiredMinPerDay());
        LocalDate expectedDate = LocalDate.of(year, month, day);
        assertEquals(expectedDate, wd.getActualDay());
    }

    //13
    @Test
    public void testConstructorWithDateAndDefaultRequiredMin() throws Exception {
        long expectedRequiredMinPerDay = 450;
        int year = 1210;
        int month = 1;
        int day = 5;
        WorkDay wd = new WorkDay(year, month, day);
        assertEquals(expectedRequiredMinPerDay, wd.getRequiredMinPerDay());
        LocalDate expectedDay = LocalDate.of(year, month, day);
        assertEquals(expectedDay, wd.getActualDay());
    }

    //14    
    @Test
    public void testConstructorWithRequiredMinAndDefaultDate() throws Exception {
        long requiredMinPerDay = 300;

        WorkDay wd = new WorkDay(requiredMinPerDay);

        LocalDate expectedDay = LocalDate.now();
        assertEquals(requiredMinPerDay, wd.getRequiredMinPerDay());
        assertEquals(expectedDay, wd.getActualDay());
    }

    //15
    @Test
    public void testConstructorWithDefaultValues() throws Exception {
        WorkDay wd = new WorkDay();

        long expectedRequiredMinPerDay = 450;
        LocalDate expectedDay = LocalDate.now();
        assertEquals(expectedRequiredMinPerDay, wd.getRequiredMinPerDay());
        assertEquals(expectedDay, wd.getActualDay());
    }

    //16
    @Test
    public void testSetActualDay() throws Exception {
        WorkDay wd = new WorkDay(1542, 12, 24);
        int year = 2016;
        int month = 9;
        int day = 1;
        wd.setActualDay(year, month, day);

        LocalDate expectedDate = LocalDate.of(year, month, day);
        assertEquals(expectedDate, wd.getActualDay());
    }

    //17
    @Test
    public void testSetRequiredMinutes() throws Exception {
        WorkDay wd = new WorkDay(450, 1542, 12, 24);
        long minutes = 1200;
        wd.setRequiredMinPerDay(minutes);

        assertEquals(minutes, wd.getRequiredMinPerDay());
    }

    //18
    @Test(expected = EmptyTimeFieldException.class)
    public void testSumPerDayWithMissingField() throws Exception {
        Task t = new Task("1234");
        WorkDay wd = new WorkDay();
        wd.addTask(t);
        wd.getSumPerDay(); //ki is lehet hagyni, mert az addTask is dobja
    }

    //19
    @Test(expected = NotSeparatedTimesException.class)
    public void testEndTimeRoundedToOverlap() throws Exception {
        Task t1 = new Task("1234", "08:45", "09:50", "first task");
        Task t2 = new Task("1234", "08:20", "08:45", "second task");
        WorkDay wd = new WorkDay();
        wd.addTask(t1);
        wd.addTask(t2);
    }

}
