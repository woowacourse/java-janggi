package model;

import static model.JanggiBoardSetUp.DEFAULT_SETUP;

import java.util.ArrayList;

public class JanggiBoard {
    private ArrayList<ArrayList<Dot>> janggiBoard;

    public JanggiBoard(JanggiBoardSetUp elephantSetup) {
        janggiBoard = initializeJanggiBoard();
        placePiece(elephantSetup);
        placePiece(DEFAULT_SETUP);
    }

    private ArrayList<ArrayList<Dot>> initializeJanggiBoard() {
        ArrayList<ArrayList<Dot>> dots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Dot> dotLine = getHorizontalDotsLine();
            dots.add(dotLine);
        }
        return dots;
    }

    private static ArrayList<Dot> getHorizontalDotsLine() {
        ArrayList<Dot> dotLine = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            dotLine.add(new Dot());
        }
        return dotLine;
    }

    private void placePiece(JanggiBoardSetUp janggiBoardSetUp) {
        janggiBoardSetUp.getMap().forEach((key, value)
                -> janggiBoard.get(key.y()).get(key.x()).place(value));
    }

    public int countPiece() {
        int count = 0;
        for (ArrayList<Dot> row : janggiBoard) {
            for (Dot dot : row) {
                if (dot.isPlaced())
                    count++;
            }
        }
        return count;
    }

}
