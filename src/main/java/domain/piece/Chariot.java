package domain.piece;

import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import domain.Position;
import domain.Side;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {
    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board) {
        List<Position> valid = new ArrayList<>();
        for (Path path : paths) {
            collectPathPositions(valid, path, board);
        }
        return valid;
    }

    private void collectPathPositions(List<Position> valid, Path path, BoardReader board) {
        List<Position> positions = path.getPositions();
        int obstacleIndex = findObstacleIndex(positions, board);
        valid.addAll(positions.subList(0, obstacleIndex));
        addCaptureIfPossible(valid, positions, obstacleIndex, board);
    }

    private int findObstacleIndex(List<Position> positions, BoardReader board) {
        int index = 0;
        while (index < positions.size() && board.isEmpty(positions.get(index))) {
            index++;
        }
        return index;
    }

    private void addCaptureIfPossible(List<Position> valid, List<Position> positions, int index, BoardReader board) {
        if (index < positions.size()) {
            addIfEnemy(valid, positions.get(index), board);
        }
    }

    private void addIfEnemy(List<Position> valid, Position position, BoardReader board) {
        Piece target = board.getPiece(position);
        if (!target.isAlly(getSide())) {
            valid.add(position);
        }
    }

    @Override
    public String toString() {
        return "차";
    }
}
