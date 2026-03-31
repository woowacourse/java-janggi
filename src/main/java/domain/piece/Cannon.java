package domain.piece;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {
    public Cannon(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board) {
        List<Position> valid = new ArrayList<>();
        for (Path path : paths) {
            addJumpPathPositions(valid, path, board);
        }
        return valid;
    }

    private void addJumpPathPositions(List<Position> valid, Path path, BoardReader board) {
        List<Position> positions = path.getPositions();
        int bridgeIndex = findBridgeIndex(positions, board);

        if (isInvalidBridge(bridgeIndex, positions, board)) {
            return;
        }

        collectValidDestinations(valid, positions, bridgeIndex + 1, board);
    }

    private int findBridgeIndex(List<Position> positions, BoardReader board) {
        int index = 0;
        while (index < positions.size() && board.isEmpty(positions.get(index))) {
            index++;
        }
        return findValidIndex(index, positions.size());
    }

    private int findValidIndex(int index, int size) {
        if (index == size) {
            return -1;
        }
        return index;
    }

    private boolean isInvalidBridge(int index, List<Position> positions, BoardReader board) {
        if (index == -1) {
            return true;
        }
        Piece bridge = board.getPiece(positions.get(index));
        return bridge.isCannon();
    }

    private void collectValidDestinations(List<Position> valid, List<Position> positions, int startIndex, BoardReader board) {
        int obstacleIndex = findObstacleIndex(positions, startIndex, board);
        valid.addAll(positions.subList(startIndex, obstacleIndex));
        addCatchableIfPossible(valid, positions, obstacleIndex, board);
    }

    private int findObstacleIndex(List<Position> positions, int startIndex, BoardReader board) {
        int index = startIndex;
        while (index < positions.size() && board.isEmpty(positions.get(index))) {
            index++;
        }
        return index;
    }

    private void addCatchableIfPossible(List<Position> valid, List<Position> positions, int index, BoardReader board) {
        if (index < positions.size()) {
            addIfCatchable(valid, positions.get(index), board);
        }
    }

    private void addIfCatchable(List<Position> valid, Position position, BoardReader board) {
        Piece target = board.getPiece(position);
        if (!target.isCannon() && !target.isAlly(getSide())) {
            valid.add(position);
        }
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public String toString() {
        return "포";
    }
}
