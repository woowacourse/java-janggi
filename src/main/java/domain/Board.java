package domain;

import domain.piece.Piece;
import domain.strategy.NoneMoveableStrategy;
import java.util.ArrayList;
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
        Piece piece = findPieceAt(piecePosition);

        piece.moved(targetPosition);
        board.replace(targetPosition, piece);
        board.replace(piecePosition,
                new Piece(PieceProperty.of(PieceType.EMPTY_VALUE, Team.NONE), NoneMoveableStrategy.of(piecePosition)));
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

    public List<Piece> allPieces() {
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(greenPieces());
        allPieces.addAll(redPieces());
        allPieces.addAll(nonePieces());
        return allPieces;
    }
}
