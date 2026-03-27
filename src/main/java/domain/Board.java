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
        board.replace(piecePosition, new Piece(PieceProperty.of(PieceType.EMPTY_VALUE, Team.NONE), NoneMoveableStrategy.of(piecePosition)));
    }

    public boolean isMoveable(Position piecePosition, Position targetPosition) {
        Piece piece = board.get(piecePosition);

        if (isTargetPositionPieceSameTeam(piece, targetPosition)) {
            return false;
        }

        if (!piece.isMoveAble(targetPosition)) {
            return false;
        }

        if(piece.isCannon()) {
            List<Position> cannonPositions = findCannonPositions();
            if(piece.isInvalidPath(targetPosition, cannonPositions)) {
                return false;
            }
        }

        List<Position> allPiecePositions = findAllPiecePositions();

        return !piece.isInvalidPath(targetPosition, allPiecePositions);
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
            return board.get(targetPosition).isRedTeam();
        }
        if (piece.isGreenTeam()) {
            return board.get(targetPosition).isGreenTeam();
        }
        return false;
    }
//
//    private List<Position> findSameTeamPositions(Piece piece) {
//        if (piece.isGreenTeam()) {
//            return greenPieces().stream().map(Piece::position).filter(position -> !position.equals(piece.position()))
//                    .toList();
//        }
//
//        return redPieces().stream().map(Piece::position).filter(position -> !position.equals(piece.position()))
//                .toList();
//    }

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
