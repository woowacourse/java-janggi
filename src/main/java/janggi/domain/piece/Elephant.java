package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Elephant implements PieceBehavior {

    private static final List<List<Vector>> VECTORS_LIST = List.of(
            List.of(new Vector(1, 0), new Vector(2, -1), new Vector(3, -2)),
            List.of(new Vector(1, 0), new Vector(2, 1), new Vector(3, 2))
    );

    @Override
    public String toName() {
        return "상";
    }

    @Override
    public Set<Position> generateMovePosition(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        List<List<Vector>> rotateVectors = new ArrayList<>(VECTORS_LIST);
        for (int i = 0; i < 4; i++) {
            rotateVectors = Vector.rotate(rotateVectors);

            process(result, board, position, rotateVectors, side);
        }

        return result;
    }

    private void process(Set<Position> result, Board board, Position position, List<List<Vector>> vectorsList,
                         Side side) {
        for (List<Vector> vectors : vectorsList) {
            if (!checkAvailableMiddleMove(position.calculate(vectors.get(0))
                    .orElse(null), board)) {
                continue;
            }

            if (!checkAvailableMiddleMove(position.calculate(vectors.get(1))
                    .orElse(null), board)) {
                continue;
            }

            Optional<Position> finalPosition = position.calculate(vectors.get(2));

            if (finalPosition.isEmpty()) {
                continue;
            }

            if (board.canMoveToPosition(side, finalPosition.get())) {
                result.add(finalPosition.get());
            }
        }
    }

    private boolean checkAvailableMiddleMove(Position midPosition, Board board) {
        return midPosition != null && !board.hasPiece(midPosition);

    }
}
