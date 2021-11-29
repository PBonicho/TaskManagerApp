package Tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class contains an override of the sort methode compare if the firstTask.getDateTime == null
 * and put it at the bottom of the list.
 *
 * @return DateTime
 */

class DateSorter implements Comparator<Task> {
    @Override
    public int compare(Task firstTask, Task secondTask) {
        if (firstTask.getDateTime() == null) {
            return -2;
        }
        return firstTask.getDateTime().compareTo(secondTask.getDateTime());
    }
}

/**
 * This class contains methods to create, add, sort, and filter tasks in the list.
 *
 * @return DateTime
 */
public class Taskmanager {

    private boolean listIsNullOrEmpty(List<Task> taskList) {
        return taskList == null || taskList.isEmpty();
    }

    /**
     * This methode give the functionality to create the tasklist.
     *
     * @return List of Tasks tasklist
     */
    public List<Task> createTaskList() {
        List<Task> taskList = new ArrayList<>();
        return taskList;
    }

    /**
     * This methode give the functionality to add a task to the tasklist.
     *
     * @param task
     * @param taskList
     * @return List of Tasks tasklist
     */
    public void addTaskToList(Task task, List<Task> taskList) {
        taskList.add(task);
    }

    /**
     * This methode give the functionality to filter the taskList by a category.
     *
     * @param taskList
     * @param category
     * @return List of Tasks resultList
     */
    public List<Task> getTaskListByCategory(List<Task> taskList, String category) {
        List<Task> resultList = createTaskList();
        if (listIsNullOrEmpty(taskList)) {
            return resultList;
        }
        taskList.forEach(task -> {
            if (task.getCategory() == null || task.getCategory().isEmpty()) {
                task.setCategory("Nicht angegeben");
            }
            String upperCaseCategory = task.getCategory().toUpperCase();
            task.setCategory(upperCaseCategory);

            if (task.getCategory().equals(category)) {
                addTaskToList(task, resultList);
            }
        });
        return resultList;
    }
    /**
     * This methode give the functionality to filter the taskList by done (closed or open task).
     *
     * @param taskList
     * @param done
     * @return List of Tasks resultList
     */
    public List<Task> getTaskListByDone(List<Task> taskList, boolean done) {
        List<Task> resultList = createTaskList();

        if (listIsNullOrEmpty(taskList)) {
            return resultList;
        }

        taskList.forEach(task -> {
            if (task.isDone() == (done)) {
                addTaskToList(task, resultList);
            }
        });
        return resultList;
    }
    /**
     * This methode give the functionality to sort tasks by date.
     *
     * @param taskList
     * @return List of Tasks tasklist
     */
    public List<Task> sortTasksByDate(List<Task> taskList) {
        if (listIsNullOrEmpty(taskList)) {
            return taskList;
        }
        Collections.sort(taskList, new DateSorter());
        return taskList;
    }
    /**
     * This methode give the functionality to filter the taskList by a jobgroup, which has closed the most tasks.
     *
     * @param taskList
     * @return String JobGroupWithMostClosedTasks
     */
    public String getJobgroupWithMostClosedTasks(List<Task> taskList) {

        List<String> allJobGroupsWithTaskDone = getAllJobGroupsWithTaskDone(taskList);

        List<ComparedJobGroupByCount> comparedJobGroupByCountListSorted = getComparedJobGroupByCountsSortedReverse(allJobGroupsWithTaskDone);

        String JobGroupWithMostClosedTasks = comparedJobGroupByCountListSorted.get(0).getComparedJobGroupByHighestCount();
        return JobGroupWithMostClosedTasks;
    }

    private void setComparedJobGroupByHighestCount(List<ComparedJobGroupByCount> comparedJobGroupByCountList) {
        for (int i = 0; i < comparedJobGroupByCountList.size() - 1; i++) {
            if (comparedJobGroupByCountList.get(i).getJobGroupCounter() > comparedJobGroupByCountList.get(i + 1).getJobGroupCounter()) {
                comparedJobGroupByCountList.get(i).setComparedJobGroupByHighestCount(comparedJobGroupByCountList.get(i).getJobGroup());
            }
            if (comparedJobGroupByCountList.get(i).getJobGroupCounter() == comparedJobGroupByCountList.get(i + 1).getJobGroupCounter()) {
                comparedJobGroupByCountList.get(i).setComparedJobGroupByHighestCount(comparedJobGroupByCountList.get(i).getJobGroup() + " & " + comparedJobGroupByCountList.get(i + 1).getJobGroup());
            } else {
                comparedJobGroupByCountList.get(i).setComparedJobGroupByHighestCount(comparedJobGroupByCountList.get(i).getJobGroup());
            }
        }
    }

    private List<ComparedJobGroupByCount> getComparedJobGroupByCountsSortedReverse(List<String> allJobGroupsWithTaskDone) {
        List<ComparedJobGroupByCount> comparedJobGroupByCountList = new ArrayList<>();
        allJobGroupsWithTaskDone.forEach(jobGroup -> {
            ComparedJobGroupByCount comparedJobGroupByCount = new ComparedJobGroupByCount();
            comparedJobGroupByCount.setJobGroup(jobGroup);
            comparedJobGroupByCount.setJobGroupCounter(Collections.frequency(allJobGroupsWithTaskDone, jobGroup));
            comparedJobGroupByCountList.add(comparedJobGroupByCount);
        });
        setComparedJobGroupByHighestCount(comparedJobGroupByCountList);
        comparedJobGroupByCountList.sort(Comparator.comparing(ComparedJobGroupByCount::getJobGroupCounter).reversed());
        return comparedJobGroupByCountList;
    }

    private List<String> getAllJobGroupsWithTaskDone(List<Task> taskList) {
        List<DoneResult> doneResultList = new ArrayList<>();
        taskList.forEach(task -> {
            DoneResult doneResult = getDoneResult(task);
            if (doneResult.isDone()) {
                doneResultList.add(doneResult);
            }
        });

        List<String> allJobGroupList = new ArrayList<>();
        doneResultList.forEach(doneResult -> {
            String jobGroup = doneResult.getJobGroup();
            allJobGroupList.add(jobGroup);
        });
        return allJobGroupList;
    }

    private void addAllJobGroupsWithTaskDone(List<DoneResult> doneResultList, List<String> allJobGroupList) {
        doneResultList.forEach(doneResult -> {
            String jobGroup = doneResult.getJobGroup();
            allJobGroupList.add(jobGroup);
        });
    }


    private DoneResult getDoneResult(Task task) {
        DoneResult doneResult = new DoneResult();
        doneResult.setJobGroup(task.getPerson().getJobgroup());
        doneResult.setDone(task.isDone());
        return doneResult;
    }
}