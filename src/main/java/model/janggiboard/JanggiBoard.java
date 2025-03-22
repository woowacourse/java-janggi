package model.janggiboard;

import static model.janggiboard.JanggiBoardSetUp.DEFAULT_SETUP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Path;
import model.Point;
import model.Team;
import model.piece.Piece;

public class JanggiBoard {
    public static final int VERTICAL_SIZE = 10;
    public static final int HORIZONTAL_SIZE = 9;
    private List<List<Dot>> janggiBoard;

    public JanggiBoard(JanggiBoardSetUp elephantSetup) {
        janggiBoard = initializeJanggiBoard();
        placePiece(elephantSetup);
        placePiece(DEFAULT_SETUP);
    }

    private static List<Dot> getHorizontalDotsLine() {
        List<Dot> dotLine = new ArrayList<>();
        for (int i = 0; i < HORIZONTAL_SIZE; i++) {
            dotLine.add(new Dot());
        }
        return dotLine;
    }

    private List<List<Dot>> initializeJanggiBoard() {
        List<List<Dot>> dots = new ArrayList<>();
        for (int i = 0; i < VERTICAL_SIZE; i++) {
            List<Dot> dotLine = getHorizontalDotsLine();
            dots.add(dotLine);
        }
        return dots;
    }

    private void placePiece(JanggiBoardSetUp janggiBoardSetUp) {
        janggiBoardSetUp.getMap().forEach((key, value)
                -> janggiBoard.get(key.y()).get(key.x()).place(value));
    }

    public int countPiece() {
        int count = 0;
        for (List<Dot> row : janggiBoard) {
            for (Dot dot : row) {
                if (dot.isPlaced()) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isCriticalPoint(Point targetPoint) {
        return getDot(targetPoint).getPiece().isCriticalPiece();
    }

    public boolean movePiece(Point beforePoint, Point targetPoint) {
        Piece beforePiece = getDot(beforePoint).getPiece();
        validateAfterPoint(beforePoint, targetPoint, beforePiece);
        Path path = beforePiece.calculatePath(beforePoint, targetPoint);
        Map<Piece, Boolean> piecesOnPathWithTargetOrNot = getPiecesOnPath(path, targetPoint);
        if (beforePiece.canMove(piecesOnPathWithTargetOrNot)) {
            getDot(targetPoint).place(beforePiece);
            getDot(beforePoint).clear();
            return true;
        }
        throw new IllegalArgumentException("이동할 수 없습니다.");
    }

    private void validateAfterPoint(Point beforePoint, Point targetPoint, Piece piece) {
        if (!piece.isValidPoint(beforePoint, targetPoint)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }

    private Dot getDot(Point point) {
        if (point.x() < 0 || point.y() < 0 || point.x() > HORIZONTAL_SIZE - 1 || point.y() > VERTICAL_SIZE - 1) {
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

    public List<List<Dot>> getJanggiBoard() {
        return janggiBoard;
    }

    public boolean isNotMyTeamPoint(Point beforePoint, Team team) {
        return getDot(beforePoint).getPiece().getTeam() != team;
    }
}
