import java.util.HashSet;
import java.util.Set;


// Класс представляет доску пятнашек
public class Board {
    private final int[][] blocks; // наше поле (пустое место обозначается нулем)
    private int zeroY; // координаты нуля
    private int zeroX;
    private int h; // колличество элементов не на своем месте
    private int k; // количество перестановок

    public int getBlocksLength() {
        return blocks.length;
    }
    public int getH() {
        return h;
    }
    public int getZeroY(){
        return zeroY;
    }
    public int getK(){
        return k;
    }

    // Коннструктор класса, инициализирует поля(заполняет доску и другие значения)
    public Board(int[][] blocks) {
        this.blocks = deepCopy(blocks);

        int[] kArr=new int[blocks.length*blocks[0].length];

        h = 0;
        int temp=0;
        for (int i = 0; i < blocks.length; i++) {  // цикл, для определения координат нуля и вычисления h(x)
            for (int j = 0; j < blocks[i].length; j++) {
                if(blocks[i][j] == 0){
                    zeroY = i;
                    zeroX = j;
                }
                else if (blocks[i][j] != (i*blocks.length + j + 1)) {
                    h++;
                }
                kArr[temp]=blocks[i][j];
                temp++;
            }
        }
        for(int i=0;i<kArr.length-1;i++){   // цикл, для вычисления k
            for(int j=i+1;j<kArr.length;j++){
                if(kArr[i]>kArr[j]){
                    k++;
                }
            }
        }
    }

    // Метод возвращает true, если пятнашки решены
    public boolean isGoal() {  // если все на своем месте, значит это нужная позиция
        return h == 0;
    }

    // Метод возврвщает все возможные шаги из текущей позиции
    public Iterable<Board> neighbors() {
        // меняем ноль с соседней клеткой, то есть всего 4 варианта
        // если соседнего нет (0 может быть с краю), change(...) вернет null
        Set<Board> boardList = new HashSet<>();
        boardList.add(change(getNewBlock(), zeroY, zeroX, zeroY, zeroX + 1));
        boardList.add(change(getNewBlock(), zeroY, zeroX, zeroY, zeroX - 1));
        boardList.add(change(getNewBlock(), zeroY, zeroX, zeroY - 1, zeroX));
        boardList.add(change(getNewBlock(), zeroY, zeroX, zeroY + 1, zeroX));

        return boardList;
    }

    // Метод копируе массив пятнашек
    private int[][] getNewBlock() {
        return deepCopy(blocks);
    }

    // Метод двигает 0 на определенную позицию
    private Board change(int[][] blocks2, int y1, int x1, int y2, int x2) {

        if (y2 > -1 && y2 < blocks.length && x2 > -1 && x2 < blocks.length) {
            int t = blocks2[y2][x2];
            blocks2[y2][x2] = blocks2[y1][x1];
            blocks2[y1][x1] = t;
            return new Board(blocks2);
        }
        else return null;
    }

    // Мето возвращает копию двумерного массива
    private static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];

        for (int i = 0; i < original.length; i++) {
            result[i] = new int[original[i].length];
            System.arraycopy(original[i], 0, result[i], 0, original[i].length);
        }
        return result;
    }

    // Перезаписанные Object методы
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (board.getBlocksLength() != blocks.length) return false;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != board.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int[] block : blocks) {
            for (int j = 0; j < blocks.length; j++) {
                s.append(String.format("%2d ", block[j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
