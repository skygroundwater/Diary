package diary.task.repeatability;
import diary.task.Task;
import diary.typeOfTask.TypesOfTask;
import java.time.LocalDateTime;
public class Once extends Task{
    private LocalDateTime time;
    private final static String exception = "Время выполнения задачи выбрано некорректно";
    private final static String once = "<одноразовая> ";
    public Once(String title, String description, TypesOfTask typesOfTask, LocalDateTime time) {
        super(title, description, typesOfTask);
        setTime(time);
    }
    public void setTime(LocalDateTime time){
        if (time.isBefore(LocalDateTime.now())) throw new RuntimeException(exception);else this.time = time;
    }
    public LocalDateTime getTime(){
        return time;
    }
    @Override
    public String toString() {
        return once + getTitle() + " " + getDescription() + " " + time;
    }
}