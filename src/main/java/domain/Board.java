package domain;

public class Board {

    private final int[][] board;

    public Board() {
        this.board = new int[10][9];
    }

    public int[][] getBoard() {
        return board;
    }

}
