package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class ElephantStrategy implements Strategy {

    @Override
    public List<Direction> getDirections() {
        return List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();

        for (Direction straight : getDirections()) {
            Position myeok1 = new Position(
                    from.row() + straight.getRowOffset(team),
                    from.col() + straight.getColOffset(team)
            );

            if (!board.isBlank(myeok1)) continue;

            for (Direction diag : getDiagonalsFor(straight)) {
                Position myeok2 = new Position(
                        myeok1.row() + diag.getRowOffset(team),
                        myeok1.col() + diag.getColOffset(team)
                );

                if (!board.isBlank(myeok2)) continue;

                Position target = new Position(myeok2.row() + diag.getRowOffset(team),
                        myeok2.col() + diag.getColOffset(team)
                );
                candidates.add(target);
            }
        }
        return candidates;
    }

    private List<Direction> getDiagonalsFor(Direction straight) {
        if (straight == Direction.NORTH) return List.of(Direction.NORTH_WEST, Direction.NORTH_EAST);
        if (straight == Direction.SOUTH) return List.of(Direction.SOUTH_WEST, Direction.SOUTH_EAST);
        if (straight == Direction.WEST) return List.of(Direction.NORTH_WEST, Direction.SOUTH_WEST);
        if (straight == Direction.EAST) return List.of(Direction.NORTH_EAST, Direction.SOUTH_EAST);
        return List.of();
    }
}
