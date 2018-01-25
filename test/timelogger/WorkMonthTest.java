/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.excetions.EmptyTimeFieldException;
import timelogger.excetions.NotNewDateException;
import timelogger.excetions.NotTheSameMonthException;
import timelogger.excetions.WeekendNotEnabledException;

/**
 *
 * @author Akos Varga
 */
public class WorkMonthTest {

    public WorkMonthTest() {
    }

    //1
    @Test
    public void testGetSumPerMonth() throws Exception {
        Task t1 = new Task("1234", "07:30", "08:45", "first task");
        WorkDay wd1 = new WorkDay(420);
        wd1.addTask(t1);

        Task t2 = new Task("1244", "08:45", "09:45", "second task");
        WorkDay wd2 = new WorkDay(420, 2018, 1, 11);
        wd2.addTask(t2);

        WorkMonth wm = new WorkMonth(2018, 1);
        wm.addWorkDay(wd1);
        wm.addWorkDay(wd2);
        long expectedMinutes = 135;
        assertEquals(expectedMinutes, wm.getSumPerMonth());
    }

    //2
    @Test
    public void testGetSumPerMonthWithoutDay() throws Exception {
        long expectedSum = 0;
        WorkMonth wm = new WorkMonth(1700, 1);
        assertEquals(expectedSum, wm.getSumPerMonth());
    }

    //3
    @Test
    public void testGetExtraMinPerMonth() throws Exception {
        Task t1 = new Task("1234", "07:30", "08:45", "first task");
        WorkDay wd1 = new WorkDay(420);
        wd1.addTask(t1);

        Task t2 = new Task("1244", "08:45", "09:45", "second task");
        WorkDay wd2 = new WorkDay(420, 2018, 1, 11);
        wd2.addTask(t2);

        WorkMonth wm = new WorkMonth(2018, 1);
        wm.addWorkDay(wd1);
        wm.addWorkDay(wd2);
        long expectedMinutes = -705;
        assertEquals(expectedMinutes, wm.getExtraMinPerMonth());
    }

    //4
    @Test
    public void testGetExtraMinPerMonthWithoutDay() throws Exception {
        long expectedSum = 0;
        WorkMonth wm = new WorkMonth(1700, 1);
        assertEquals(expectedSum, wm.getExtraMinPerMonth());
    }

    //5
    @Test
    public void testGetRequiredMinPerMonth() throws Exception {
        Task t1 = new Task("1234", "07:30", "08:45", "first task");
        WorkDay wd1 = new WorkDay(420);
        wd1.addTask(t1);

        Task t2 = new Task("1244", "08:45", "09:45", "second task");
        WorkDay wd2 = new WorkDay(420, 2018, 1, 11);
        wd2.addTask(t2);

        WorkMonth wm = new WorkMonth(2018, 1);
        wm.addWorkDay(wd1);
        wm.addWorkDay(wd2);
        long expectedMinutes = 840;
        assertEquals(expectedMinutes, wm.getRequiredMinPerMonth());
    }

    //6
    @Test
    public void testGetRequiredMinPerMonthWithoutDay() throws Exception {
        long expectedMinutes = 0;
        WorkMonth wm = new WorkMonth(1700, 1);
        assertEquals(expectedMinutes, wm.getRequiredMinPerMonth());
    }

    //7
    @Test
    public void testSumPerMonthAndSumPerDayWithOneTask() throws Exception {
        Task t1 = new Task("1234", "07:30", "08:45", "first task");
        WorkDay wd = new WorkDay(420, 2018, 1, 10);
        wd.addTask(t1);
        WorkMonth wm = new WorkMonth(2018, 1);
        wm.addWorkDay(wd);

        assertEquals(wd.getSumPerDay(), wm.getSumPerMonth());
    }

    //8
    @Test
    public void testGetRequiredMinPerMonth_() throws Exception {
        Task t1 = new Task("1234", "07:30", "08:45", "first task");
        WorkDay wd = new WorkDay(420, 2018, 1, 14);
        wd.addTask(t1);
        WorkMonth wm = new WorkMonth(2018, 1);
        wm.addWorkDay(wd, true);

        assertEquals(wd.getSumPerDay(), wm.getSumPerMonth());
    }

    //9
    @Test(expected = WeekendNotEnabledException.class)
    public void testAddWeekendDayAndWeekendDisabled() throws Exception {
        Task t1 = new Task("1234", "07:30", "08:45", "first task");
        WorkDay wd = new WorkDay(420, 2018, 1, 14);
        wd.addTask(t1);
        WorkMonth wm = new WorkMonth(2018, 1);
        wm.addWorkDay(wd, false);
    }

    //10
    @Test(expected = NotNewDateException.class)
    public void testAddWorkDaysWithSameDate() throws Exception {
        WorkDay wd1 = new WorkDay(2016, 9, 1);
        WorkDay wd2 = new WorkDay(2016, 9, 1);

        WorkMonth wm = new WorkMonth(2016, 9);
        wm.addWorkDay(wd1);
        wm.addWorkDay(wd2);
    }

    //11
    @Test(expected = NotTheSameMonthException.class)
    public void testAddWorkDayOnDifferentMonth() throws Exception {
        WorkDay wd1 = new WorkDay(2016, 9, 1);
        WorkDay wd2 = new WorkDay(2016, 8, 30);

        WorkMonth wm = new WorkMonth(2016, 9);
        wm.addWorkDay(wd1);
        wm.addWorkDay(wd2);
    }

    //12 
    @Test(expected = EmptyTimeFieldException.class)
    public void testGetSumPerMonthOnEmptyField() throws Exception{
        Task t = new Task("1234");
        WorkDay wd = new WorkDay(2018, 1, 10);
        WorkMonth wm = new WorkMonth(2018, 1);
        wd.addTask(t);          
        wm.addWorkDay(wd);
        wm.getSumPerMonth();
        
    }
    
    
    //13
    @Test(expected = EmptyTimeFieldException.class)
    public void testGetExtraMinPerMonthOnEmptyField() throws Exception{
        Task t = new Task("1234");
        WorkDay wd = new WorkDay(2018, 1, 10);
        WorkMonth wm = new WorkMonth(2018, 1);
        wd.addTask(t);
        wm.addWorkDay(wd);      
        wm.getSumPerMonth();
    }
    
}
