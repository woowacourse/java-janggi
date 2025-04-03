package model.janggiboard;

import static model.janggiboard.JanggiBoardSetUp.DEFAULT_SETUP;

import dao.JanggiDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Path;
import model.Point;
import model.Team;
import model.piece.Piece;
import model.piece.PieceFactory;
import model.piece.PieceName;
import vo.PieceVo;

public class JanggiBoard {
    public static final int VERTICAL_SIZE = 10;
    public static final int HORIZONTAL_SIZE = 9;
    private static final int HORIZONTAL_MINIMUM_SIZE = 0;
    private static final int VERTICAL_MINIMUM_SIZE = 0;
    private final List<List<Dot>> janggiBoard;
    private final JanggiDao janggiDao;

    public JanggiBoard(JanggiBoardSetUp elephantSetup, JanggiDao janggiDao) {
        janggiBoard = initializeJanggiBoard();
        this.janggiDao = janggiDao;
        placePiece(elephantSetup);
        placePiece(DEFAULT_SETUP);

        List<PieceVo> pieceVo = pieceToPieceVo();
        janggiDao.settingNewJanggiBoard(pieceVo);
    }

    private List<PieceVo> pieceToPieceVo() {
        List<PieceVo> pieceVos = new ArrayList<>();
        for (int i = 0; i < VERTICAL_SIZE; i++) {
            for (int j = 0; j < HORIZONTAL_SIZE; j++) {
                if (janggiBoard.get(i).get(j).isPlaced()) {
                    Piece piece = janggiBoard.get(i).get(j).getPiece();
                    String pieceName = piece.getPieceName().getName();
                    String pieceTeam = piece.getTeam().getTeam();

                    PieceVo pieceVo = new PieceVo(pieceName, j, i, pieceTeam);
                    pieceVos.add(pieceVo);
                }
            }
        }
        return pieceVos;
    }

    public JanggiBoard(JanggiDao janggiDao) {
        this.janggiDao = janggiDao;
        janggiBoard = initializeJanggiBoard();
        settingJanggiBoard(janggiDao);
    }

    private void settingJanggiBoard(JanggiDao janggiDao) {
        List<PieceVo> pieceVos = janggiDao.settingBeforeJanggiBoard();
        for (PieceVo pieceVo : pieceVos) {
            Team team = Team.findTeamByName(pieceVo.getTeam());
            String pieceName = pieceVo.getPieceName();

            int x = pieceVo.getPointX();
            int y = pieceVo.getPointY();

            Piece piece = PieceFactory.createPiece(pieceName, team);
            Dot dot = new Dot(piece);
            janggiBoard.get(y).set(x, dot);
        }
    }

    private List<List<Dot>> initializeJanggiBoard() {
        List<List<Dot>> dots = new ArrayList<>();
        for (int i = 0; i < VERTICAL_SIZE; i++) {
            List<Dot> dotLine = getHorizontalDotsLine();
            dots.add(dotLine);
        }
        return dots;
    }

    private static List<Dot> getHorizontalDotsLine() {
        List<Dot> dotLine = new ArrayList<>();
        for (int i = 0; i < HORIZONTAL_SIZE; i++) {
            dotLine.add(new Dot());
        }
        return dotLine;
    }

    private void placePiece(JanggiBoardSetUp janggiBoardSetUp) {
        janggiBoardSetUp.getPoints().forEach((key, value) -> {
            janggiBoard.get(key.y()).set(key.x(), new Dot(value));
        });
    }

    public int countPiece() {
        return (int) janggiBoard.stream()
                .flatMap(List::stream)
                .filter(Dot::isPlaced)
                .count();
    }

    public void move(Point beforePoint, Point targetPoint) {
        Piece piece = getDot(beforePoint).getPiece();
        validateAfterPoint(beforePoint, targetPoint, piece);
        Path path = piece.calculatePath(beforePoint, targetPoint);
        Map<Piece, Boolean> piecesOnPathWithTargetOrNot = getPiecesOnPath(path, targetPoint);

        if (piece.canMove(piecesOnPathWithTargetOrNot)) {
            if (getDot(targetPoint).isPlaced()) {
                janggiDao.deletePiece(targetPoint);
            }
            Piece beforePiece = getDot(beforePoint).getPiece();
            janggiDao.changePieceLocation(beforePiece, targetPoint);
            janggiBoard.get(targetPoint.y()).set(targetPoint.x(), new Dot(piece));
            janggiBoard.get(beforePoint.y()).set(beforePoint.x(), new Dot());
            janggiDao.updateTurn();
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
        if (point.x() < HORIZONTAL_MINIMUM_SIZE
                || point.y() < VERTICAL_MINIMUM_SIZE || point.x() >= HORIZONTAL_SIZE || point.y() >= VERTICAL_SIZE) {
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
        boolean isLastPoint = point.equals(targetPoint);

        if (getDot(point).isPlaced()) {
            piecesOnPathWithTargetOrNot.put(getDot(point).getPiece(), isLastPoint);
        }
    }

    public boolean isNotMyTeamPoint(Point beforePoint, Team team) {
        return getDot(beforePoint).getPiece().getTeam() != team;
    }

    public double getTeamScore(Team team) {
        double score = team.getDefaultScore();
        for (List<Dot> dots : janggiBoard) {
            for (Dot dot : dots) {
                if (dot.isPlaced()) {
                    Piece piece = dot.getPiece();
                    if (piece.getTeam() == team) {
                        score += piece.getScore();
                    }
                }
            }
        }
        return score;
    }

    public boolean isKingDead() {
        int kingCount = 0;
        for (List<Dot> dots : janggiBoard) {
            for (Dot dot : dots) {
                if (dot.isPlaced()) {
                    Piece piece = dot.getPiece();
                    if (piece.getPieceName() == PieceName.JANG) {
                        kingCount += 1;
                    }
                }
            }
        }
        return kingCount != 2;
    }

    public Team getWinner() {
        Team winner = null;
        for (List<Dot> dots : janggiBoard) {
            for (Dot dot : dots) {
                if (dot.isPlaced()) {
                    Piece piece = dot.getPiece();
                    if (piece.getPieceName() == PieceName.JANG) {
                        return piece.getTeam();
                    }
                }
            }
        }
        return winner;
    }

    public List<List<Dot>> getJanggiBoard() {
        return janggiBoard;
    }
}
