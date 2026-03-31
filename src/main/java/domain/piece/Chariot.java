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
        for (Position pos : path.getPositions()) {
            if (processPosition(valid, pos, board)) {
                break;
            }
        }
    }

    private boolean processPosition(List<Position> valid, Position pos, BoardReader board) {
        if (board.isEmpty(pos)) {
            valid.add(pos);
            return false;
        }

        addIfEnemy(valid, pos, board);
        return true;
    }

    private void addIfEnemy(List<Position> valid, Position pos, BoardReader board) {
        Piece target = board.getPiece(pos);
        if (!target.isAlly(getSide())) {
            valid.add(pos);
        }
    }

    @Override
    public String toString() {
        return "차";
    }
}
