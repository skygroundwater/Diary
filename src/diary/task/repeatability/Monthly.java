package diary.task.repeatability;
import diary.task.Task;
import diary.typeOfTask.TypesOfTask;
import java.time.LocalDateTime;
public class Monthly extends Task implements CommonMethods{
    private LocalDateTime time;
    private final static String monthly = "<ежемесячная> ";
    private final static String exception = "Время выполнения задачи выбрано некорректно";
    public Monthly(String title, String description, TypesOfTask typesOfTask, LocalDateTime time) {
        super(title, description, typesOfTask);
        setTime(time);
    }
    public void setTime(LocalDateTime time){
        if (time.isBefore(LocalDateTime.now())) throw new RuntimeException(exception);else this.time = time;
    }
    @Override
    public LocalDateTime getNextTime() {
        return time.plusMonths(1);
    }
    @Override
    public LocalDateTime getTime(){
        return time;
    }
    @Override
    public String toString() {
        return monthly  + getTitle() + " " + getDescription() + " " + time;
    }
}