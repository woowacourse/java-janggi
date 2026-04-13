package domain.piece;

import domain.game.Position;
import domain.game.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cannon extends Piece {
    private final PieceType pieceType = PieceType.CANNON;

    public Cannon(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(List<Path> paths, BoardReader board) {
        return paths.stream()
                .flatMap(path -> collectJumpPathPositions(path, board).stream())
                .toList();
    }

    private List<Position> collectJumpPathPositions(Path path, BoardReader board) {
        List<Position> positions = path.getPositions();
        int bridgeIndex = findBridgeIndex(positions, board);

        if (isInvalidBridge(bridgeIndex, positions, board)) {
            return List.of();
        }

        return collectValidDestinations(positions, bridgeIndex + 1, board);
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

    private List<Position> collectValidDestinations(List<Position> positions, int startIndex, BoardReader board) {
        int obstacleIndex = findObstacleIndex(positions, startIndex, board);
        List<Position> valid = new ArrayList<>(positions.subList(startIndex, obstacleIndex));
        findCatchablePosition(positions, obstacleIndex, board).ifPresent(valid::add);
        return valid;
    }

    private int findObstacleIndex(List<Position> positions, int startIndex, BoardReader board) {
        int index = startIndex;
        while (index < positions.size() && board.isEmpty(positions.get(index))) {
            index++;
        }
        return index;
    }

    private Optional<Position> findCatchablePosition(List<Position> positions, int index, BoardReader board) {
        if (index >= positions.size()) {
            return Optional.empty();
        }
        Position position = positions.get(index);
        Piece target = board.getPiece(position);
        if (!target.isCannon() && !target.isAlly(getSide())) {
            return Optional.of(position);
        }
        return Optional.empty();
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public String getName() {
        return pieceType.getName();
    }
}
