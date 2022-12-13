package diary.task;
import diary.typeOfTask.TypesOfTask;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;
public abstract class Task{
    private String title;
    private String description;
    private final int id;
    private static int count = 1;
    private LocalDateTime time;
    private TypesOfTask personalOrWorking;
    private boolean deleted;
    private static final String personal = "<личная>";
    private static final String working = "<рабочая>";
    public Task(String title, String description, TypesOfTask typesOfTasks){
        setTitle(title);
        setDescription(description);
        this.id = count++;
        personalOrWorking = typesOfTasks;
        setDeleted(false);
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public String getInfo(){
        return getTitle() + " " + getDescription();
    }
    public long getId(){
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getTime(){
        return time;
    }
    public TypesOfTask getPersonalOrWorking(){
        return personalOrWorking;
    }
    public void setPersonalOrWorking(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите новый тип задачи: 1." + personal + "; или по дефолту будет выбран тип " + working);
        int type = scanner.nextInt();
        if(type != 1) personalOrWorking = TypesOfTask.WORKING; else personalOrWorking = TypesOfTask.PERSONAL;
    }
    public void setTime(LocalDateTime time){
        this.time = time;
    }
    public void setDescription(String description) {
        if(description == null || description.isEmpty()) this.description = "Описание"; else this.description = description;
    }
    public void setTitle(String title) {
        if(title == null || title.isEmpty()) this.title = "Заголовок"; else this.title = title;
    }
    @Override
    public String toString() {
        return title + " " + description + " " + personalOrWorking;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(deleted, task.deleted);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, deleted);
    }
}