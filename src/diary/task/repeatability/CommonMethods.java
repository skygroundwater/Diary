package diary.task.repeatability;
import java.time.LocalDateTime;
public interface CommonMethods {
    LocalDateTime getNextTime();
    LocalDateTime getTime();
    void setTime(LocalDateTime time);
}