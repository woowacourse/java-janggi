package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Horse implements PieceBehavior {

    private static final List<List<Vector>> VECTORS_LIST = List.of(
            List.of(new Vector(1, 0), new Vector(2, -1)),
            List.of(new Vector(1, 0), new Vector(2, 1))
    );

    @Override
    public String toName() {
        return "마";
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        // TODO 함수형 인터페이스 활용

        Set<Position> result = new HashSet<>();
        List<List<Vector>> rotateVectors = new ArrayList<>(VECTORS_LIST);
        for (int i = 0; i < 4; i++) {
            rotateVectors = Vector.rotate(rotateVectors);

            addAvailableMove(result, board, position, rotateVectors, side);
        }

        return result;
    }

    private void addAvailableMove(Set<Position> result, Board board, Position position, List<List<Vector>> vectorsList,
                         Side side) {
        for (List<Vector> vectors : vectorsList) {
            if (!position.canMove(vectors.get(0)) || !position.canMove(vectors.get(1))) {
                return;
            }

            Position midPosition = position.moveToNextPosition(vectors.get(0));
            Position finalPosition = position.moveToNextPosition(vectors.get(1));

            if (board.hasPiece(midPosition)) {
                continue;
            }

            if (board.canMoveToPosition(side, finalPosition)) {
                result.add(finalPosition);
            }
        }
    }
}
