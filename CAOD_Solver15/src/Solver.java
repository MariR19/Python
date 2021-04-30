import java.util.*;


// Класс решает пятнашки
public class Solver {

    private final List<Board> result = new ArrayList<>();  // лист - цепочка ходов, приводящих к решению задачи
    private final Board board;

    // Встоенный класс хранит цепочку ходов, которые привели к текущей позицие (связанный список)
    private static class ITEM {

        private final ITEM prevBoard;  // ссылка на предыдущий
        private final Board board;   // сама позиция

        private ITEM(ITEM prevBoard, Board board) {  // конструктор класса ITEM
            this.prevBoard = prevBoard;
            this.board = board;
        }

        public Board getBoard() {
            return board;
        }
    }

    // Конструктор класса, решает пятнашки
    public Solver(Board initial) {
        this.board=initial;

        // проверка пятнашек на решаемость
        if(notSolvable(board)) return;

        // создаем очередь для нахождения приоритетного (сравниваем f(x))
        PriorityQueue<ITEM> priorityQueue = new PriorityQueue<>(10, Comparator.comparingInt(Solver::measure));

        // кладем исходную рсстановку в список
        priorityQueue.add(new ITEM(null, board));

        // Цикл
        while (true){
            ITEM board = priorityQueue.poll(); // вытаскивае самый приоритетный элемен

            // смотрим решены ли пятнашки
            if(board.board.isGoal()) {
                itemToList(new ITEM(board, board.board));
                return;
            }

            // смотрим возможные пути
            for (Board board1 : board.board.neighbors()) {
                if (board1 != null && !containsInPath(board, board1))
                    // рассчитываем f(x) и записываем пути в приоритетную очередь
                    priorityQueue.add(new ITEM(board, board1));
            }

        }
    }

    // Метод для вычисления f(x)
    private static int measure(ITEM item){
        ITEM item2 = item;
        int g = 0;   // g(x)
        int h = item.getBoard().getH();  // h(x)
        while (true){
            g++;
            item2 = item2.prevBoard;
            if(item2 == null) {
                // g(x) + h(x)
                return h + g;
            }
        }
    }

    // Метод, который записывает шаги в результат
    private void itemToList(ITEM item){
        ITEM item2 = item;
        while (true){
            item2 = item2.prevBoard;
            if(item2 == null) {
                Collections.reverse(result);
                return;
            }
            result.add(item2.board);
        }
    }

    // Метод, который проверяет была ли уже такая позиция в пути
    private boolean containsInPath(ITEM item, Board board){
        ITEM item2 = item;
        while (true){
            if(item2.board.equals(board)) return true;
            item2 = item2.prevBoard;
            if(item2 == null) return false;
        }
    }

    // Метод, который проверяет решаемы ли пятнашки
    public boolean notSolvable(Board board) {
        return (board.getK() + board.getZeroY()) % 2 != 0;
    }

    // Метод возвращает колличество ходов
    public int moves() {
        if(notSolvable(board)) return -1;
        return result.size() - 1;
    }

    // Метод позволяет вывести результат в консоль
    public Iterable<Board> solution() {
        return result;
    }
}
