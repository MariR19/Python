public class Main {

    public static void main(String[] args) {
        int[][] blocks = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {13, 9, 11, 12}, {10, 15, 14, 0}};
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        System.out.println("Минимальное колличество действий = " + solver.moves());
        for (Board board : solver.solution())
            System.out.println(board);
    }
}
