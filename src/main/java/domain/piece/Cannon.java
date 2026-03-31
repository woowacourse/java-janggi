package domain.piece;

import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import domain.Position;
import domain.Side;
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
        for (int i = 0; i < positions.size(); i++) {
            if (!board.isEmpty(positions.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private boolean isInvalidBridge(int index, List<Position> positions, BoardReader board) {
        if (index == -1) {
            return true;
        }
        Piece bridge = board.getPiece(positions.get(index));
        return bridge.isCannon();
    }

    private void collectValidDestinations(List<Position> valid, List<Position> positions, int startIndex, BoardReader board) {
        for (int i = startIndex; i < positions.size(); i++) {
            if (processPositionAfterJump(valid, positions.get(i), board)) {
                break;
            }
        }
    }

    private boolean processPositionAfterJump(List<Position> valid, Position pos, BoardReader board) {
        if (board.isEmpty(pos)) {
            valid.add(pos);
            return false;
        }

        addIfCatchable(valid, pos, board);
        return true;
    }

    private void addIfCatchable(List<Position> valid, Position pos, BoardReader board) {
        Piece target = board.getPiece(pos);
        if (!target.isCannon() && !target.isAlly(getSide())) {
            valid.add(pos);
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
