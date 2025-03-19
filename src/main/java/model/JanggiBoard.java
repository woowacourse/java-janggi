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

    public void move(Point beforePoint ,Point afterPoint){
        // b에 있는 piece를 뽑아서
        Piece piece=getDot(beforePoint).getPiece();
//        piece.isValidPoint(afterPoint);
        // 랑 를 부른다
        // piece가 piece.isValidPoint(a)를 통과해야되고,
        // piece.calculatePath(a)에 이미 존재하는 piece들을 리스트형태로 다시 piece에 넘김
        // piece 그걸 받아서 자기 규칙으로 넘어갈 수 있는지 여부를 반환
        //      -> 경로에 존재하는 기물의 수 + 1개존재할 경우 종점만 존재하고 적군인 지여부까지 통과
        // a에 있는 dot에 옮긴다.
    }

    private Dot getDot(Point point) {
        return janggiBoard.get(point.y()).get(point.x());
    }
}
