package domain;

import domain.piece.Piece;
import domain.strategy.NoneMoveableStrategy;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board of(Map<Position, Piece> board) {
        return new Board(board);
    }

    public void movePiece(Position piecePosition, Position targetPosition) {
        Piece piece = board.get(piecePosition);

        board.replace(targetPosition, piece);
        board.replace(piecePosition, new Piece(PieceProperty.of(PieceType.EMPTY_VALUE, Team.NONE), new NoneMoveableStrategy(piecePosition)));
    }

    public boolean isMoveable(Position piecePosition, Position targetPosition) {
        Piece piece = board.get(piecePosition);

        if (isTargetPositionPieceSameTeam(piece, targetPosition)) {
            return false;
        }

        if (!piece.isMoveAble(targetPosition)) {
            return false;
        }

        List<Position> sameTeamPositions = findSameTeamPositions(piece);

        return !piece.isInvalidPath(targetPosition, sameTeamPositions);
    }

    private boolean isTargetPositionPieceSameTeam(Piece piece, Position targetPosition) {
        if (piece.isRedTeam()) {
            return board.get(targetPosition).isRedTeam();
        }
        if (piece.isGreenTeam()) {
            return board.get(targetPosition).isGreenTeam();
        }
        return false;
    }

    private List<Position> findSameTeamPositions(Piece piece) {
        if (piece.isGreenTeam()) {
            return greenPieces().stream().map(Piece::position).filter(position -> !position.equals(piece.position()))
                    .toList();
        }

        return redPieces().stream().map(Piece::position).filter(position -> !position.equals(piece.position()))
                .toList();
    }

    public boolean hasGreenTeamGeneral() {
        return greenPieces().stream().anyMatch(Piece::isGeneral);
    }

    public boolean hasRedTeamGeneral() {
        return redPieces().stream().anyMatch(Piece::isGeneral);
    }


    public List<Piece> greenPieces() {
        return board.values().stream().filter(Piece::isGreenTeam)
                .toList();
    }

    public List<Piece> redPieces() {
        return board.values().stream().filter(Piece::isRedTeam)
                .toList();
    }

    public List<Piece> nonePieces() {
        return board.values().stream()
                .filter(Piece::isNoneTeam)
                .toList();
    }

}
