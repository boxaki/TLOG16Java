package timelogger;

import java.time.*;
import java.util.*;
import timelogger.excetions.*;

/**
 * Time logger program that helps keep record of daily tasks, and makes some
 * basic calculations.
 *
 *
 * @author Akos Varga
 * @version 0.5.0
 */
public class TimeLoggerUI {

    private static final int INVALID_INPUT = -1;
    private static final float DEFAULT_WORK_HOURS = 7.5f;
    private static final int MIN_ACCEPTED_YEAR = 2000;
    private static final int MAX_ACCEPTED_YEAR = LocalDate.now().getYear();
    private static final String STATISTICS_OUTPUT_FORMAT = "%18s %18s %18s %18s\n";
    private static TimeLogger timelogger;

    /**
     * @param args unused
     */
    public static void main(String[] args) {
        init();

        int choice;
        do {
            printMainMenu();
            choice = getIntInput(0, 10, ":");
            if (choice != INVALID_INPUT) {
                selectOption(choice);
            }
        } while (choice != 0);
    }

    private static void init() {
        timelogger = new TimeLogger();
    }

    private static void printMainMenu() {
        System.out.println("0. Exit");
        System.out.println("1. List months");
        System.out.println("2. List days");
        System.out.println("3. List tasks for a specific day");
        System.out.println("4. Add new month");
        System.out.println("5. Add day to a specific month");
        System.out.println("6. Start a task for a day");
        System.out.println("7. Finish a specific task");
        System.out.println("8. Delete a task");
        System.out.println("9. Modify task");
        System.out.println("10. Statistics");

    }

    private static void selectOption(int choice) {

        switch (choice) {
            case 0:
                System.out.println("See you later boss!");
                System.exit(0);
            case 1:
                if (listMonths() > 0) {
                    pressEnterToContinue();
                }
                break;
            case 2:
                if (listDays(selectMonth()) > 0) {
                    pressEnterToContinue();
                }
                break;
            case 3:
                if (listTasks(selectDay(selectMonth())) > 0) {
                    pressEnterToContinue();
                }
                break;
            case 4:
                addMonth();
                break;
            case 5:
                addDayToMonth(selectMonth());
                break;
            case 6:
                startTask(selectDay(selectMonth()));
                break;
            case 7:
                finishTask(selectDay(selectMonth()));
                break;
            case 8:
                deleteTask(selectDay(selectMonth()));
                break;
            case 9:
                modifyTaskOrFindError(selectDay(selectMonth()));
                break;
            case 10:
                printStatistics(selectMonth());
                break;

        }
    }

    private static int listMonths() {
        List<WorkMonth> allMonths = timelogger.getMonths();
        if (allMonths.isEmpty()) {
            System.out.println("No months have been added yet.");
            return 0;
        }

        int counter = 1;
        for (WorkMonth month : allMonths) {
            System.out.println(counter++ + ". " + month.getDate());
        }

        return allMonths.size();
    }

    private static int listDays(WorkMonth selectedMonth) {
        if (selectedMonth == null) {
            return 0;
        }

        List<WorkDay> days = selectedMonth.getDays();
        if (days.isEmpty()) {
            System.out.println("No days have been added yet.");
            return 0;
        }

        int index = 1;
        for (WorkDay wd : days) {
            System.out.println(index++ + ". " + wd.getActualDay().toString());
        }

        return days.size();
    }

    private static int listTasks(WorkDay selectedDay) {

        if (selectedDay == null) {
            return 0;
        }

        List<Task> tasks = selectedDay.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No task for the day");
        }

