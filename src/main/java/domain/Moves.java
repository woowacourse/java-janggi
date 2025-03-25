package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Moves {

    private final List<Move> moves;

    private Moves(List<Move> moves) {
        this.moves = moves;
    }

    public static Moves create(Move... moves) {
        return new Moves(List.of(moves));
    }

    public static Moves createStraightMove(int distance, Move move) {
        ArrayList<Move> moves = new ArrayList<>(Collections.nCopies(distance, move));
        return new Moves(moves);
    }

    public List<Position> convertToPath(Position startPosition) {
        List<Position> path = new ArrayList<>();
        Position positionInPath = startPosition;
        for (int i = 0; i < moves.size() - 1; i++) {
            positionInPath = positionInPath.movePosition(moves.get(i));
            path.add(positionInPath);
        }

        return path;
    }

    public boolean isPossibleToArrive(Position startPosition, Position targetPosition) {
        Position positionInPath = startPosition;
        for (Move move : moves) {
            if (!positionInPath.canApplyMove(move)) {
                return false;
            }
            positionInPath = positionInPath.movePosition(move);
        }
        return positionInPath.equals(targetPosition);
    }
}
