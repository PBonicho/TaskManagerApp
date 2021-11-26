package Tasks;

import Persons.Person;

import java.time.LocalDateTime;

public class Task {
    private String description;
    private String category;
    private LocalDateTime dateTime;
    private boolean done;
    private Person person;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "discription='" + description + '\'' +
                ", category='" + category + '\'' +
                ", dateTime=" + dateTime +
                ", done=" + done +
                ", person=" + person +
                '}';
    }
}
