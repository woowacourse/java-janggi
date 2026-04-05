package domain.strategy;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import java.util.ArrayList;
import java.util.List;

public class SequenceStrategy implements MovementStrategy {
    private final List<List<Direction>> defaultSequences;

    public SequenceStrategy(List<List<Direction>> defaultSequences) {
        this.defaultSequences = defaultSequences;
    }

    @Override
    public List<Position> getMovablePositions(Position current, BoardReader board, Side side) {
        return generatePaths(current).stream()
                .filter(path -> !path.isBlocked(board))
                .map(Path::getDestination)
                .filter(dest -> board.isEmpty(dest) || board.isAlly(dest, side))
                .toList();
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return defaultSequences.stream()
                .filter(current::canMove)
                .map(sequence -> Path.ofSequence(current, sequence))
                .toList();
    }
}
