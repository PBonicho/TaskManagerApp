import Persons.Person;
import Tasks.Task;
import Tasks.Taskmanager;

import java.time.LocalDateTime;
import java.util.List;

public class Programm {
    public static void main(String[] args) {
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


        Person person2 = new Person();
        person2.setJobgroup("Nachwuchskraft");
        person2.setAge(27);
        person2.setName("Pascal");

        Task task2 = new Task();
        task2.setPerson(person2);
        task2.setDone(false);
        task2.setDescription("Spühlmaschine ausräumen");
        task2.setDateTime(LocalDateTime.now());
        task2.setCategory(null);

        taskmanager.addTaskToList(task, taskList);
        taskmanager.addTaskToList(task2, taskList);

        taskmanager.getTaskListByCategory(taskList, task.getCategory());
        taskmanager.getTaskListByDone(taskList, task.isDone());
        taskmanager.sortTasksByDate(taskList);
        taskmanager.getJobgroupWithMostClosedTasks(taskList);




        System.out.println(taskList);
        System.out.println(taskmanager.getTaskListByCategory(taskList, task.getCategory()));
        System.out.println(taskmanager.getTaskListByDone(taskList, true));
    }
}
