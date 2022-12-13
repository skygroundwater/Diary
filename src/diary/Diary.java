package diary;
import diary.taskBooks.ActualTaskBook;
import diary.taskBooks.DeletedTaskBook;
import java.util.Scanner;
public class Diary {
    private static void printMenu() {
        System.out.println("""
                Выберите пункт меню:
                1. Добавить задачу
                2. Удалить задачу
                3. Получить задачу на указанный день
                4. Получить список всех актуальных задач
                5. Получить список всех удалённых задач
                6. Редактировать заголовок и описание задачи
                7. Получить список задач, сгруппированных по датам
                8. Получить задачи на сегодня
                0. Выход
                """);
    }
    public static void letsStartDiary() {
        ActualTaskBook actualTaskBook = new ActualTaskBook();
        DeletedTaskBook deletedTaskBook = new DeletedTaskBook();
        printMenu();
        label:
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int menu = scanner.nextInt();
                switch (menu) {
                    case 1:
                        actualTaskBook.createNewTask();
                        break;
                    case 2:
                        deletedTaskBook.removeTaskFromActual(actualTaskBook);
                        break;
                    case 3:
                        actualTaskBook.findTasksForDay();
                        break;
                    case 4:
                        actualTaskBook.showAllActualTasks();
                        break;
                    case 5:
                        deletedTaskBook.showAllDeletedTasks();
                        break;
                    case 6:
                        actualTaskBook.refactoringTask(actualTaskBook);
                        break;
                    case 7:
                        actualTaskBook.sortActualTaskBookByTime();
                        break;
                    case 8:
                        actualTaskBook.methodForCheckActualTaskBook();
                        break;
                    case 0:
                        break label;
                }
            } else {
                break;
            }
        }
    }
    public static void main(String[] args){
        letsStartDiary();
    }
}
