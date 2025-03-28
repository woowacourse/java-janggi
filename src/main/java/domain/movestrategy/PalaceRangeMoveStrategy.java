package domain.movestrategy;

import domain.Position;
import domain.move.Move;
import domain.move.Moves;
import java.util.List;

public class PalaceRangeMoveStrategy implements RangeMoveStrategy {

    private final List<Moves> moves = List.of(
            Moves.createMoves(Move.FRONT), Moves.createMoves(Move.BACK),
            Moves.createMoves(Move.RIGHT), Moves.createMoves(Move.LEFT),

            Moves.createMoves(Move.FRONT, Move.FRONT), Moves.createMoves(Move.BACK, Move.BACK),
            Moves.createMoves(Move.RIGHT, Move.RIGHT), Moves.createMoves(Move.LEFT, Move.LEFT),

            Moves.createMoves(Move.FRONT_RIGHT), Moves.createMoves(Move.FRONT_LEFT),
            Moves.createMoves(Move.BACK_RIGHT), Moves.createMoves(Move.BACK_LEFT),

            Moves.createMoves(Move.FRONT_RIGHT, Move.FRONT_RIGHT), Moves.createMoves(Move.FRONT_LEFT, Move.FRONT_LEFT),
            Moves.createMoves(Move.BACK_RIGHT, Move.BACK_RIGHT), Moves.createMoves(Move.BACK_LEFT, Move.BACK_LEFT)
    );

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        for (Moves moves : moves) {
            boolean compareResult = moves.comparePath(startPosition, targetPosition);
            if (compareResult) {
                return moves.convertToPath(startPosition);
            }
        }
        throw new IllegalArgumentException("이 위치로 이동할 수 없습니다.");
    }


}
