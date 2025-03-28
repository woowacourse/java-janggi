package domain.move;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class Moves {
    List<Move> moves;

    private Moves(List<Move> moves) {
        this.moves = moves;
    }

    public static Moves createMoves(Move... moves) {
        return new Moves(List.of(moves));
    }

    public boolean comparePath(Position startPosition, Position targetPosition) {
        Position movedPosition = startPosition;
        for (Move move : moves) {
            if (!movedPosition.canMovePosition(move)) {
                continue;
            }

            movedPosition = movedPosition.movePosition(move);
        }
        return movedPosition.equals(targetPosition);
    }

    public List<Position> convertToPath(Position startPosition) {
        List<Position> path = new ArrayList<>();
        for (int i = 0; i < moves.size() - 1; i++) {
            startPosition = startPosition.movePosition(moves.get(i));
            path.add(startPosition);
        }
        return path;
    }
}
