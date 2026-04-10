package janggi.domain.move;

import janggi.domain.board.BoardReader;
import janggi.domain.space.Direction;
import janggi.domain.space.Path;
import janggi.domain.space.Position;
import java.util.List;

public class SequenceStrategy implements MovementStrategy {
    private final List<List<Direction>> defaultSequences;

    public SequenceStrategy(List<List<Direction>> defaultSequences) {
        this.defaultSequences = defaultSequences;
    }

    @Override
    public List<Position> getMovablePositions(Position current, BoardReader board) {
        return generatePaths(current, board).stream()
                .filter(path -> !path.isBlocked(board))
                .map(Path::getDestination)
                .toList();
    }

    @Override
    public List<Path> generatePaths(Position current, BoardReader board) {
        return defaultSequences.stream()
                .filter(current::canMove)
                .map(sequence -> Path.ofSequence(current, sequence))
                .toList();
    }
}
