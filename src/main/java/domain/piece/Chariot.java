package domain.piece;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Chariot extends Piece {
    private final PieceType pieceType = PieceType.CHARIOT;

    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(List<Path> paths, BoardReader board) {
        return paths.stream()
                .flatMap(path -> collectPathPositions(path, board).stream())
                .toList();
    }

    private List<Position> collectPathPositions(Path path, BoardReader board) {
        List<Position> positions = path.getPositions();
        int obstacleIndex = findObstacleIndex(positions, board);
        List<Position> valid = new ArrayList<>(positions.subList(0, obstacleIndex));
        findCapturePosition(positions, obstacleIndex, board).ifPresent(valid::add);
        return valid;
    }

    private int findObstacleIndex(List<Position> positions, BoardReader board) {
        int index = 0;
        while (index < positions.size() && board.isEmpty(positions.get(index))) {
            index++;
        }
        return index;
    }

    private Optional<Position> findCapturePosition(List<Position> positions, int index, BoardReader board) {
        if (index >= positions.size()) {
            return Optional.empty();
        }
        Position position = positions.get(index);
        Piece target = board.getPiece(position);
        if (!target.isAlly(getSide())) {
            return Optional.of(position);
        }
        return Optional.empty();
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