        int index = 1;
        for (Task t : tasks) {
            System.out.println(index + ". " + t);
            index++;
        }
        return tasks.size();

    }

    private static void addMonth() {
        try {
            addNewMonth();
        } catch (NotNewMonthException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void addNewMonth() throws NotNewMonthException {
        int year = getValidIntInput(MIN_ACCEPTED_YEAR, MAX_ACCEPTED_YEAR, "year: ");
        int month = getValidIntInput(1, 12, "month: ");
        timelogger.addNewMonth(new WorkMonth(year, month));
    }

    private static void addDayToMonth(WorkMonth selectedMonth) {
        try {
            addNewDayToMonth(selectedMonth);
        } catch (NegativeMinutesOfWorkException | NotTheSameMonthException | NotNewDateException | WeekendNotEnabledException | FutureWorkException ex) {
            System.out.println("Could not add day!");
            System.out.println(ex.getMessage());
        }
    }

    private static void addNewDayToMonth(WorkMonth selectedMonth) throws NegativeMinutesOfWorkException, FutureWorkException, NotNewDateException, WeekendNotEnabledException, NotTheSameMonthException {

        if (selectedMonth != null) {
            int day = getValidIntInput(1, selectedMonth.getDate().lengthOfMonth(), "day: ");
            WorkDay newDay = new WorkDay(selectedMonth.getDate().getYear(), selectedMonth.getDate().getMonthValue(), day);
            selectedMonth.addWorkDay(newDay);

            int minutes = getValidHrToMin();
            newDay.setRequiredMinPerDay(minutes);
        }
    }

    private static void startTask(WorkDay selectedDay) {
        try {
            startNewTask(selectedDay);
        } catch (NotExpectedTimeOrderException | EmptyTimeFieldException | NotSeparatedTimesException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void startNewTask(WorkDay selectedDay) throws NotExpectedTimeOrderException, EmptyTimeFieldException, NotSeparatedTimesException {
        if (selectedDay == null) {
            return;
        }

        String taskId;
        Task newTask;
        while (true) {
            taskId = getStringInput("task id: ");
            try {
                newTask = new Task(taskId);
            } catch (InvalidTaskIdException | NoTaskIdException ex) {
                System.out.println("Task id consists of four digits or the characters \"LT-\" plus four digits.");
                continue;
            }
            break;
        }

        String comment = getStringInput("task description: ");
        newTask.setComment(comment);

        LocalTime latestTaskFinishedAt = selectedDay.getEndTimeOfLatestTask();
        String startTime = latestTaskFinishedAt != null ? latestTaskFinishedAt.toString() : "";
        startTime = getValidTimeFormat("start time" + (!startTime.isEmpty() ? "[" + startTime + "]: " : ": "), startTime);

        newTask.setStartTime(startTime);
        newTask.setEndTime(startTime);
        selectedDay.addTask(newTask);
    }

    private static void finishTask(WorkDay selectedDay) {
        try {
            finishNewTask(selectedDay);
        } catch (NotExpectedTimeOrderException | EmptyTimeFieldException | NotSeparatedTimesException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void finishNewTask(WorkDay selectedDay) throws NotExpectedTimeOrderException, EmptyTimeFieldException, NotSeparatedTimesException {
        if (selectedDay == null) {
            return;
        }
        Task selectedTask = selectUnfinishedTask(selectedDay);
        if (selectedTask == null) {
            return;
        }

        String endTime = getValidTimeFormat("finish time(HH:MM): ");
        selectedTask.setEndTime(endTime);

        selectedDay.getTasks().remove(selectedTask);
        if (Util.isSeparatedTime(selectedTask, selectedDay.getTasks())) {
            selectedDay.addTask(selectedTask);
        } else {
            System.out.println("Could not finish task! It would be in the time interval of an other task.");
            selectedTask.setEndTime(selectedTask.getStartTime());
            selectedDay.addTask(selectedTask);
        }

    }

    private static void deleteTask(WorkDay selectedDay) {
        if (selectedDay == null) {
            return;
        }
        Task selectedTask = selectTask(selectedDay);
        if (selectedTask == null) {
            return;
        }

        String confirmation;
        while (true) {
            confirmation = getStringInput("Do you really want to delete " + selectedTask + " [yes/no]: ");
            if ("yes".equals(confirmation)) {
                selectedDay.getTasks().remove(selectedTask);
                break;
            }
            if ("no".equals(confirmation)) {
                break;
            }
        }
    }

    private static void modifyTaskOrFindError(WorkDay selectedDay) {
        try {
            modifyTask(selectedDay);
        } catch (InvalidTaskIdException | NotExpectedTimeOrderException | EmptyTimeFieldException | NoTaskIdException | NotSeparatedTimesException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void modifyTask(WorkDay selectedDay) throws InvalidTaskIdException, NotExpectedTimeOrderException, EmptyTimeFieldException, NoTaskIdException, NotSeparatedTimesException {

        Task selectedTask = selectTask(selectedDay);
        if (selectedTask == null) {
            return;
        }
        if (selectedTask.getStartTime().equals(selectedTask.getEndTime())) {
            System.out.println("You cannot modify unfinished tasks!");
            return;
        }

        String taskId = selectedTask.getTaskId();
        selectedTask.setTaskId(getStringInput("new task id[" + taskId + "]: ", taskId));

        String comment = selectedTask.getComment();
        selectedTask.setComment(getStringInput("task description[" + comment + "]: ", comment));

        String start = selectedTask.getStartTime().toString();
        LocalTime newStartTime = LocalTime.parse(getValidTimeFormat("start time[" + start + "]: ", start));

        String end = selectedTask.getEndTime().toString();
        LocalTime newEndTime = LocalTime.parse(getValidTimeFormat("end time[" + end + "]: ", end));

        if (!newStartTime.equals(selectedTask.getStartTime()) || !newEndTime.equals(selectedTask.getEndTime())) {
            Task newTask = new Task(taskId, newStartTime.toString(), newEndTime.toString(), comment);

            selectedDay.getTasks().remove(selectedTask);

            if (Util.isSeparatedTime(newTask, selectedDay.getTasks())) {
                selectedDay.addTask(newTask);
            } else {
                System.out.println("Could not modify times! ");
                selectedDay.addTask(selectedTask);
            }

        }
    }

    private static void printStatistics(WorkMonth selectedMonth) {
        try {
            printStatisticsForDaysAndMonthOf(selectedMonth);
        } catch (EmptyTimeFieldException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void printStatisticsForDaysAndMonthOf(WorkMonth selectedMonth) throws EmptyTimeFieldException {
        if (selectedMonth == null) {
            return;
        }
        List<WorkDay> daysOfMonth = selectedMonth.getDays();
        if (daysOfMonth.isEmpty()) {
            System.out.println("No days have been added yet");
            return;
        }

        System.out.printf(STATISTICS_OUTPUT_FORMAT, "month", "required minutes", "minutes done", "extra minutes");
        System.out.printf(STATISTICS_OUTPUT_FORMAT, selectedMonth.getDate(), selectedMonth.getRequiredMinPerMonth(), selectedMonth.getSumPerMonth(), selectedMonth.getExtraMinPerMonth());

        System.out.printf(STATISTICS_OUTPUT_FORMAT, "day", "required minutes", "minutes done", "extra minutes");
        for (WorkDay day : daysOfMonth) {
            System.out.printf(STATISTICS_OUTPUT_FORMAT, day.getActualDay(), day.getRequiredMinPerDay(), day.getSumPerDay(), day.getExtraMinPerDay());
        }
    }

    private static String getStringInput(String textToDisplay) {
        return getStringInput(textToDisplay, "");
    }

    private static String getStringInput(String textToDisplay, String defaultValue) {

        System.out.print(textToDisplay);
        Scanner in = new Scanner(System.in);
        String input = in.nextLine().trim();

        if (!defaultValue.isEmpty() && input.isEmpty()) {
            input = defaultValue;
        }
        return input;
    }

    private static int getIntInput(int minValue, int maxValue, String inputText) {
        return getIntInput(minValue, maxValue, inputText, 0);
    }

    private static int getIntInput(int minValue, int maxValue, String inputText, int defaultValue) {

        String number = getStringInput(inputText);
        if (defaultValue > 0 && number.isEmpty()) {
            return defaultValue;
        }
        if (number.matches("\\d{1,4}")) {
            int choice = Integer.parseInt(number);
            if (choice >= minValue && choice <= maxValue) {
                return choice;
            }
        }
        return INVALID_INPUT;
    }

    private static int getValidIntInput(int minValue, int maxValue, String inputText) {
        int choice = INVALID_INPUT;
        do {
            choice = getIntInput(minValue, maxValue, inputText);

        } while (choice == INVALID_INPUT);

        return choice;
    }

    private static String getValidTimeFormat(String textToDisplay) {
        return getValidTimeFormat(textToDisplay, "");
    }

    private static String getValidTimeFormat(String textToDisplay, String defaultValue) {

        String time;
        do {
            time = getStringInput(textToDisplay, defaultValue);

        } while (!isValidTimeFormat(time));
        return time;
    }

    private static boolean isValidTimeFormat(String time) {

        return time.matches("([01]\\d|(2[0-3])):[0-5]\\d");
    }

    private static int getValidHrToMin() {

        String input;
        do {
            input = getStringInput("Required working hours [default 7.5]:", "" + DEFAULT_WORK_HOURS);

        } while (!input.matches("1{0,1}\\d(.\\d{1,2}){0,1}"));

        return (int) (Float.parseFloat(input) * 60);
    }

    private static WorkMonth selectMonth() {

        int numberOfMonths = listMonths();
        if (numberOfMonths == 0) {
            return null;
        }

        int selected = getValidIntInput(1, numberOfMonths, "Select a month: ") - 1;
        return timelogger.getMonths().get(selected);
    }

    private static WorkDay selectDay(WorkMonth selectedMonth) {

        int numberOfDays = listDays(selectedMonth);
        if (numberOfDays == 0) {
            return null;
        }

        int selected = getValidIntInput(1, numberOfDays, "select a day: ") - 1;
        return selectedMonth.getDays().get(selected);
    }

    private static Task selectTask(WorkDay selectedDay) {

        int numberOfTasks = listTasks(selectedDay);
        if (numberOfTasks == 0) {
            return null;
        }

        int selected = getValidIntInput(1, numberOfTasks, "select a task: ") - 1;
        return selectedDay.getTasks().get(selected);
    }

    private static Task selectUnfinishedTask(WorkDay selectedDay) throws EmptyTimeFieldException {

        List<Task> unfinishedTasks = getUnfinishedTasksOn(selectedDay);
        if (unfinishedTasks.isEmpty()) {
            System.out.println("No task has been added yet!");
            return null;
        }
        listUnfinishedTasks(unfinishedTasks);

        int selected = getIntInput(1, unfinishedTasks.size(), "Select task: ") - 1;
        return unfinishedTasks.get(selected);
    }

    private static List<Task> getUnfinishedTasksOn(WorkDay selectedDay) throws EmptyTimeFieldException {
        List<Task> unfinishedTasks = new ArrayList<>();
        List<Task> allTasksForTheDay = selectedDay.getTasks();
        for (Task t : allTasksForTheDay) {
            if (t.getMinPerTask() == 0) {
                unfinishedTasks.add(t);
            }
        }
        return unfinishedTasks;
    }

    private static void listUnfinishedTasks(List<Task> unfinishedTasks) {
        int index = 1;
        for (Task t : unfinishedTasks) {
            System.out.println(index++ + ". " + t.toString());
        }

    }

    private static void pressEnterToContinue() {
        Scanner in = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }

}
