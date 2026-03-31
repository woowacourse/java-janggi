package domain;

import domain.strategy.NonMoveableStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public static Board of(Map<Position, Piece> board) {
        return new Board(board);
    }

    public void movePiece(Position source, Position destination) {
        Piece piece = pieceAt(source);
        piece.moveTo(destination);
        board.put(destination, piece);
        board.put(source, emptyPieceAt(source));
    }

    public boolean canMove(Position source, Position destination) {
        Piece piece = pieceAt(source);

        if (hasSameTeamPieceAt(piece, destination)) {
            return false;
        }

        if (!piece.canMoveTo(destination)) {
            return false;
        }

        if (piece.isCannon()) {
            return (canCannonMove(piece, destination));
        }

        return piece.hasValidPathTo(destination, occupiedPositions());
    }

    public boolean hasGreenTeamGeneral() {
        return greenPieces().stream()
                .anyMatch(Piece::isGeneral);
    }

    public boolean hasRedTeamGeneral() {
        return redPieces().stream()
                .anyMatch(Piece::isGeneral);
    }


    public List<Piece> greenPieces() {
        return board.values().stream()
                .filter(Piece::isGreenTeam)
                .toList();
    }

    public List<Piece> redPieces() {
        return board.values().stream()
                .filter(Piece::isRedTeam)
                .toList();
    }

    public List<Piece> nonePieces() {
        return board.values().stream()
                .filter(Piece::isNoneTeam)
                .toList();
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }

    private Piece pieceAt(Position source) {
        return board.get(source);
    }

    private static Piece emptyPieceAt(Position source) {
        return Piece.of(PieceProperty.of(PieceType.EMPTY_VALUE, Team.NONE), NonMoveableStrategy.of(source));
    }

    private boolean hasSameTeamPieceAt(Piece piece, Position targetPosition) {
        if (piece.isRedTeam()) {
            return pieceAt(targetPosition).isRedTeam();
        }
        if (piece.isGreenTeam()) {
            return pieceAt(targetPosition).isGreenTeam();
        }
        return false;
    }

    private boolean canCannonMove(Piece piece, Position destination) {
        if (pieceAt(destination).isCannon()) {
            return false;
        }
        if (piece.hasValidPathTo(destination, cannonPositions())) {
            return false;
        }
        return piece.hasValidPathTo(destination, occupiedPositions());
    }

    private List<Position> cannonPositions() {
        return board.values().stream()
                .filter(Piece::isCannon)
                .map(Piece::currentPosition)
                .toList();
    }

    private List<Position> occupiedPositions() {
        return board.values().stream().filter(piece -> !piece.isNoneTeam()).map(Piece::currentPosition).toList();
    }

}
