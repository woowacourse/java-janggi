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
        Piece piece = getDot(beforePoint).getPiece();
        validateAfterPoint(beforePoint, targetPoint, piece);
        Path path = piece.calculatePath(beforePoint, targetPoint);
        Map<Piece, Boolean> piecesOnPathWithTargetOrNot = getPiecesOnPath(path, targetPoint);
        if (piece.canMove(piecesOnPathWithTargetOrNot)) {
            getDot(targetPoint).place(piece);
            getDot(beforePoint).clear();
            return;
        }
        throw new IllegalArgumentException("이동할 수 없습니다.");
    }

    private void validateAfterPoint(Point beforePoint, Point targetPoint, Piece piece) {
        if (!piece.isValidPoint(beforePoint, targetPoint)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }

    private Dot getDot(Point point) {
        if (point.x() < 0 || point.y() < 0 || point.x() > 8 || point.y() > 9) {
            throw new IllegalArgumentException("장기판을 벗어난 좌표입니다.");
        }
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

    public ArrayList<ArrayList<Dot>> getJanggiBoard() {
        return janggiBoard;
    }
}
