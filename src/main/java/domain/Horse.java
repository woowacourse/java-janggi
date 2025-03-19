package domain;

import java.util.HashSet;
import java.util.Set;

public class Horse extends Piece {

    public Horse(Position position, Team team, Board board) {
        super(position, team, board);
    }

    @Override
    protected Set<Position> getMovablePositions() {
        Set<Position> positions = new HashSet<>();
        for (Direction straightDirection : Direction.getStraightDirection()) {
            goOneSde(position.nextPosition(straightDirection), straightDirection, positions, 0);
        }
        positions.forEach(p -> System.out.println(p.getColumn() + ", " + p.getRow()));
        return positions;
    }

    void goOneSde(Position position, Direction direction, Set<Position> positions, int depth) {
        if (position.isInValidPosition()) {
            return;
        }
        if (depth == 1) {
            if (!board.isSameTeam(this, position)) {
                positions.add(position);
            }
            return;
        }
        if (board.isExists(position)) {
            return;
        }
        if (depth == 0) {
            for (Direction crossDirection : direction.nextCrossDirection()) {
                goOneSde(position.nextPosition(crossDirection), crossDirection, positions, depth + 1);
            }
        }
    }

}
