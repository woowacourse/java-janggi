package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;

import java.util.ArrayList;
import java.util.List;

public class ChariotMoveStrategy implements MoveStrategy {

    private static final List<Delta> ORTHOGONAL = List.of(
            Delta.UP,
            Delta.RIGHT,
            Delta.DOWN,
            Delta.LEFT
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        final List<Position> movable = new ArrayList<>();

        for (final Delta delta : ORTHOGONAL) {
            movable.addAll(calculateDefaultMoveByDirection(from, board, delta));
        }

        movable.addAll(calculatePalaceMovablePositions(from, board));

        return movable;
    }

    @Override
    public List<Position> calculatePalaceMovablePositions(final Position from, final Board board) {
        if (!board.inPalace(from)) {
            return List.of();
        }

        final List<Position> movable = new ArrayList<>();

        for (Delta delta : board.getPalaceDeltas(from)) {
            movable.addAll(calculatePalaceMoveByDirection(from, board, delta));
        }

        return movable;
    }


    private List<Position> calculateDefaultMoveByDirection(final Position from, final Board board, final Delta delta) {
        final List<Position> movable = new ArrayList<>();
        final Piece fromPiece = board.getPiece(from);

        Position current = from.move(delta);

        while (board.inBoard(current)) {
            if (!board.hasPiece(current)) {
                movable.add(current);
                current = current.move(delta);
                continue;
            }

            if (!fromPiece.isSameTeam(board.getPiece(current))) {
                movable.add(current);
            }

            break;
        }

        return movable;
    }

    private List<Position> calculatePalaceMoveByDirection(final Position from, final Board board, final Delta delta) {
        final List<Position> movable = new ArrayList<>();
        final Piece fromPiece = board.getPiece(from);

        Position current = from.move(delta);

        while (board.inBoard(current) && board.inPalace(current)) {
            if (!board.hasPiece(current)) {
                movable.add(current);
                current = current.move(delta);
                continue;
            }

            if (!fromPiece.isSameTeam(board.getPiece(current))) {
                movable.add(current);
            }

            break;
        }

        return movable;
    }
}
