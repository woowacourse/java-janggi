package model.janggiboard;

import static model.janggiboard.JanggiBoardSetUp.DEFAULT_SETUP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Path;
import model.Point;
import model.Team;
import model.dto.PieceDto;
import model.piece.Piece;

public class JanggiBoard {
    public static final int VERTICAL_SIZE = 10;
    public static final int HORIZONTAL_SIZE = 9;
    private final List<List<Dot>> janggiBoard;

    public JanggiBoard(JanggiBoardSetUp elephantSetup) {
        janggiBoard = initializeJanggiBoard();
        placePiece(elephantSetup);
        placePiece(DEFAULT_SETUP);
    }

    public JanggiBoard(Map<Point, Piece> map) {
        janggiBoard = initializeJanggiBoard();
        map.forEach((point, piece) -> janggiBoard.get(point.y()).get(point.x()).place(piece));
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
        janggiBoardSetUp.getMap().forEach((point, piece)
                -> janggiBoard.get(point.y()).get(point.x()).place(piece));
    }

    public int countPiece() {
        int count = 0;

        for (List<Dot> row : janggiBoard) {
            count += (int) row.stream().filter(Dot::isPlaced).count();
        }
        return count;
    }

    public boolean isCriticalPoint(Point targetPoint, Team myTeam) {
        if (getDot(targetPoint).isPlaced()) {
            Piece targetPiece = getPieceFromPoint(targetPoint);
            return targetPiece.isCriticalPiece() && targetPiece.getTeam() != myTeam;
        }
        return false;
    }

    private Piece getPieceFromPoint(Point targetPoint) {
        return getDot(targetPoint).findPiece()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 점에는 장기말이 없습니다."));
    }

    public boolean movePiece(Point beforePoint, Point targetPoint) {
        Piece beforePiece = getPieceFromPoint(beforePoint);
        validateAfterPoint(beforePoint, targetPoint, beforePiece);
        Path path = beforePiece.calculatePath(beforePoint, targetPoint);
        Map<Piece, Boolean> piecesOnPathWithTargetOrNot = getPiecesOnPath(path, targetPoint);

        if (beforePiece.canMove(piecesOnPathWithTargetOrNot)) {
            getDot(targetPoint).place(beforePiece);
            getDot(beforePoint).clear();
            return true;
        }
        throw new IllegalArgumentException("[ERROR] 이동할 수 없는 지점입니다.");
    }

    public List<PieceDto> getAlivePieces() {
        List<PieceDto> alivePieces = new ArrayList<>();
        for (int y_pos = 0; y_pos < janggiBoard.size(); y_pos++) {
            for (int x_pos = 0; x_pos < janggiBoard.get(y_pos).size(); x_pos++) {
                if (janggiBoard.get(y_pos).get(x_pos).isPlaced()) {
                    Piece piece = getPieceFromDot(janggiBoard.get(y_pos).get(x_pos));
                    alivePieces.add(new PieceDto(x_pos, y_pos, piece.getTeam().getTeamName(), piece.getPieceName()));
                }
            }
        }
        return alivePieces;
    }

    private void validateAfterPoint(Point beforePoint, Point targetPoint, Piece piece) {
        if (!piece.isValidPoint(beforePoint, targetPoint)) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 지점입니다.");
        }
    }

    public boolean isNotMyTeamPoint(Point beforePoint, Team team) {
        return getDot(beforePoint).findPiece()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 점에는 장기말이 없습니다."))
                .getTeam() != team;
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
            Piece piece = getPieceFromPoint(point);
            if (point.equals(targetPoint)) {
                piecesOnPathWithTargetOrNot.put(piece, true);
                return;
            }
            piecesOnPathWithTargetOrNot.put(piece, false);
        }
    }

    private Dot getDot(Point point) {
        if (point.x() < 0 || point.y() < 0 || point.x() > HORIZONTAL_SIZE - 1 || point.y() > VERTICAL_SIZE - 1) {
            throw new IllegalArgumentException("[ERROR] 장기판을 벗어난 좌표입니다.");
        }
        return janggiBoard.get(point.y()).get(point.x());
    }

    public double getTotalScore(Team team) {
        double total = 0;
        for (List<Dot> row : janggiBoard) {
            total += row.stream().filter(dot -> dot.isPlaced() && getPieceFromDot(dot).getTeam() == team)
                    .mapToDouble(dot -> getPieceFromDot(dot).getPieceScore()).sum();
        }
        if (team == Team.RED) {
            total += 1.5;
        }
        return total;
    }

    private Piece getPieceFromDot(Dot dot) {
        return dot.findPiece()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 점에는 장기말이 없습니다."));
    }

    public List<List<Dot>> getJanggiBoard() {
        return janggiBoard;
    }
}
