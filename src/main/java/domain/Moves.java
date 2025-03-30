package domain;

import domain.piece.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
            if (!positionInPath.hasLine(move)) {
                return false;
            }
            positionInPath = positionInPath.movePosition(move);
        }
        return positionInPath.equals(targetPosition);
    }

    public boolean isPossibleInPalace(Position startPosition) {
        Position positionInPath = startPosition;
        for (Move move : moves) {
            if (!positionInPath.canMoveInPalace(move)) {
                return false;
            }
            positionInPath = positionInPath.movePosition(move);
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Moves moves1 = (Moves) o;
        return Objects.equals(moves, moves1.moves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(moves);
    }
}
