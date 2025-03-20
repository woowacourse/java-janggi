package model;

import static model.JanggiBoardSetUp.DEFAULT_SETUP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                if (dot.isPlaced()) {
                    count++;
                }
            }
        }
        return count;
    }

    public void move(Point beforePoint, Point targetPoint) {
        // b에 있는 piece를 뽑아서
        Piece piece = getDot(beforePoint).getPiece();
        validateAfterPoint(beforePoint, targetPoint, piece);
        Path path = piece.calculatePath(beforePoint, targetPoint);
        Map<Piece,Boolean> piecesOnPathWithTargetOrNot = getPiecesOnPath(path, targetPoint);
        if (piece.canMove(piecesOnPathWithTargetOrNot)) {
            getDot(targetPoint).place(piece);
            getDot(beforePoint).clear();
        }
        // piece 그걸 받아서 자기 규칙으로 넘어갈 수 있는지 여부를 반환
        //      -> 경로에 존재하는 기물의 수 + 1개존재할 경우 종점만 존재하고 적군인 지여부까지 통과
        // a에 있는 dot에 옮긴다.
    }

    private void validateAfterPoint(Point beforePoint, Point targetPoint, Piece piece) {
        if (!piece.isValidPoint(beforePoint, targetPoint)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }

    private Dot getDot(Point point) {
        return janggiBoard.get(point.y()).get(point.x());
    }

    private Map<Piece, Boolean> getPiecesOnPath(Path path, Point targetPoint) {
        Map<Piece, Boolean> piecesOnPathWithTargetOrNot = new HashMap<>();
        for (Point point : path.getPath()) {
            addPiecesOnPathWithTargetOrNot(targetPoint, point, piecesOnPathWithTargetOrNot);
        }
        return piecesOnPathWithTargetOrNot;
    }

    private void addPiecesOnPathWithTargetOrNot(Point targetPoint, Point point,
                                                Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (getDot(point).isPlaced()) {
            if (point.equals(targetPoint)) {
                piecesOnPathWithTargetOrNot.put(getDot(point).getPiece(), true);
                return;
            }
            piecesOnPathWithTargetOrNot.put(getDot(point).getPiece(), false);
        }
    }
}
