package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Piece;
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
            movable.addAll(calculateMovableByDirection(from, board, delta));
        }

        return movable;
    }


    private List<Position> calculateMovableByDirection(final Position from, final Board board, final Delta delta) {
        return findJumpPosition(from, board, delta)
                .map(jump -> movablePositionsAfterJump(from, jump, board, delta))
                .orElseGet(List::of);
    }

    private Optional<Position> findJumpPosition(Position from, Board board, Delta delta) {
        Position current = from.move(delta);

        while (board.inBoard(current) && !board.hasPiece(current)) {
            current = current.move(delta);
        }

        if (!board.inBoard(current) || board.getPiece(current).isSameType()) {
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
            if (!fromPiece.isSameTeam(piece) && !piece.isSameType()) {
                movable.add(current);
            }
            break;
        }

        return movable;
    }
}
