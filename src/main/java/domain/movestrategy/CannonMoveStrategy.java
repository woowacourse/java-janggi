package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CannonMoveStrategy implements MoveStrategy {

    private static final List<Delta> ORTHOGONAL_DELTAS = List.of(
            Delta.UP,
            Delta.RIGHT,
            Delta.DOWN,
            Delta.LEFT
    );

    @Override
    public List<Position> calculateMovablePositions(Position from, Board board) {
        List<Position> movable = new ArrayList<>();

        for (final Delta delta : ORTHOGONAL_DELTAS) {
            movable.addAll(calculateDefaultMoveByDirection(from, board, delta));
        }

        movable.addAll(calculatePalaceMovablePositions(from, board));

        return movable;
    }

    @Override
    public List<Position> calculatePalaceMovablePositions(final Position from, final Board board) {
        if (!board.inPalace(from)) return List.of();

        List<Position> movable = new ArrayList<>();
        for (Delta delta : board.getPalaceDeltas(from)) {
            movable.addAll(calculatePalaceMove(from, board, delta));
        }

        return movable;
    }


    private List<Position> calculatePalaceMove(Position from, Board board, Delta delta) {
        List<Position> movable = new ArrayList<>();

        Position jumpPosition = from.move(delta);
        Position destination = jumpPosition.move(delta);

        if (!isValidPalacePath(board, jumpPosition, destination)) return movable;
        if (!canJump(board, jumpPosition)) return movable;

        Piece fromPiece = board.getPiece(from);

        if (!board.hasPiece(destination)) {
            movable.add(destination);
            return movable;
        }

        Piece destinationPiece = board.getPiece(destination);
        if (!fromPiece.isSameTeam(destinationPiece) && !destinationPiece.isSameType(PieceType.CANNON)) {
            movable.add(destination);
        }

        return movable;
    }

    private boolean isValidPalacePath(Board board, Position jumpPosition, Position destination) {
        return board.inBoard(jumpPosition) && board.inBoard(destination)
                && board.inPalace(jumpPosition) && board.inPalace(destination);
    }

    private boolean canJump(Board board, Position jumpPosition) {
        if (!board.hasPiece(jumpPosition)) return false;
        return !board.getPiece(jumpPosition).isSameType(PieceType.CANNON);
    }


    private List<Position> calculateDefaultMoveByDirection(final Position from, final Board board, final Delta delta) {
        return findJumpPosition(from, board, delta)
                .map(jump -> movablePositionsAfterJump(from, jump, board, delta))
                .orElseGet(List::of);
    }

    private Optional<Position> findJumpPosition(Position from, Board board, Delta delta) {
        Position current = from.move(delta);

        while (board.inBoard(current) && !board.hasPiece(current)) {
            current = current.move(delta);
        }

        if (!board.inBoard(current) || board.getPiece(current).isSameType(PieceType.CANNON)) {
            return Optional.empty();
        }

        return Optional.of(current);
    }

    private List<Position> movablePositionsAfterJump(Position from, Position jump, Board board, Delta delta) {
        List<Position> movable = new ArrayList<>();

        Position current = jump.move(delta);
        Piece fromPiece = board.getPiece(from);

        while (board.inBoard(current)) {
            if (!board.hasPiece(current)) {
                movable.add(current);
                current = current.move(delta);
                continue;
            }

            Piece piece = board.getPiece(current);
            if (!fromPiece.isSameTeam(piece) && !piece.isSameType(PieceType.CANNON)) {
                movable.add(current);
            }
            break;
        }

        return movable;
    }
}
