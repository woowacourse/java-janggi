package domain.movement;

import domain.board.BoardState;
import domain.board.Position;
import domain.movement.vo.Path;
import domain.movement.vo.Paths;
import java.util.ArrayList;
import java.util.List;

public interface Movement {
    Paths findPotentialPaths(Position source);

    boolean isAvailablePath(Path path, BoardState board);

    default List<Position> findReachablePositions(Position source, BoardState board) {
        List<Position> reachablePositions = new ArrayList<>();

        for (Path path : findPotentialPaths(source).asList()) {
            reachablePositions.addAll(extractValidDestination(path, board));
        }

        return reachablePositions;
    }

    default List<Position> extractValidDestination(Path path, BoardState board) {
        if (!isAvailablePath(path, board)) {
            return List.of();
        }
        return List.of(path.positions().getLast());
    }

}
