package diary.taskBooks;
import diary.task.Task;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class DeletedTaskBook {
    private final Map<Long, Task> deletedTaskBook;
    public DeletedTaskBook() {
        this.deletedTaskBook = new HashMap<>();
    }
    public void removeTaskFromActual(ActualTaskBook actualTaskBook){
        try {
            System.out.println(actualTaskBook);
            Task task = actualTaskBook.taskForDelete();
            task.setTitle("УДАЛЕНА! " + task.getTitle());
            deletedTaskBook.put(task.getId(), task);
            actualTaskBook.deleteTask(task.getId());
            System.out.println("Задача удалена");
        }catch (NullPointerException e){
            System.out.println("Вы выбрали несуществующий id");
        }
    }
    public void showAllDeletedTasks(){
        for (Map.Entry<Long, Task> pair : deletedTaskBook.entrySet()) System.out.println(pair);
    }
    @Override
    public String toString() {
        return deletedTaskBook.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeletedTaskBook deletedTaskBook1 = (DeletedTaskBook) o;
        return Objects.equals(deletedTaskBook, deletedTaskBook1.deletedTaskBook);
    }
    @Override
    public int hashCode() {
        return Objects.hash(deletedTaskBook);
    }
}