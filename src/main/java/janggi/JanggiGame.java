package janggi;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final Pieces pieces;

    public JanggiGame(final Pieces pieces) {
        this.pieces = pieces;
    }

    public JanggiGame() {
        this.pieces = Pieces.init();
    }

    public void move(final Position start, final Position end) {
        validateEndPositionPiece(start, end);
        validatePieceOnPath(start, end);
        pieces.moveForward(start, end);
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
}
