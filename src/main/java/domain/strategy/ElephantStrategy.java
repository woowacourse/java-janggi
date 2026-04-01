package domain.strategy;

import domain.position.Position;
import domain.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class ElephantStrategy implements Strategy {

    @Override
    public List<Position> getMoveCandidates(Position from) {
        List<Position> candidates = new ArrayList<>();
        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction straight : straightDirections) {
            Position myeok1 = new Position(
                    from.getRow() + straight.getRowOffset(),
                    from.getColumn() + straight.getColOffset()
            );

            if (!board.isBlank(myeok1)) continue;

            for (Direction diag : getDiagonalsFor(straight)) {
                Position myeok2 = new Position(
                        myeok1.getRow() + diag.getRowOffset(),
                        myeok1.getColumn() + diag.getColOffset()
                );

                if (!board.isBlank(myeok2)) continue;

                Position target = new Position(myeok2.getRow() + diag.getRowOffset(),
                        myeok2.getColumn() + diag.getColOffset()
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
