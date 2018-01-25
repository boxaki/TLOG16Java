/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogger;

import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.excetions.NotNewMonthException;

/**
 *
 * @author Akos Varga
 */
public class TimeLoggerTest {

    public TimeLoggerTest() {
    }

    //1
    @Test
    public void testOneTaskForMonths() throws Exception {
        WorkDay wd = new WorkDay(2016, 4, 14);
        WorkMonth wm = new WorkMonth(2016, 4);
        Task t = new Task("1234", "07:30", "10:30", "task");

        wd.addTask(t);
        wm.addWorkDay(wd);
        TimeLogger tl = new TimeLogger();
        tl.addNewMonth(wm);
        assertEquals(t.getMinPerTask(), tl.getMonths().get(0).getSumPerMonth());
    }

    //2
    @Test(expected = NotNewMonthException.class)
    public void testAddNotNewMonth() throws Exception{
        WorkMonth wm1 = new WorkMonth(2016, 4);
        WorkMonth wm2 = new WorkMonth(2016, 4);
        TimeLogger tl = new TimeLogger();
        tl.addNewMonth(wm1);
        tl.addNewMonth(wm2);
    }

}
