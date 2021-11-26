package Tasks;

import Persons.Person;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskmanagerTest {

    @Test
    public void createTaskListTest() {
        Taskmanager taskmanager = new Taskmanager();
        List<Task> taskList = taskmanager.createTaskList();
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void addTaskToListTest() {
        Taskmanager taskmanager = new Taskmanager();
        List<Task> taskList = taskmanager.createTaskList();
        Person person = new Person();
        person.setJobgroup("Software");
        person.setAge(32);
        person.setName("Max");


        Task task = new Task();
        task.setPerson(person);
        task.setDone(false);
        task.setDescription("Hof kehren");
        task.setDateTime(LocalDateTime.now());
        task.setCategory("Haushalt");

        taskmanager.addTaskToList(task, taskList);
        assertTrue(taskList.size() == 1);
    }

    @Test
    public void getTasksByCategoryHaushaltTest() {
        Taskmanager taskmanager = new Taskmanager();
        List<Task> taskList = taskmanager.createTaskList();
        Task task = new Task();
        task.setCategory("Haushalt");

        Task taskb = new Task();
        taskb.setCategory("Sport");

        Task taskc = new Task();
        taskc.setCategory("HauShalt");

        Task taskd = new Task();
        taskd.setCategory(null);

        Task taske = new Task();
        taske.setCategory("");

        Task taskf = new Task();
        taskf.setCategory("haushalt");

        taskmanager.addTaskToList(task, taskList);
        taskmanager.addTaskToList(taskb, taskList);
        taskmanager.addTaskToList(taskc, taskList);
        taskmanager.addTaskToList(taskd, taskList);
        taskmanager.addTaskToList(taske, taskList);
        taskmanager.addTaskToList(taskf, taskList);


        List<Task> taskListByCategoryHaushalt = taskmanager.getTaskListByCategory(taskList, "HAUSHALT");

        assertTrue(taskListByCategoryHaushalt.size() == 3);

        Task task1 = taskListByCategoryHaushalt.get(0);
        boolean taskHasCategoryHaushalt = task1.getCategory().equals("HAUSHALT");
        Task task2 = taskListByCategoryHaushalt.get(1);
        boolean task2HasCategoryHaushalt = task2.getCategory().equals("HAUSHALT");
        Task task3 = taskListByCategoryHaushalt.get(2);
        boolean task3HasCategoryHaushalt = task3.getCategory().equals("HAUSHALT");

        assertTrue(taskHasCategoryHaushalt);
        assertTrue(task2HasCategoryHaushalt);
        assertTrue(task3HasCategoryHaushalt);

    }

    @Test
    public void getTasksByCategorySportTest() {
        Taskmanager taskmanager = new Taskmanager();
        List<Task> taskList = taskmanager.createTaskList();
        Task task = new Task();
        task.setCategory("Haushalt");

        Task taskb = new Task();
        taskb.setCategory("Sport");

        Task taskc = new Task();
        taskc.setCategory("Haushalt");

        Task taskd = new Task();
        taskd.setCategory(null);

        Task taske = new Task();
        taske.setCategory("");

        taskmanager.addTaskToList(task, taskList);
        taskmanager.addTaskToList(taskb, taskList);
        taskmanager.addTaskToList(taskc, taskList);
        taskmanager.addTaskToList(taskd, taskList);
        taskmanager.addTaskToList(taske, taskList);

        List<Task> taskListByCategorySport = taskmanager.getTaskListByCategory(taskList, "Sport");
        assertTrue(taskListByCategorySport.size() == 1);

        Task task1 = taskListByCategorySport.get(0);
        boolean taskHasCatergorySport = task1.getCategory().equals("SPORT");
        assertTrue(taskHasCatergorySport);

    }

    @Test
    public void getTasksByDoneFalseTest() {
        Taskmanager taskmanager = new Taskmanager();
        List<Task> taskList = taskmanager.createTaskList();

        Task task = new Task();
        task.setDone(false);

        Task taskb = new Task();
        taskb.setDone(true);

        Task taskc = new Task();
        taskc.setDone(false);


        taskmanager.addTaskToList(task, taskList);
        taskmanager.addTaskToList(taskb, taskList);
        taskmanager.addTaskToList(taskc, taskList);

        List<Task> taskListByDoneFalse = taskmanager.getTaskListByDone(taskList, false);
        assertTrue(taskListByDoneFalse.size() == 2);
    }

    @Test
    public void getTasksByDoneTrueTest() {
        Taskmanager taskmanager = new Taskmanager();
        List<Task> taskList = taskmanager.createTaskList();

        Task task = new Task();
        task.setDone(false);

        Task taskb = new Task();
        taskb.setDone(true);

        Task taskc = new Task();
        taskc.setDone(false);


        taskmanager.addTaskToList(task, taskList);
        taskmanager.addTaskToList(taskb, taskList);
        taskmanager.addTaskToList(taskc, taskList);

        List<Task> taskListByDoneTrue = taskmanager.getTaskListByDone(taskList, true);
        assertTrue(taskListByDoneTrue.size() == 1);
    }


    @Test
    public void sortTasksByDateTest() {
        Taskmanager taskmanager = new Taskmanager();
        List<Task> taskList = taskmanager.createTaskList();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus3Days = LocalDateTime.now().plusDays(3L);

        Task task = new Task();
        task.setDateTime(now);

        Task taskb = new Task();
        taskb.setDateTime(nowPlus3Days);

        Task taskc = new Task();
        taskc.setDateTime(LocalDateTime.of(2021, 10, 8, 4, 53, 47, 123456789));

        Task taskd = new Task();
        taskd.setDateTime(null);

        taskmanager.addTaskToList(task, taskList);
        taskmanager.addTaskToList(taskb, taskList);
        taskmanager.addTaskToList(taskc, taskList);
        taskmanager.addTaskToList(taskd, taskList);

        List<Task> sortedTasksByDate = taskmanager.sortTasksByDate(taskList);
        assertEquals("Task dateTime is not now", now, taskList.get(0).getDateTime());
    }



    @Test
    public void getJobgroupWithMostClosedTasksTest() {
        Taskmanager taskmanager = new Taskmanager();
        List<Task> taskList = taskmanager.createTaskList();
        Person person = new Person();
        person.setJobgroup("Entwickler");

        Person personb = new Person();
        personb.setJobgroup("Sportler");

        Person personc = new Person();
        personc.setJobgroup("Bäcker");


        Task taska = new Task();
        taska.setDescription("1st Task");
        taska.setDone(true);
        taska.setPerson(personc);

        Task taskb = new Task();
        taskb.setDone(true);
        taskb.setDescription("2nd Task");
        taskb.setPerson(person);

        Task taskc = new Task();
        taskc.setDone(true);
        taskc.setDescription("3rd Task");
        taskc.setPerson(personb);

        Task taskd = new Task();
        taskd.setDone(true);
        taskd.setDescription("4th Task");
        taskd.setPerson(person);

        Task taske = new Task();
        taske.setDone(false);
        taske.setDescription("5th Task");
        taske.setPerson(personb);

        Task taskf = new Task();
        taskf.setDone(true);
        taskf.setDescription("6th Task");
        taskf.setPerson(personb);

        taskmanager.addTaskToList(taska, taskList);
        taskmanager.addTaskToList(taskb, taskList);
        taskmanager.addTaskToList(taskc, taskList);
        taskmanager.addTaskToList(taskd, taskList);
        taskmanager.addTaskToList(taske, taskList);
        taskmanager.addTaskToList(taskf, taskList);

        String JobgroupWithMostClosedTasks = taskmanager.getJobgroupWithMostClosedTasks(taskList);
        assertTrue(personb.getJobgroup() + " is not the jobgroup with the most closed tasks!", JobgroupWithMostClosedTasks.contains(personb.getJobgroup()));

    }


}