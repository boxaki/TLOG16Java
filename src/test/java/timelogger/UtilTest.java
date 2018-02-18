package timelogger;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.exceptions.EmptyTimeFieldException;
import timelogger.exceptions.NotExpectedTimeOrderException;

/**
 *
 * @author Akos Varga
 */
public class UtilTest {

    public UtilTest() {
    }

    //1
    @Test
    public void testRoundToMultipleQuarterHour() throws Exception {
        LocalTime startTime = LocalTime.of(7, 30);
        LocalTime endTime = LocalTime.of(7, 50);
        LocalTime expectedTime = LocalTime.of(7, 45);
        assertEquals(expectedTime, Util.roundToMultipleQuarterHour(startTime, endTime));
    }

    //2
    @Test
    public void testIsMultipleQuarterHour_LocalTime_LocalTime_False() throws Exception {
        boolean expected = false;
        LocalTime startTime = LocalTime.of(7, 30);
        LocalTime endTime = LocalTime.of(7, 50);
        boolean isMultipleQuarterHour = Util.isMultipleQuarterHour(startTime, endTime);

        assertEquals(expected, isMultipleQuarterHour);
    }

    //3
    @Test
    public void testIsMultipleQuarterHour_LocalTime_LocalTime_True() throws Exception {
        boolean expected = true;
        LocalTime startTime = LocalTime.of(7, 30);
        LocalTime endTime = LocalTime.of(7, 45);
        boolean isMultipleQuarterHour = Util.isMultipleQuarterHour(startTime, endTime);

        assertEquals(expected, isMultipleQuarterHour);
    }

    //4
    @Test(expected = EmptyTimeFieldException.class)
    public void testIsMultipleQuarterHour_Null_LocalTime() throws Exception {
        LocalTime startTime = null;
        LocalTime endTime = LocalTime.of(7, 45);
        Util.isMultipleQuarterHour(startTime, endTime);
    }

    //5
    @Test(expected = NotExpectedTimeOrderException.class)
    public void testIsMultipleQuarterHour_StartTime_IsAfter_EndTime() throws Exception {
        LocalTime startTime = LocalTime.of(8, 45);
        LocalTime endTime = LocalTime.of(7, 45);
        Util.isMultipleQuarterHour(startTime, endTime);
    }

    //6
    @Test
    public void testIsSepatedTime() throws Exception {
        List<Task> existing = new ArrayList<>();
        
        Task existingTask = new Task("1234", "06:30", "06:45", "existing task");
        existing.add(existingTask);
        Task newTask = new Task("1234", "05:30", "06:30", "new task");
        assertEquals(true, Util.isSeparatedTime(newTask, existing));

        newTask = new Task("1234", "06:45", "07:00", "new task");
        assertEquals(true, Util.isSeparatedTime(newTask, existing));

        existingTask = new Task("1234", "06:30", "06:30", "existing task");
        existing.set(0, existingTask);
        newTask = new Task("1234", "05:30", "06:30", "new task");
        assertEquals(true, Util.isSeparatedTime(newTask, existing));
        
        existingTask = new Task("1234", "06:30", "07:30", "existing task");
        existing.set(0, existingTask);
        newTask = new Task("1234", "07:30", "07:30", "new task");
        assertEquals(true, Util.isSeparatedTime(newTask, existing));
        
        existingTask = new Task("1234", "06:30", "07:00", "existing task");
        existing.set(0, existingTask);
        newTask = new Task("1234", "06:00", "06:45", "new task");
        assertEquals(false, Util.isSeparatedTime(newTask, existing));
        
        newTask = new Task("1234", "06:30", "06:45", "new task");
        assertEquals(false, Util.isSeparatedTime(newTask, existing));
        
        newTask = new Task("1234", "06:45", "07:15", "new task");
        assertEquals(false, Util.isSeparatedTime(newTask, existing));
        
        newTask = new Task("1234", "06:45", "07:00", "new task");
        assertEquals(false, Util.isSeparatedTime(newTask, existing));
        
        existingTask = new Task("1234", "06:30", "06:30", "existing task");
        existing.set(0, existingTask);
        newTask = new Task("1234", "06:30", "07:00", "new task");
        assertEquals(false, Util.isSeparatedTime(newTask, existing));
        
        existingTask = new Task("1234", "06:30", "07:30", "existing task");
        existing.set(0, existingTask);
        newTask = new Task("1234", "06:30", "06:30", "new task");
        assertEquals(false, Util.isSeparatedTime(newTask, existing));    

    }
 
}
