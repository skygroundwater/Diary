package diary.taskBooks;
import diary.task.Task;
import diary.task.repeatability.*;
import diary.typeOfTask.TypesOfTask;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
public class ActualTaskBook {
    private final Map<Long, Task> actualTaskBook;
    private static final String personal = "<личная>";
    private static final String working = "<рабочая>";
    public ActualTaskBook() {
        this.actualTaskBook = new HashMap<>();
    }
    private static Scanner scanner(){
        return new Scanner(System.in);
    }
    private Task newTask(){
        try {
            try {
                Scanner scanner = scanner();
                System.out.println("Выберите тип задачи: 1." + personal + "; или по дефолту будет выбран тип " + working);
                int type = scanner.nextInt();
                TypesOfTask typesOfTask;
                if(type != 1) typesOfTask = TypesOfTask.WORKING; else typesOfTask = TypesOfTask.PERSONAL;
                System.out.println("Выберите частоту выполнения задачи: 1. ежедневно; 2. еженедельно; 3. ежемесячно; 4. ежегодично. Либо задача выполнится один раз.");
                int frequency = scanner.nextInt();
                System.out.println("Введите заголовок");
                String title = scanner().nextLine();
                System.out.println("Введите описание");
                String description = scanner().nextLine();
                System.out.println("Введите дату и время дедлайна в виде <год-месяц-день-часы-минуты>");
                LocalDateTime time = LocalDateTime.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                try {
                    if (time.isBefore(LocalDateTime.now())) throw new RuntimeException();
                }catch (RuntimeException e){
                    return null;
                }
                System.out.println("Задача была добавлена");
                if(frequency == 4) return new Yearly(title, description, typesOfTask, time);
                else if(frequency == 3) return new Monthly(title, description, typesOfTask, time);
                else if(frequency == 2) return new Weekly(title, description, typesOfTask, time);
                else if(frequency == 1) return new Daily(title,description,typesOfTask,time);
                else return new Once(title,description,typesOfTask,time);
            }catch (InputMismatchException e){
                System.out.println("Некорректно введено значение"  );
            }
        }catch (DateTimeException e){
            System.out.println("Некорректно введено время");
        }
        return null;
    }
    public void createNewTask(){
        Task task = newTask();
        if(task != null) actualTaskBook.put(task.getId(), task); else System.out.println("Задача не была добавлена. Данные были введены некорректно");
    }
    public void findTasksForDay(){
        System.out.println("Введите дату в виде <год-месяц-день>");
        try {
            try {
                Scanner scanner = scanner();
                LocalDate time = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                Map<Long, Task> tasksForDay = new HashMap<>();
                for (Map.Entry<Long, Task> pair : actualTaskBook.entrySet()) {
                    if(pair.getValue().getTime().toLocalDate().isAfter(time)) continue;
                    if (pair.getValue().getClass().equals(Daily.class)) {
                        for (int i = 0; i < 10000; i++) {
                            if (pair.getValue().getTime().toLocalDate().isEqual(pair.getValue().getTime().toLocalDate().plusDays(i))) {
                                tasksForDay.put(pair.getKey(), pair.getValue());
                            }
                        }
                    } else if (pair.getValue().getClass().equals(Weekly.class)) {
                        for (int i = 0; i < 3000; i++) {
                            if (time.isEqual(pair.getValue().getTime().toLocalDate().plusWeeks(i))) {
                                tasksForDay.put(pair.getKey(), pair.getValue());
                            }
                        }
                    } else if (pair.getValue().getClass().equals(Monthly.class)) {
                        for (int i = 0; i < 1000; i++) {
                            if (time.isEqual(pair.getValue().getTime().toLocalDate().plusMonths(i))) {
                                tasksForDay.put(pair.getKey(), pair.getValue());
                            }
                        }
                    } else if (pair.getValue().getClass().equals(Yearly.class)) {
                        for (int i = 0; i < 200; i++) {
                            if (time.isEqual(pair.getValue().getTime().toLocalDate().plusYears(i))) {
                                tasksForDay.put(pair.getKey(), pair.getValue());
                            }
                        }
                    } else if (pair.getValue().getClass().equals(Once.class)) {
                        if (time.isEqual(pair.getValue().getTime().toLocalDate())) {
                            tasksForDay.put(pair.getKey(), pair.getValue());
                        }
                    }
                }
                for (Map.Entry<Long, Task> pair : tasksForDay.entrySet()) System.out.println(pair.getValue().getInfo());
            }catch (InputMismatchException e){
                System.out.println("Вы ввели некорректное значение даты");
            }
        }catch (DateTimeException e){
            System.out.println("Вы ввели некорректное значение даты");
        }
    }
    public void showAllActualTasks(){
        for (Map.Entry<Long, Task> pair : actualTaskBook.entrySet()) System.out.println(pair);
    }
    private Task findTask(Long id){
        return actualTaskBook.get(id);
    }
    protected Task taskForDelete() {
        try {
            System.out.println("Введите id задачи, которую хотите удалить");
            return findTask(scanner().nextLong());
        } catch (InputMismatchException e) {
            System.out.println("Вы ввели некорректное значение, попробуйте снова");
        }
        return taskForDelete();
    }
    public void deleteTask(Long id){
        actualTaskBook.remove(id);
    }
    public void deleteTask2(Long id){
        for (Map.Entry<Long, Task> pair : actualTaskBook.entrySet()) {
            if(pair.getValue().getId() == id){
                pair.getValue().setDeleted(true);
                pair.getValue().setTitle("УДАЛЕНА!" + pair.getValue().getTitle());
            }
        }
    }
    public void methodForCheckActualTaskBook() {
        for (Map.Entry<Long, Task> pair : actualTaskBook.entrySet()) {
            if (pair.getValue().getTime().getMinute() == (LocalDateTime.now().getMinute())) {
                System.out.println("Пора выполнять задачу " + pair.getValue());
            } else if (pair.getValue().getTime().getDayOfYear() == LocalDateTime.now().getDayOfYear() || pair.getValue().getTime().isBefore(LocalDateTime.now())) {
                System.out.println("Задача на сегодня " + pair.getValue());
                if (pair.getValue().getClass().equals(Daily.class)) {
                    pair.getValue().setTime(pair.getValue().getTime().plusDays(1));
                } else if (pair.getValue().getClass().equals(Weekly.class)) {
                    pair.getValue().setTime(pair.getValue().getTime().plusWeeks(1));
                } else if (pair.getValue().getClass().equals(Monthly.class)) {
                    pair.getValue().setTime(pair.getValue().getTime().plusMonths(1));
                } else if (pair.getValue().getClass().equals(Yearly.class)) {
                    pair.getValue().setTime(pair.getValue().getTime().plusYears(1));
                } else actualTaskBook.remove(pair.getKey());
            }
        }
    }
    private void refactorTask(long id){
        Scanner scanner = new Scanner(System.in);
        for (Map.Entry<Long, Task> pair : actualTaskBook.entrySet()) {
            if(pair.getValue().getId() == id){
                System.out.println("вы можете изменить заголовок");
                System.out.println(pair.getValue().getTitle());
                pair.getValue().setTitle(scanner.nextLine());
                System.out.println("вы можете изменить описание");
                System.out.println(pair.getValue().getDescription());
                pair.getValue().setDescription(scanner.nextLine());
            }
        }
    }
    public void refactoringTask(ActualTaskBook actualTaskBook){
        try {
            System.out.println("Для редактирования, выберите задачу по её номеру id");
            actualTaskBook.showAllActualTasks();
            refactorTask(scanner().nextLong());
        }catch (InputMismatchException e){
            System.out.println("Вы ввели некорректное значение. Повторите");
            refactoringTask(actualTaskBook);
        }
    }
    public void sortActualTaskBookByTime(){
        Map<LocalDate, String> mapForSortedTasksByTime = new TreeMap<>();
        for (Map.Entry<Long, Task> pair : actualTaskBook.entrySet()) {
            if(!mapForSortedTasksByTime.containsKey(pair.getValue().getTime().toLocalDate())) mapForSortedTasksByTime.put(pair.getValue().getTime().toLocalDate(), pair.getValue().getInfo());
            else {
                String newValue = mapForSortedTasksByTime.get(pair.getValue().getTime().toLocalDate()) + "; " + pair.getValue().getInfo();
                mapForSortedTasksByTime.put(pair.getValue().getTime().toLocalDate(), newValue);
            }
        }
        for (Map.Entry<LocalDate, String> pair: mapForSortedTasksByTime.entrySet()) System.out.println(pair);
    }
    @Override
    public String toString() {
        return actualTaskBook.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActualTaskBook taskBook1 = (ActualTaskBook) o;
        return Objects.equals(actualTaskBook, taskBook1.actualTaskBook);
    }
    @Override
    public int hashCode() {
        return Objects.hash(actualTaskBook);
    }
}