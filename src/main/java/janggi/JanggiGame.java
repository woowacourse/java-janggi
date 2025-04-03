package janggi;

import janggi.database.JanggiDao;
import janggi.piece.Color;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class JanggiGame {
    private static final double DEOM_SCORE = 1.5;

    private final Pieces pieces;
    private final JanggiDao janggiDao;


    public JanggiGame(final Pieces pieces) {
        this.pieces = pieces;
        this.janggiDao = new JanggiDao();
    }

    public JanggiGame() {
        this.pieces = Pieces.init();
        this.janggiDao = new JanggiDao();
        // NOTE: DB에 피스 정보를 저장한다
        janggiDao.deletePieces();
        janggiDao.savePieces(pieces.getPieces());
    }

    public static JanggiGame continueGame() {
        JanggiDao janggiDao = new JanggiDao();
        // NOTE: DB에 피스 정보를 가져온다
        Pieces pieces = janggiDao.findPieces();
        return new JanggiGame(pieces);
    }

    public boolean move(final Position start, final Position end) {
        if (!pieces.isMoveable(start, end)) {
            return false;
        }
        pieces.moveForward(start, end);
        janggiDao.deletePieces();
        janggiDao.savePieces(pieces.getPieces());
        return true;
    }

    private void validateEndPositionPiece(Position start, Position end) {
        if (pieces.isSameColorPiece(start, end)) {
            throw new IllegalArgumentException("같은 팀이 있는 위치로는 이동할 수 없습니다.");
        }
        if (pieces.isEachCannonPiece(start, end)) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    private void validatePieceOnPath(final Position start, final Position end) {
        Piece startPositionPiece = pieces.getPieceByPosition(start);
        if (startPositionPiece.isCannon()) {
            validateNoneCannonPieceOnPathOfCannonPiece(start, end);
            validateOnlyOnePieceOnPathOfCannonPiece(start, end);
            return;
        }
        validateNonePieceOnPathOfNormalPiece(start, end);
    }

    private void validateNonePieceOnPathOfNormalPiece(final Position start, final Position end) {
        Piece startPositionPiece = pieces.getPieceByPosition(start);
        List<Position> path = startPositionPiece.calculatePath(start, end);
        if (pieces.containsPiece(path)) {
            throw new IllegalArgumentException("경로 상에 말이 존재합니다.");
        }
    }

    // 경로 위에 말이 하나만 있어야 함.
    private void validateOnlyOnePieceOnPathOfCannonPiece(final Position start, final Position end) {
        Piece startPositionPiece = pieces.getPieceByPosition(start);
        List<Position> path = startPositionPiece.calculatePath(start, end);
        long countPieceOnPath = pieces.countPieceOnPath(path);
        if (countPieceOnPath != 1) {
            throw new IllegalArgumentException("포가 이동하기 위해서는 1개의 말만을 뛰어넘어야 합니다.");
        }
    }

    // 경로 위에 포가 존재하지 않아야 함.
    private void validateNoneCannonPieceOnPathOfCannonPiece(final Position start, final Position end) {
        Piece startPositionPiece = pieces.getPieceByPosition(start);
        List<Position> path = startPositionPiece.calculatePath(start, end);
        boolean isCannonPieceOnPath = pieces.isCannonPieceOnPath(path);
        if (isCannonPieceOnPath) {
            throw new IllegalArgumentException("포의 이동 경로 상에 포가 존재합니다.");
        }
    }

    public Map<Position, Piece> getBoard() {
        return pieces.getPieces();
    }

    public double getScore(final Color color) {
        if (color == Color.RED) {
            return pieces.calculatePieceScore(color) + DEOM_SCORE;
        }
        return pieces.calculatePieceScore(color);
    }
}
