package Tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class DateSorter implements Comparator<Task> {
    @Override
    public int compare(Task firstTask, Task secondTask) {
        if (firstTask.getDateTime() == null) {
            return -2;
        }
        return firstTask.getDateTime().compareTo(secondTask.getDateTime());
    }
}

public class Taskmanager {

    private boolean listIsNullOrEmpty(List<Task> taskList) {
        return taskList == null || taskList.isEmpty();
    }

    public List<Task> createTaskList() {
        List<Task> taskList = new ArrayList<>();
        return taskList;
    }

    public void addTaskToList(Task task, List<Task> taskList) {
        taskList.add(task);
    }

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

    public List<Task> sortTasksByDate(List<Task> taskList) {
        if (listIsNullOrEmpty(taskList)) {
            return taskList;
        }
        Collections.sort(taskList, new DateSorter());
        return taskList;
    }

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