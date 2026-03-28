package domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Board {

    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board of(Map<Position, Piece> board) {
        return new Board(board);
    }

    public void movePiece(Position piecePosition, Position targetPosition) {
        Piece piece = findPieceAt(piecePosition);

        board.replace(targetPosition, piece.moved(targetPosition));
        board.replace(piecePosition, new Piece(PieceProperty.none(), piecePosition));
    }

    public boolean isMoveable(Position piecePosition, Position targetPosition) {
        Piece piece = findPieceAt(piecePosition);

        if (isTargetPositionPieceSameTeam(piece, targetPosition)) {
            return false;
        }

        if (!piece.isMoveAble(targetPosition)) {
            return false;
        }

        if (piece.isCannon()) {
            List<Position> cannonPositions = findCannonPositions();
            if (findPieceAt(targetPosition).isCannon() || !piece.hasPieceOnPath(targetPosition, cannonPositions)) {
                return false;
            }
        }

        List<Position> allPiecePositions = findAllPiecePositions();

        return !piece.hasPieceOnPath(targetPosition, allPiecePositions);
    }

    private List<Position> findAllPiecePositions() {
        return board.values().stream().filter(piece -> !piece.isNoneTeam()).map(Piece::position).toList();
    }

    private List<Position> findCannonPositions() {
        return board.values().stream()
                .filter(Piece::isCannon)
                .map(Piece::position)
                .toList();
    }

    private boolean isTargetPositionPieceSameTeam(Piece piece, Position targetPosition) {
        if (piece.isRedTeam()) {
            return findPieceAt(targetPosition).isRedTeam();
        }
        if (piece.isGreenTeam()) {
            return findPieceAt(targetPosition).isGreenTeam();
        }
        return false;
    }

    Piece findPieceAt(Position targetPosition) {
        return board.get(targetPosition);
    }

    public boolean hasGreenTeamGeneral() {
        return greenPieces().stream()
                .anyMatch(Piece::isGeneral);
    }

    public boolean hasRedTeamGeneral() {
        return redPieces().stream()
                .anyMatch(Piece::isGeneral);
    }

    public List<Piece> allPieces() {
        return Stream.of(greenPieces(), redPieces(), nonePieces())
                .flatMap(List::stream)
                .toList();
    }

    private List<Piece> greenPieces() {
        return board.values().stream()
                .filter(Piece::isGreenTeam)
                .toList();
    }

    private List<Piece> redPieces() {
        return board.values().stream()
                .filter(Piece::isRedTeam)
                .toList();
    }

    private List<Piece> nonePieces() {
        return board.values().stream()
                .filter(Piece::isNoneTeam)
                .toList();
    }
}
